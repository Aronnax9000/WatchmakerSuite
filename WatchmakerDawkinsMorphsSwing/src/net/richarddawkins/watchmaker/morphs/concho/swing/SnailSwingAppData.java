package net.richarddawkins.watchmaker.morphs.concho.swing;

import net.richarddawkins.watchmaker.swing.SimpleSwingAppData;
import net.richarddawkins.watchmaker.swing.genome.GeneBoxStrip;

public class SnailSwingAppData extends SimpleSwingAppData {
	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) {
		return new SnailGeneBoxStrip(this, engineeringMode);
	}
}
