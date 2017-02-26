package net.richarddawkins.watchmaker.swing.drawer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
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
import net.richarddawkins.watchmaker.util.Globals;

public class SwingMorphDrawer implements MorphDrawer {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.drawer.SwingMorphDrawer");

	public SwingMorphDrawer(PhenotypeDrawer picDrawer) {
		this.picDrawer = picDrawer;
	}


	protected PhenotypeDrawer picDrawer;

	
	
	@Override
	public void draw(BoxedMorph boxedMorph, Object graphicsContext, Dim size, boolean selectionState) {
		Phenotype phenotype = boxedMorph.getMorph().getPhenotype();
		Graphics2D g2 = (Graphics2D) graphicsContext;
		AffineTransform saveTransform = g2.getTransform();
		logger.fine("Draw BoxedMorph, saved transform");
		double scale = 1;
		if (boxedMorph.isScaleWithProgress()) {
			scale = boxedMorph.getProgress();
			logger.fine("Scale: " + scale);
		}
		scale *= Math.pow(Globals.zoomBase, boxedMorph.getBoxes().getScale());
		logger.fine("Scale: " + scale);
		
		Morph morph = boxedMorph.getMorph();
		BufferedImage bufferedImage = (BufferedImage) morph.getImage();
		if (morph.getImage() == null) {
			bufferedImage = (BufferedImage) picDrawer.getImage(phenotype, scale);
			morph.setImage(bufferedImage);
		}
		Point position = boxedMorph.getPosition(size);
		g2.translate(position.h - bufferedImage.getWidth() / 2, position.v - bufferedImage.getHeight() / 2);
		g2.clearRect(0, 0, bufferedImage.getWidth() - 1, bufferedImage.getHeight() - 1);

		
		if(selectionState) {
			g2.drawImage(bufferedImage, 0, 0, null);
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(2));
			g2.drawRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
		} else {
			g2.drawImage(bufferedImage, 0, 0, null);
		}
		String name = morph.getName();
		if(name != null) {
			FontRenderContext frc = g2.getFontRenderContext();
			
			float width = (float) g2.getFont().getStringBounds(name, frc).getWidth();
			float height = (float) g2.getFont().getStringBounds(name, frc).getHeight();
			g2.translate((bufferedImage.getWidth() - width) / 2, bufferedImage.getHeight() + height);
			g2.drawString(name, 0, 0);
		}
		logger.fine("Restore transform");
		g2.setTransform(saveTransform);
	}
}
