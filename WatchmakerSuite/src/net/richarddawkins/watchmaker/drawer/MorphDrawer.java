package net.richarddawkins.watchmaker.drawer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import net.richarddawkins.watchmaker.draw.DrawingPrimitive;
import net.richarddawkins.watchmaker.draw.QuickDrawState;
import net.richarddawkins.watchmaker.morph.common.Morph;

public class MorphDrawer implements GraphicsDrawer {

	private Point origin;
	private Point destination;
	private double progress = 0.0d;
	private Morph morph;

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
		QuickDrawState qds = new QuickDrawState();
		Rectangle bounds = null;
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
		Point offset = new Point(origin.x - horzAxis , origin.y - vertAxis);
		
		int animationX = 0;
		int animationY = 0;
		
		if(destination != null) {
			animationX = (int) ((double)(destination.x - origin.x) * progress);
			animationY = (int) ((double)(destination.y - origin.y) * progress);
		}
		
		
		AffineTransform saveTransform = g.getTransform();
		AffineTransform translationTransform = 
				AffineTransform.getTranslateInstance(offset.x + animationX, offset.y + animationY);
		
		
		
		g.transform(translationTransform);
		qds = new QuickDrawState();
		
		for(DrawingPrimitive primitive: primitives)
		{
			primitive.execute(g, qds);
		}
		g.setTransform(saveTransform);
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
