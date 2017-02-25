package net.richarddawkins.watchmaker.morphs.mono.genebox.swing;

import net.richarddawkins.watchmaker.genebox.GeneBox;
import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.morphs.mono.genome.CompletenessGene;
import net.richarddawkins.watchmaker.morphs.mono.genome.IntegerGradientGene;
import net.richarddawkins.watchmaker.morphs.mono.genome.SpokesGene;
import net.richarddawkins.watchmaker.swing.genebox.SwingIntegerGeneBox;
import net.richarddawkins.watchmaker.swing.geneboxstrip.SwingGeneBoxStrip;

public class SwingMonochromeGeneBoxStrip extends SwingGeneBoxStrip {

	private static final long serialVersionUID = 1L;
    @Override
    public boolean isReusable() { return true; }

	public GeneBox getGeneBoxForGene(Gene gene) {
		if(gene instanceof IntegerGradientGene)
			return new SwingIntegerGradientGeneBox();
		else if(gene instanceof IntegerGene)
			return new SwingIntegerGeneBox();
		else if(gene instanceof CompletenessGene)
			return new SwingCompletenessGeneBox();
		else if(gene instanceof SpokesGene)
			return new SwingSpokesGeneBox();
		else
			return super.getGeneBoxForGene(gene);
	}
	
	
}
