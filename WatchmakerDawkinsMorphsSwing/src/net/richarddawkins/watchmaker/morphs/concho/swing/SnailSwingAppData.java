package net.richarddawkins.watchmaker.morphs.concho.swing;

import net.richarddawkins.watchmaker.genome.gui.GeneBoxStrip;
import net.richarddawkins.watchmaker.gui.SimpleSwingAppData;

public class SnailSwingAppData extends SimpleSwingAppData {
	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) {
		return new SnailGeneBoxStrip(this, engineeringMode);
	}
}
