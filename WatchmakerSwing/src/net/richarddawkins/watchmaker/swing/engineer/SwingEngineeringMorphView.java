package net.richarddawkins.watchmaker.swing.engineer;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.swing.app.SwingAppData;
import net.richarddawkins.watchmaker.swing.components.SwingGeneBoxyMorphView;

public class SwingEngineeringMorphView extends SwingGeneBoxyMorphView {
	
	public SwingEngineeringMorphView(SwingAppData swingAppData, Morph morph) {
		super(swingAppData, "Hypodermic_PICT_03937_16x16", "Engineering", true);
		SwingEngineeringBoxedMorphViewPanel engineeringPanel = new SwingEngineeringBoxedMorphViewPanel(this);
		setCentrePanel(engineeringPanel);
		engineeringPanel.seed(morph);
	}

    @Override
    public Morph getMorphOfTheHour() {
    	SwingEngineeringBoxedMorphViewPanel engineeringPanel =  (SwingEngineeringBoxedMorphViewPanel) centrePanel;
    	return engineeringPanel.getBoxedMorphVector()
    			.getBoxedMorph(0)
    			.getMorph();
    }
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8224824610112892419L;

}
