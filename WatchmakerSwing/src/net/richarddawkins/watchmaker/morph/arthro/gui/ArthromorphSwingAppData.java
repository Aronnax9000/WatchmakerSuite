package net.richarddawkins.watchmaker.morph.arthro.gui;

import net.richarddawkins.watchmaker.genome.gui.GeneBoxStrip;
import net.richarddawkins.watchmaker.gui.SimpleSwingAppData;
import net.richarddawkins.watchmaker.morph.arthro.genome.gui.ArthromorphGeneBoxStrip;

public class ArthromorphSwingAppData extends SimpleSwingAppData {
	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) {
		
		return new ArthromorphGeneBoxStrip(this, engineeringMode);
	}
}
