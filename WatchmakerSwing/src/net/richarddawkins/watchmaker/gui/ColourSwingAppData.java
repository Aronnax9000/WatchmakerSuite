package net.richarddawkins.watchmaker.gui;

import net.richarddawkins.watchmaker.genome.gui.GeneBoxStrip;
import net.richarddawkins.watchmaker.morphs.colour.genome.swing.ColourGeneBoxStrip;

public class ColourSwingAppData extends SimpleSwingAppData implements SwingAppData {
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) { 
		return new ColourGeneBoxStrip(this, engineeringMode); 
	}

}
