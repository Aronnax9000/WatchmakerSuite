package net.richarddawkins.watchmaker.morphs.concho.swing;

import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.morphs.concho.SnailConfig;
import net.richarddawkins.watchmaker.swing.appdata.SimpleSwingAppData;

public class SnailSwingAppData extends SimpleSwingAppData {
	
	public SnailSwingAppData() {
		setMenuBuilder(new SnailMenuBuilder(this));
		setSwingPicDrawer(new SwingSnailPicDrawer());
		config = new SnailConfig();
		
	}
	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) {
		GeneBoxStrip geneBoxStrip = new SwingSnailGeneBoxStrip();
		geneBoxStrip.setEngineeringMode(engineeringMode);
		return geneBoxStrip;
	}
}
