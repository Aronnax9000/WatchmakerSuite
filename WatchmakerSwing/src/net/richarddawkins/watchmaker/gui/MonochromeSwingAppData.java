package net.richarddawkins.watchmaker.gui;

import net.richarddawkins.watchmaker.genome.gui.GeneBoxStrip;
import net.richarddawkins.watchmaker.morph.mono.gui.MonochromeGeneBoxStrip;

public class MonochromeSwingAppData extends SimpleSwingAppData implements SwingAppData {
	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) {

		return new MonochromeGeneBoxStrip(this, engineeringMode);
	}

}
