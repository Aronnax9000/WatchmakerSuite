package net.richarddawkins.watchmaker.morphs.mono.genebox.swing;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.genebox.GeneBox;
import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.morphs.mono.genome.CompletenessGene;
import net.richarddawkins.watchmaker.morphs.mono.genome.IntegerGradientGene;
import net.richarddawkins.watchmaker.morphs.mono.genome.SpokesGene;
import net.richarddawkins.watchmaker.swing.genebox.SwingIntegerGeneBox;
import net.richarddawkins.watchmaker.swing.geneboxstrip.SwingGeneBoxStrip;

public class SwingMonochromeGeneBoxStrip extends SwingGeneBoxStrip {

	public SwingMonochromeGeneBoxStrip(AppData appData) {
        super(appData);
    }


    @Override
    public boolean isReusable() { return true; }

	@Override
    public GeneBox getGeneBoxForGene(Gene gene, AppData appData) {
		if(gene instanceof IntegerGradientGene)
			return new SwingIntegerGradientGeneBox(appData);
		else if(gene instanceof IntegerGene)
			return new SwingIntegerGeneBox(appData);
		else if(gene instanceof CompletenessGene)
			return new SwingCompletenessGeneBox(appData);
		else if(gene instanceof SpokesGene)
			return new SwingSpokesGeneBox(appData);
		else
			return super.getGeneBoxForGene(gene, appData);
	}
	
	
}
