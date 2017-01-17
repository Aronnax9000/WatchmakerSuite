package net.richarddawkins.watchmaker.morphs.colour.swing;

import net.richarddawkins.watchmaker.genome.gui.GeneBoxStrip;
import net.richarddawkins.watchmaker.gui.SimpleSwingAppData;
import net.richarddawkins.watchmaker.gui.SwingAppData;
import net.richarddawkins.watchmaker.morphs.colour.genome.swing.ColourGeneBoxStrip;

public class ColourSwingAppData extends SimpleSwingAppData implements SwingAppData {
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) { 
		return new ColourGeneBoxStrip(this, engineeringMode); 
	}

}
