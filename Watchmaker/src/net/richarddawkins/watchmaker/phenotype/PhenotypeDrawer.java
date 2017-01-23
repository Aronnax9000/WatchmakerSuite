package net.richarddawkins.watchmaker.phenotype;

public interface PhenotypeDrawer {

	DrawingPreferences getDrawingPreferences();
	void drawPic(Object graphicsContext, Phenotype phenotype);

}
