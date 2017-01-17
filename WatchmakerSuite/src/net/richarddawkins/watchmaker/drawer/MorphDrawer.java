package net.richarddawkins.watchmaker.drawer;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import net.richarddawkins.watchmaker.draw.DrawingPrimitive;
import net.richarddawkins.watchmaker.morph.biomorph.geom.gui.SwingPicDrawer;

public abstract class MorphDrawer implements GraphicsDrawer {

    public MorphDrawer(SwingPicDrawer swingPicDrawer) {
        this.swingPicDrawer = swingPicDrawer;
    }
    
	protected Dimension size;
	public void setSize(Dimension size) {
		this.size = size;
	}

	protected SwingPicDrawer swingPicDrawer;

	
	
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
	
	
	abstract public void drawChildren(LocatedMorph locatedMorph, Graphics2D g);
	
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
