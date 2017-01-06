package net.richarddawkins.watchmaker.gui;

import net.richarddawkins.watchmaker.gui.breed.BreedingPanel;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;

public class BreedingWatchmakerPanel extends GeneBoxyWatchmakerPanel {

	
	private static final long serialVersionUID = -5445629768562940527L;
    public BreedingWatchmakerPanel(MorphConfig config, Morph morph) {
       	super(config, 
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
