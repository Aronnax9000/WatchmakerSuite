package net.richarddawkins.watchmaker.swing.components;

import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.swing.app.SwingAppData;

public class GeneBoxyWatchmakerPanel extends SwingBorderLayoutMorphView {


	private static final long serialVersionUID = 4916823549714431659L;

	public GeneBoxyWatchmakerPanel(SwingAppData swingAppData) {
		super(swingAppData);
	}

	public GeneBoxyWatchmakerPanel(
			SwingAppData appData, String icon, 
			String name, boolean engineeringMode) {
		super(appData, icon, name);
		GeneBoxStrip geneBoxStrip = appData.newGeneBoxStrip(engineeringMode);
		setUpperStrip(geneBoxStrip);
		SwingScaleSlider scaleSlider = new SwingScaleSlider();
		scaleSlider.addWatchmakerScaleListener(appData.getPhenotypeDrawer().getDrawingPreferences());
		setLowerStrip(scaleSlider);
	}

}