package net.richarddawkins.watchmaker.swing.components;

import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.swing.appdata.SwingAppData;

public class GeneBoxyWatchmakerPanel extends SwingBorderLayoutMorphView {


	private static final long serialVersionUID = 4916823549714431659L;

	public GeneBoxyWatchmakerPanel(SwingAppData swingAppData) {
		super(swingAppData);
	}

	public GeneBoxyWatchmakerPanel(
			SwingAppData swingAppData, String icon, 
			String name, boolean engineeringMode) {
		super(swingAppData, icon, name);
		GeneBoxStrip geneBoxStrip = swingAppData.newGeneBoxStrip(engineeringMode);
		setUpperStrip(geneBoxStrip);
	}

}