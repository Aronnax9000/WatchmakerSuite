package net.richarddawkins.watchmaker.morphs.colour.swing;

import net.richarddawkins.watchmaker.morphs.colour.ColourBiomorphConfig;
import net.richarddawkins.watchmaker.morphs.colour.genome.swing.ColourGeneBoxStrip;
import net.richarddawkins.watchmaker.morphs.colour.geom.swing.SwingColourPicDrawer;
import net.richarddawkins.watchmaker.swing.SimpleSwingAppData;
import net.richarddawkins.watchmaker.swing.SwingAppData;
import net.richarddawkins.watchmaker.swing.genome.GeneBoxStrip;

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
