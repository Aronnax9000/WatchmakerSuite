package net.richarddawkins.watchmaker.gui;

import net.richarddawkins.watchmaker.genome.gui.GeneBoxStrip;
import net.richarddawkins.watchmaker.morphs.mono.swing.MonochromeGeneBoxStrip;

public class MonochromeSwingAppData extends SimpleSwingAppData implements SwingAppData {
	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) {

		return new MonochromeGeneBoxStrip(this, engineeringMode);
	}

}
