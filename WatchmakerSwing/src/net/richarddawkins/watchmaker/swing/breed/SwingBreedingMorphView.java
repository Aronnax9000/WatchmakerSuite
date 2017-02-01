package net.richarddawkins.watchmaker.swing.breed;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.swing.app.SwingAppData;
import net.richarddawkins.watchmaker.swing.morphview.SwingGeneBoxyMorphView;

public class SwingBreedingMorphView extends SwingGeneBoxyMorphView {

	
	private static final long serialVersionUID = -5445629768562940527L;
    public SwingBreedingMorphView(SwingAppData simpleSwingAppData, Morph morph) {
       	super(simpleSwingAppData, 
       			"IconFlipBirdToBreedingGrid_ICON_00261_32x32", 
       			"Breeding",
       			false);
       	SwingBreedingBoxedMorphViewPanel breedingPanel = new SwingBreedingBoxedMorphViewPanel(this);
       	setCentrePanel(breedingPanel);
       	breedingPanel.seed(morph);
    }
    @Override
    public Morph getMorphOfTheHour() {
    	SwingBreedingBoxedMorphViewPanel breedingPanel =  (SwingBreedingBoxedMorphViewPanel) centrePanel;
    	return breedingPanel.getBoxedMorphVector()
    			.getBoxedMorph(breedingPanel.getBoxes().getMidBox())
    			.getMorph();
    }
}
