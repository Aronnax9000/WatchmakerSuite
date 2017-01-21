package net.richarddawkins.watchmaker.morphs.colour.swing;

import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.morphs.colour.ColourBiomorphConfig;
import net.richarddawkins.watchmaker.morphs.colour.genome.swing.SwingColorGeneBoxStrip;
import net.richarddawkins.watchmaker.morphs.colour.geom.swing.SwingColourPicDrawer;
import net.richarddawkins.watchmaker.swing.appdata.SimpleSwingAppData;
import net.richarddawkins.watchmaker.swing.appdata.SwingAppData;

public class ColourSwingAppData extends SimpleSwingAppData implements SwingAppData {
	
	public ColourSwingAppData() {
		setMenuBuilder(new ColourMenuBuilder(this));
		setSwingPicDrawer(new SwingColourPicDrawer());
		config = new ColourBiomorphConfig();
	}
	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) { 
		GeneBoxStrip geneBoxStrip = new SwingColorGeneBoxStrip();
		geneBoxStrip.setEngineeringMode(engineeringMode);
		return geneBoxStrip;
	}

}
