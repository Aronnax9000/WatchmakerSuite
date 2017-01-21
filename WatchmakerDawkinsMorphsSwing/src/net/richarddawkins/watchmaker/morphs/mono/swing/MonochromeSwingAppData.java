package net.richarddawkins.watchmaker.morphs.mono.swing;

import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.morphs.mono.MonochromeMorphConfig;
import net.richarddawkins.watchmaker.morphs.mono.geom.swing.SwingMonoPicDrawer;
import net.richarddawkins.watchmaker.swing.appdata.SimpleSwingAppData;
import net.richarddawkins.watchmaker.swing.appdata.SwingAppData;

public class MonochromeSwingAppData extends SimpleSwingAppData implements SwingAppData {
	public MonochromeSwingAppData() {
		setMenuBuilder(new MonochromeMenuBuilder(this));
		setSwingPicDrawer(new SwingMonoPicDrawer());
		config = new MonochromeMorphConfig();
	}
	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) {
		GeneBoxStrip geneBoxStrip = new SwingMonochromeGeneBoxStrip();
		geneBoxStrip.setEngineeringMode(engineeringMode);
		return geneBoxStrip;
	}

}
