package net.richarddawkins.watchmaker.morphs.colour.swing;

import net.richarddawkins.watchmaker.morphs.colour.ColourBiomorphConfig;
import net.richarddawkins.watchmaker.morphs.colour.genome.swing.ColourGeneBoxStrip;
import net.richarddawkins.watchmaker.morphs.colour.geom.swing.SwingColourPicDrawer;
import net.richarddawkins.watchmaker.swing.appdata.SimpleSwingAppData;
import net.richarddawkins.watchmaker.swing.appdata.SwingAppData;
import net.richarddawkins.watchmaker.swing.geneboxstrip.GeneBoxStrip;

public class ColourSwingAppData extends SimpleSwingAppData implements SwingAppData {
	
	public ColourSwingAppData() {
		setMenuBuilder(new ColourMenuBuilder(this));
		setSwingPicDrawer(new SwingColourPicDrawer());
		config = new ColourBiomorphConfig();
		
		
	}
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) { 
		return new ColourGeneBoxStrip(this, engineeringMode); 
	}

}
