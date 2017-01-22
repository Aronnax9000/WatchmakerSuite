package net.richarddawkins.watchmaker.morphs.arthro.swing;

import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.morphs.arthro.ArthromorphConfig;
import net.richarddawkins.watchmaker.morphs.arthro.genome.swing.SwingArthromorphGeneBoxStrip;
import net.richarddawkins.watchmaker.morphs.arthro.geom.gui.swing.SwingArthroPicDrawer;
import net.richarddawkins.watchmaker.morphs.swing.MorphType;
import net.richarddawkins.watchmaker.swing.app.SwingAppData;

public class ArthromorphSwingAppData extends SwingAppData {
	public ArthromorphSwingAppData() {
		ArthromorphMenuBuilder menuBuilder = new ArthromorphMenuBuilder();
		this.setIcon(MorphType.ARTHROMORPH.getIconFilename());
		menuBuilder.setAppData(this);
		setMenuBuilder(menuBuilder);
		setPicDrawer(new SwingArthroPicDrawer());
		config = new ArthromorphConfig();
	}
	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) {
		GeneBoxStrip geneBoxStrip = new SwingArthromorphGeneBoxStrip();
		geneBoxStrip.setEngineeringMode(engineeringMode); 
		return geneBoxStrip;
	}
}
