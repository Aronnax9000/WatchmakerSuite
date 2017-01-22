package net.richarddawkins.watchmaker.morphs.arthro.genome.swing;

import net.richarddawkins.watchmaker.genebox.GeneBox;
import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.morphs.arthro.ArthromorphConfig;
import net.richarddawkins.watchmaker.morphs.arthro.genome.ArthromorphGenome;
import net.richarddawkins.watchmaker.swing.geneboxstrip.SwingGeneBoxStrip;

public class SwingArthromorphGeneBoxStrip extends SwingGeneBoxStrip {


	private static final long serialVersionUID = 550977251971215966L;
	protected ArthromorphConfig config;
	protected ArthromorphGenome genome;
	
	@Override
	public GeneBox getGeneBoxForGene(Gene gene) {
		return new SwingAtomGeneBox();
	}
	
	public SwingArthromorphGeneBoxStrip() {
		
	}

}