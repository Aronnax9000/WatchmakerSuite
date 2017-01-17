package net.richarddawkins.watchmaker.gui;

import net.richarddawkins.watchmaker.genome.gui.GeneBoxStrip;
import net.richarddawkins.watchmaker.morph.colour.genome.gui.ColourGeneBoxStrip;

public class ColourSwingAppData extends SimpleSwingAppData implements SwingAppData {
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) { 
		return new ColourGeneBoxStrip(this, engineeringMode); 
	}

}
