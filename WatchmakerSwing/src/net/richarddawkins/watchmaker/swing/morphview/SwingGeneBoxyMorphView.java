package net.richarddawkins.watchmaker.swing.morphview;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.swing.components.SwingScaleSlider;

public class SwingGeneBoxyMorphView extends SwingBorderLayoutMorphView {


	private static final long serialVersionUID = 4916823549714431659L;

	public SwingGeneBoxyMorphView(AppData appData) {
		super(appData);
	}

	public SwingGeneBoxyMorphView(
			AppData appData, String icon, 
			String name, boolean engineeringMode) {
		super(appData, icon, name);
		GeneBoxStrip geneBoxStrip = appData.newGeneBoxStrip(engineeringMode);
		setUpperStrip(geneBoxStrip);
		SwingScaleSlider scaleSlider = new SwingScaleSlider(appData.getPhenotypeDrawer().getDrawingPreferences());
		setLowerStrip(scaleSlider);
	}

}