package net.richarddawkins.watchmaker.morphs.mono.swing;

import net.richarddawkins.watchmaker.morphs.mono.MonochromeMorphConfig;
import net.richarddawkins.watchmaker.morphs.mono.geom.swing.SwingMonoPicDrawer;
import net.richarddawkins.watchmaker.swing.appdata.SimpleSwingAppData;
import net.richarddawkins.watchmaker.swing.appdata.SwingAppData;
import net.richarddawkins.watchmaker.swing.geneboxstrip.GeneBoxStrip;

public class MonochromeSwingAppData extends SimpleSwingAppData implements SwingAppData {
	public MonochromeSwingAppData() {
		setMenuBuilder(new MonochromeMenuBuilder(this));
		setSwingPicDrawer(new SwingMonoPicDrawer());
		config = new MonochromeMorphConfig();
	}
	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) {

		return new MonochromeGeneBoxStrip(this, engineeringMode);
	}

}
