package net.richarddawkins.watchmaker.drawer;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import net.richarddawkins.watchmaker.draw.DrawingPrimitive;
import net.richarddawkins.watchmaker.draw.QuickDrawState;
import net.richarddawkins.watchmaker.morph.common.Morph;

public class MorphDrawer implements GraphicsDrawer {

	protected Dimension size;
	public void setSize(Dimension size) {
		this.size = size;
	}
	protected Point origin;
	protected Point destination;
	protected double progress = 0.0d;
	protected Morph morph;

	protected boolean scaleWithProgress;
	
	public boolean isScaleWithProgress() {
		return scaleWithProgress;
	}

	public void setScaleWithProgress(boolean scaleWithProgress) {
		this.scaleWithProgress = scaleWithProgress;
	}

	public Morph getMorph() {
		return morph;
	}

	public void setMorph(Morph morph) {
		this.morph = morph;
	}

	
	@Override
	public Point getDestination() {
		return destination;
	}

	@Override
	public void setDestination(Point destination) {
		this.destination = destination;
	}

	
	@Override
	public double getProgress() {
		return progress;
	}

	@Override
	public void setProgress(double progress) {
		this.progress = progress;
	}

	public void setPosition(Point position) { origin = position; }
	
	private Vector<DrawingPrimitive> primitives = new Vector<DrawingPrimitive>();
	public void add(DrawingPrimitive primitive) { primitives.add(primitive); }
	
	public MorphDrawer(Morph morph, Point centre) {
		this.morph = morph;
		this.setPosition(origin);
		this.morph.generatePrimitives(primitives, centre);
	}
	
	@Override
	public void draw(Graphics2D g) {
		AffineTransform saveTransform = g.getTransform();
		AffineTransform originTransform = 
				AffineTransform.getTranslateInstance(origin.x, origin.y);
		g.transform(originTransform);
		drawChildren(g);
		g.setTransform(saveTransform);
	}
	
	
	public void drawChildren(Graphics2D g) {
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

		animate(g);

		qds = new QuickDrawState();
		for(DrawingPrimitive primitive: primitives)
		{
			primitive.execute(g, qds);
		}
	}

	public void animate(Graphics2D g) {
		int animationX = 0;
		int animationY = 0;
		double scale = 1;
		if(destination != null) {
			animationX = (int) ((double)(destination.x - origin.x) * progress);
			animationY = (int) ((double)(destination.y - origin.y) * progress);
			scale = progress;
			AffineTransform animationTransform = 
					AffineTransform.getTranslateInstance(animationX, animationY);
			g.transform(animationTransform);
			if(scaleWithProgress) {
				AffineTransform scaleTransform = AffineTransform.getScaleInstance(scale, scale);
				g.transform(scaleTransform);
			}		
		}

	}
	
	public void nudge() {
		if(Math.abs(1.0d - progress) < 0.1d) {
			
    		progress = 1.0d;
    	} else {
    		progress += (1.0d - progress) * 0.1;
    	}
		
	}

	public Point getPosition() {
		
		return origin;
	}


}
