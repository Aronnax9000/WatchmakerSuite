package net.richarddawkins.watchmaker.swing.drawer;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import net.richarddawkins.watchmaker.geom.LocatedMorph;
import net.richarddawkins.watchmaker.geom.PhenotypeDrawer;
import net.richarddawkins.watchmaker.morph.draw.MorphDrawer;

public class SwingMorphDrawer implements MorphDrawer {

    public SwingMorphDrawer(PhenotypeDrawer picDrawer) {
        this.picDrawer = picDrawer;
    }
    
	protected Dimension size;
	public void setSize(Dimension size) {
		this.size = size;
	}

	protected PhenotypeDrawer picDrawer;


	
	@Override
	public void draw(LocatedMorph locatedMorph, Object graphicsContext) {
		Graphics2D g = (Graphics2D) graphicsContext;
		Point position = locatedMorph.getPosition();
		AffineTransform saveTransform = g.getTransform();
		AffineTransform originTransform = 
				AffineTransform.getTranslateInstance(position.x, position.y);
		g.transform(originTransform);
		drawChildren(locatedMorph, g);
		g.setTransform(saveTransform);
	}
    
	public void drawChildren(LocatedMorph locatedMorph, Graphics2D g2) {
		animate(locatedMorph, g2);
		picDrawer.drawPic(g2, locatedMorph.getMorph().getPic());
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
			if(locatedMorph.isScaleWithProgress()) {
				AffineTransform scaleTransform = AffineTransform.getScaleInstance(scale, scale);
				g.transform(scaleTransform);
			}		
		}

	}
	



}
