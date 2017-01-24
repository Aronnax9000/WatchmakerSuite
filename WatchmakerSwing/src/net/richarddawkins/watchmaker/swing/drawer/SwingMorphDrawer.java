package net.richarddawkins.watchmaker.swing.drawer;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morph.draw.MorphDrawer;
import net.richarddawkins.watchmaker.phenotype.PhenotypeDrawer;

public class SwingMorphDrawer implements MorphDrawer {

    public SwingMorphDrawer(PhenotypeDrawer picDrawer) {
        this.picDrawer = picDrawer;
    }
    
	protected Dim size;
	public void setSize(Dim size) {
		this.size = size;
	}

	protected PhenotypeDrawer picDrawer;


	
	@Override
	public void draw(BoxedMorph locatedMorph, Object graphicsContext, Dim d) {
		Graphics2D g = (Graphics2D) graphicsContext;
		Point position = locatedMorph.getPosition();
		AffineTransform saveTransform = g.getTransform();
		AffineTransform originTransform = 
				AffineTransform.getTranslateInstance(position.h, position.v);
		g.transform(originTransform);
		double scale = Math.pow(2, picDrawer.getDrawingPreferences().getScale());
		g.scale(scale, scale);
		drawChildren(locatedMorph, g, d);
		g.setTransform(saveTransform);
	}
    
	public void drawChildren(BoxedMorph boxedMorph, Graphics2D g2, Dim d) {
		animate(boxedMorph, g2, d);
		picDrawer.drawPic(g2, boxedMorph.getMorph().getPic());
	}


	public void animate(BoxedMorph boxedMorph, Graphics2D g, Dim d) {
		double scale = 1;
		if(boxedMorph.getDestinationBoxNo() != -1) {
			Point position = boxedMorph.getPosition(d);
			scale = boxedMorph.getProgress();
			AffineTransform animationTransform = 
					AffineTransform.getTranslateInstance(position.h, position.v);
			g.transform(animationTransform);
			if(boxedMorph.isScaleWithProgress()) {
				AffineTransform scaleTransform = AffineTransform.getScaleInstance(scale, scale);
				g.transform(scaleTransform);
			}		
		}
	}	



}
