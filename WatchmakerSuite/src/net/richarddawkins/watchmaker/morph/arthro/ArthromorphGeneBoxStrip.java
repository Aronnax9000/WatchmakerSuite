package net.richarddawkins.watchmaker.morph.arthro;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.gui.GeneBoxStrip;
import net.richarddawkins.watchmaker.morph.MorphConfig;

public class ArthromorphGeneBoxStrip extends GeneBoxStrip {

	/**
	 * 
	 */
	private static final long serialVersionUID = 550977251971215966L;
	protected ArthromorphConfig config;
	protected ArthromorphGenome genome;
	
	public ArthromorphGeneBoxStrip(ArthromorphConfig arthromorphConfig, boolean engineeringMode) {
		this.engineeringMode = engineeringMode;
	}
	
	@Override
	public void setGenome(Genome genome) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Genome getGenome() {
		// TODO Auto-generated method stub
		return genome;
	}

	@Override
	public void setMorphConfig(MorphConfig morphConfig) {
		this.config = (ArthromorphConfig) morphConfig;
		
	}



}
