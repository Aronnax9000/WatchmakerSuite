package net.richarddawkins.watchmaker.morphs.mono.swing;

import net.richarddawkins.watchmaker.genome.gui.GeneBoxStrip;
import net.richarddawkins.watchmaker.gui.SimpleSwingAppData;
import net.richarddawkins.watchmaker.gui.SwingAppData;

public class MonochromeSwingAppData extends SimpleSwingAppData implements SwingAppData {
	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) {

		return new MonochromeGeneBoxStrip(this, engineeringMode);
	}

}
