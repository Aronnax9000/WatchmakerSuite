package net.richarddawkins.watchmaker.swing.drawer;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.MorphDrawer;
import net.richarddawkins.watchmaker.phenotype.Phenotype;
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
		Phenotype phenotype = boxedMorph.getMorph().getPic();
		Graphics2D g2 = (Graphics2D) graphicsContext;
		AffineTransform saveTransform = g2.getTransform();
		logger.fine("Draw BoxedMorph, saved transform");
		double scale = 1;
		if (boxedMorph.isScaleWithProgress()) {
			scale = boxedMorph.getProgress();
			logger.fine("Scale: " + scale);
		}
		scale *= Math.pow(2, picDrawer.getDrawingPreferences().getScale());
		logger.fine("Scale: " + scale);
		
		Morph morph = boxedMorph.getMorph();
		BufferedImage bufferedImage = (BufferedImage) morph.getImage();
		if (morph.getImage() == null) {
			bufferedImage = (BufferedImage) picDrawer.getImage(phenotype, scale);
			morph.setImage(bufferedImage);
		}
		Point position = boxedMorph.getPosition(d);
		g2.translate(position.h - bufferedImage.getWidth() / 2, position.v - bufferedImage.getHeight() / 2);

		
		
		g2.drawImage(bufferedImage, 0, 0, null);
		logger.fine("Restore transform");
		g2.setTransform(saveTransform);
	}
}
