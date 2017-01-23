package net.richarddawkins.watchmaker.morphs.arthro.swing;

import java.awt.Graphics2D;

import net.richarddawkins.watchmaker.morphs.arthro.ArthromorphPic;
import net.richarddawkins.watchmaker.phenotype.Phenotype;
import net.richarddawkins.watchmaker.phenotype.PhenotypeDrawer;

public class SwingArthromorphPicDrawer implements PhenotypeDrawer {

	@Override
	public void drawPic(Object graphicsContext, Phenotype phenotype) {
		ArthromorphPic pic = (ArthromorphPic) phenotype;
		Graphics2D g2 = (Graphics2D) graphicsContext;

	}

}
