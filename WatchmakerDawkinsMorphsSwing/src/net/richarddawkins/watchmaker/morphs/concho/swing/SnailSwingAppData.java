package net.richarddawkins.watchmaker.morphs.concho.swing;

import net.richarddawkins.watchmaker.morphs.concho.SnailConfig;
import net.richarddawkins.watchmaker.swing.appdata.SimpleSwingAppData;
import net.richarddawkins.watchmaker.swing.geneboxstrip.GeneBoxStrip;

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
