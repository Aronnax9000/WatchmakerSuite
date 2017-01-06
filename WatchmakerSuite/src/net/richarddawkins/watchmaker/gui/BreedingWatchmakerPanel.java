package net.richarddawkins.watchmaker.gui;

import net.richarddawkins.watchmaker.gui.breed.BreedingPanel;
import net.richarddawkins.watchmaker.morph.MorphConfig;

public class BreedingWatchmakerPanel extends WatchmakerPanel {
    /**
	 *  
	 */
	private static final long serialVersionUID = -5445629768562940527L;
    public BreedingWatchmakerPanel(MorphConfig config) {
       	super(config);
       	this.setIconFromFilename("IconFlipBirdToBreedingGrid_ICON_00261_32x32");
       	this.setName("Breeding");
    	setPageStartPanel(config.newGeneBoxStrip());
		BreedingPanel breedingPanel = new BreedingPanel(this);
		setCentrePanel(breedingPanel);
		breedingPanel.seed();
    }
}
