package net.richarddawkins.watchmaker.swing.engineer;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.swing.appdata.SwingAppData;
import net.richarddawkins.watchmaker.swing.components.GeneBoxyWatchmakerPanel;

public class EngineeringWatchmakerPanel extends GeneBoxyWatchmakerPanel {
	
	public EngineeringWatchmakerPanel(SwingAppData swingAppData, Morph morph) {
		super(swingAppData, "Hypodermic_PICT_03937_16x16", "Engineering", true);
		EngineeringPanel engineeringPanel = new EngineeringPanel(this);
		setCentrePanel(engineeringPanel);
		engineeringPanel.seed(morph);
	}

    @Override
    public Morph getMorphOfTheHour() {
    	EngineeringPanel engineeringPanel =  (EngineeringPanel) centrePanel;
    	return engineeringPanel.getBoxedMorphVector()
    			.getBoxedMorph(0)
    			.getMorph();
    }
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8224824610112892419L;

}
