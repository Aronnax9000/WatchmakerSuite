package net.richarddawkins.watchmaker.swing.drawer;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morph.draw.MorphDrawer;
import net.richarddawkins.watchmaker.phenotype.PhenotypeDrawer;

public class SwingMorphDrawer implements MorphDrawer {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.drawer.SwingMorphDrawer");

    public SwingMorphDrawer(PhenotypeDrawer picDrawer) {
        this.picDrawer = picDrawer;
    }
    
	protected Dim size;
	public void setSize(Dim size) {
		this.size = size;
	}

	protected PhenotypeDrawer picDrawer;
	
	@Override
	public void draw(BoxedMorph boxedMorph, Object graphicsContext, Dim d) {
		Graphics2D g2 = (Graphics2D) graphicsContext;
		AffineTransform saveTransform = g2.getTransform();
		logger.fine("Draw BoxedMorph, saved transform");
		Point position = boxedMorph.getPosition(d);
		g2.translate(position.h, position.v);
		logger.fine("Translate: "+ position.toString());
		
		if(boxedMorph.isScaleWithProgress()) {
			double scale = boxedMorph.getProgress();
			logger.fine("Scale: "+ scale);
			g2.scale(scale, scale);
		}
		double scale = Math.pow(2, picDrawer.getDrawingPreferences().getScale());
		logger.fine("Scale: "+ scale);
		g2.scale(scale, scale);

		picDrawer.drawPic(g2, boxedMorph.getMorph().getPic());
		logger.fine("Restore transform");
		g2.setTransform(saveTransform);
	}
}
