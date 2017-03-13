package net.richarddawkins.watchmaker.morphs.concho.genebox.swing;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.genebox.GeneBox;
import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.morphs.concho.genome.DoubleGene;
import net.richarddawkins.watchmaker.morphs.concho.genome.HandednessGene;
import net.richarddawkins.watchmaker.swing.geneboxstrip.SwingGeneBoxStrip;

public class SwingSnailGeneBoxStrip extends SwingGeneBoxStrip {


    @Override
    public boolean isReusable() { return true; }

	public GeneBox getGeneBoxForGene(Gene gene) {
		if (gene instanceof DoubleGene)
			return new SwingDoubleGeneBox(appData);
		else if (gene instanceof HandednessGene)
			return new SwingHandednessGeneBox(appData);
		else
			return super.getGeneBoxForGene(gene, appData);

	}

	public SwingSnailGeneBoxStrip(AppData appData) {
	    super(appData);
	}

}
