package net.richarddawkins.watchmaker.swing.breed;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.swing.app.SwingAppData;
import net.richarddawkins.watchmaker.swing.components.GeneBoxyWatchmakerPanel;

public class BreedingWatchmakerPanel extends GeneBoxyWatchmakerPanel {

	
	private static final long serialVersionUID = -5445629768562940527L;
    public BreedingWatchmakerPanel(SwingAppData simpleSwingAppData, Morph morph) {
       	super(simpleSwingAppData, 
       			"IconFlipBirdToBreedingGrid_ICON_00261_32x32", 
       			"Breeding",
       			false);
       	BreedingPanel breedingPanel = new BreedingPanel(this);
       	setCentrePanel(breedingPanel);
       	
       	breedingPanel.seed(morph);
    }
    @Override
    public Morph getMorphOfTheHour() {
    	BreedingPanel breedingPanel =  (BreedingPanel) centrePanel;
    	return breedingPanel.getBoxedMorphVector()
    			.getBoxedMorph(breedingPanel.getBoxesDrawer().midBox)
    			.getMorph();
    }
}
