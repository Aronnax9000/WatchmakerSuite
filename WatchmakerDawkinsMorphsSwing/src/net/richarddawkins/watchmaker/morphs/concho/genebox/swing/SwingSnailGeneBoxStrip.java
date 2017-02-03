package net.richarddawkins.watchmaker.morphs.concho.genebox.swing;

import net.richarddawkins.watchmaker.genebox.GeneBox;
import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.morphs.concho.genome.DoubleGene;
import net.richarddawkins.watchmaker.morphs.concho.genome.HandednessGene;
import net.richarddawkins.watchmaker.swing.geneboxstrip.SwingGeneBoxStrip;

public class SwingSnailGeneBoxStrip extends SwingGeneBoxStrip {

	private static final long serialVersionUID = 1L;

	public GeneBox getGeneBoxForGene(Gene gene) {
		if (gene instanceof DoubleGene)
			return new SwingDoubleGeneBox();
		else if (gene instanceof HandednessGene)
			return new SwingHandednessGeneBox();
		else
			return super.getGeneBoxForGene(gene);

	}

	public SwingSnailGeneBoxStrip() {
	}

}
