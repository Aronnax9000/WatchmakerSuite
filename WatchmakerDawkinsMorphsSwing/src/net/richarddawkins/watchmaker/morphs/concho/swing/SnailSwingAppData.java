package net.richarddawkins.watchmaker.morphs.concho.swing;

import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.morphs.concho.SnailConfig;
import net.richarddawkins.watchmaker.swing.app.SwingAppData;

public class SnailSwingAppData extends SwingAppData {
	
	public SnailSwingAppData() {
		setMenuBuilder(new SnailMenuBuilder(this));
		setPicDrawer(new SwingSnailPicDrawer());
		config = new SnailConfig();
		
	}
	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) {
		GeneBoxStrip geneBoxStrip = new SwingSnailGeneBoxStrip();
		geneBoxStrip.setEngineeringMode(engineeringMode);
		return geneBoxStrip;
	}
}
