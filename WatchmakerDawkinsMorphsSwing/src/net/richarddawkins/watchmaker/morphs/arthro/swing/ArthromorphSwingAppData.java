package net.richarddawkins.watchmaker.morphs.arthro.swing;

import net.richarddawkins.watchmaker.genome.gui.GeneBoxStrip;
import net.richarddawkins.watchmaker.gui.SimpleSwingAppData;
import net.richarddawkins.watchmaker.morphs.arthro.genome.swing.ArthromorphGeneBoxStrip;

public class ArthromorphSwingAppData extends SimpleSwingAppData {
	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) {
		
		return new ArthromorphGeneBoxStrip(this, engineeringMode);
	}
}
