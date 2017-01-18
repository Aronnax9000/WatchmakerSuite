package net.richarddawkins.watchmaker.morphs.mono.swing;

import net.richarddawkins.watchmaker.swing.SimpleSwingAppData;
import net.richarddawkins.watchmaker.swing.SwingAppData;
import net.richarddawkins.watchmaker.swing.genome.GeneBoxStrip;

public class MonochromeSwingAppData extends SimpleSwingAppData implements SwingAppData {
	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) {

		return new MonochromeGeneBoxStrip(this, engineeringMode);
	}

}
