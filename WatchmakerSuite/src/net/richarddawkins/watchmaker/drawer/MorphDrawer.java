package net.richarddawkins.watchmaker.drawer;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import net.richarddawkins.watchmaker.draw.DrawingPrimitive;
import net.richarddawkins.watchmaker.draw.QuickDrawState;

public class MorphDrawer implements GraphicsDrawer {

	protected Dimension size;
	public void setSize(Dimension size) {
		this.size = size;
	}


	
	
	private Vector<DrawingPrimitive> primitives = new Vector<DrawingPrimitive>();
	public void add(DrawingPrimitive primitive) { primitives.add(primitive); }
	
	
	@Override
	public void draw(LocatedMorph locatedMorph, Graphics2D g) {
		Point position = locatedMorph.getPosition();
		AffineTransform saveTransform = g.getTransform();
		AffineTransform originTransform = 
				AffineTransform.getTranslateInstance(position.x, position.y);
		g.transform(originTransform);
		drawChildren(locatedMorph, g);
		g.setTransform(saveTransform);
	}
	
	
	public void drawChildren(LocatedMorph locatedMorph, Graphics2D g) {
		locatedMorph.morph.generatePrimitives(primitives, new Point(0,0));
		Rectangle bounds = null;
		QuickDrawState qds = new QuickDrawState();
		for(DrawingPrimitive primitive: primitives)
		{
			Rectangle boundingRect = primitive.getBounds(qds);
			if(boundingRect != null) 
				if(bounds != null)
					bounds = bounds.union(boundingRect);
				else 
					bounds = boundingRect;
		}
		int horzAxis = bounds.x + bounds.width / 2;
		int vertAxis = bounds.y + bounds.height / 2;
		
		AffineTransform offsetTransform = 
				AffineTransform.getTranslateInstance(- horzAxis , - vertAxis);
		g.transform(offsetTransform);

		animate(locatedMorph, g);

		qds = new QuickDrawState();
		for(DrawingPrimitive primitive: primitives)
		{
			primitive.execute(g, qds);
		}
	}

	public void animate(LocatedMorph locatedMorph, Graphics2D g) {
		int animationX = 0;
		int animationY = 0;
		double scale = 1;
		Point origin = locatedMorph.getPosition();
		Point destination = locatedMorph.getDestination();
		double progress = locatedMorph.getProgress();
		if(destination != null) {
			animationX = (int) ((double)(destination.x - origin.x) * progress);
			animationY = (int) ((double)(destination.y - origin.y) * progress);
			scale = progress;
			AffineTransform animationTransform = 
					AffineTransform.getTranslateInstance(animationX, animationY);
			g.transform(animationTransform);
			if(locatedMorph.scaleWithProgress) {
				AffineTransform scaleTransform = AffineTransform.getScaleInstance(scale, scale);
				g.transform(scaleTransform);
			}		
		}

	}
	



}
