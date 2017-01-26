package net.richarddawkins.watchmaker.phenotype;

import java.awt.image.BufferedImage;

public interface PhenotypeDrawer {

	DrawingPreferences getDrawingPreferences();
	void drawPic(Object graphicsContext, Phenotype phenotype);

	BufferedImage getBufferedImage(Phenotype phenotype, double scale);

}
