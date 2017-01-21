package net.richarddawkins.watchmaker.morphs.arthro.swing;

import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.morphs.arthro.ArthromorphConfig;
import net.richarddawkins.watchmaker.morphs.arthro.genome.swing.SwingArthromorphGeneBoxStrip;
import net.richarddawkins.watchmaker.morphs.arthro.geom.gui.swing.SwingArthroPicDrawer;
import net.richarddawkins.watchmaker.swing.appdata.SimpleSwingAppData;
import net.richarddawkins.watchmaker.swing.geom.SwingPicDrawer;

public class ArthromorphSwingAppData extends SimpleSwingAppData {
	public ArthromorphSwingAppData() {
		setMenuBuilder(new ArthromorphMenuBuilder(this));
		setSwingPicDrawer((SwingPicDrawer)new SwingArthroPicDrawer());
		config = new ArthromorphConfig();
	}
	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) {
		GeneBoxStrip geneBoxStrip = new SwingArthromorphGeneBoxStrip();
		geneBoxStrip.setEngineeringMode(engineeringMode); 
		return geneBoxStrip;
	}
}
