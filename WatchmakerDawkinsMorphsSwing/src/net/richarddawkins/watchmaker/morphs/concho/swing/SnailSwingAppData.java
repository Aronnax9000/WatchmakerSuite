package net.richarddawkins.watchmaker.morphs.concho.swing;

import net.richarddawkins.watchmaker.morphs.concho.SnailConfig;
import net.richarddawkins.watchmaker.swing.SimpleSwingAppData;
import net.richarddawkins.watchmaker.swing.genome.GeneBoxStrip;

public class SnailSwingAppData extends SimpleSwingAppData {
	
	public SnailSwingAppData() {
		setMenuBuilder(new SnailMenuBuilder(this));
		setSwingPicDrawer(new SwingSnailPicDrawer());
		config = new SnailConfig();
		
	}
	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) {
		return new SnailGeneBoxStrip(this, engineeringMode);
	}
}
