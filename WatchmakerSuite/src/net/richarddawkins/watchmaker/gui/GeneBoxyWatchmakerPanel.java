package net.richarddawkins.watchmaker.gui;

import net.richarddawkins.watchmaker.morph.MorphConfig;

public class GeneBoxyWatchmakerPanel extends WatchmakerPanel {


	private static final long serialVersionUID = 4916823549714431659L;

	public GeneBoxyWatchmakerPanel(MorphConfig config) {
		super(config);
	}

	public GeneBoxyWatchmakerPanel(
			MorphConfig config, String icon, 
			String name, boolean engineeringMode) {
		super(config, icon, name);
		setPageStartPanel(config.newGeneBoxStrip(engineeringMode));
	}

}