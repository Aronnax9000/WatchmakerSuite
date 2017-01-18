package net.richarddawkins.watchmaker.morphs.arthro.swing;

import net.richarddawkins.watchmaker.morphs.arthro.genome.swing.ArthromorphGeneBoxStrip;
import net.richarddawkins.watchmaker.swing.SimpleSwingAppData;
import net.richarddawkins.watchmaker.swing.genome.GeneBoxStrip;

public class ArthromorphSwingAppData extends SimpleSwingAppData {
	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) {
		
		return new ArthromorphGeneBoxStrip(this, engineeringMode);
	}
}
