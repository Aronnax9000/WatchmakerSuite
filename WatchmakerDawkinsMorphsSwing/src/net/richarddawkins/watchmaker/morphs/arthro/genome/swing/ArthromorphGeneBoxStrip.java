package net.richarddawkins.watchmaker.morphs.arthro.genome.swing;

import net.richarddawkins.watchmaker.morphs.arthro.ArthromorphConfig;
import net.richarddawkins.watchmaker.morphs.arthro.genome.ArthromorphGenome;
import net.richarddawkins.watchmaker.swing.appdata.SwingAppData;
import net.richarddawkins.watchmaker.swing.geneboxstrip.SimpleGeneBoxStrip;

public class ArthromorphGeneBoxStrip extends SimpleGeneBoxStrip {

	/**
	 * 
	 */
	private static final long serialVersionUID = 550977251971215966L;
	protected ArthromorphConfig config;
	protected ArthromorphGenome genome;
	
	public ArthromorphGeneBoxStrip(SwingAppData swingAppData, boolean engineeringMode) {
		super(swingAppData, engineeringMode);
	}

}
