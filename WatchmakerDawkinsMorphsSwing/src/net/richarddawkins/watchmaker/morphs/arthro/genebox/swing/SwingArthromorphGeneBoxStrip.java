package net.richarddawkins.watchmaker.morphs.arthro.genebox.swing;

import java.awt.GridBagConstraints;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genebox.GeneBox;
import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.morphs.arthro.genome.Atom;
import net.richarddawkins.watchmaker.swing.geneboxstrip.SwingGeneBoxStrip;

public class SwingArthromorphGeneBoxStrip extends SwingGeneBoxStrip {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morphs.arthro.genome.swing.SwingArthromorphGeneBoxStrip");

	private static final long serialVersionUID = 550977251971215966L;

	protected void applyGeneSpecificConstraints(GridBagConstraints constraints, Gene gene) {
		constraints.insets.left = 10 * ((Atom)gene).depthBelow((Atom)gene.getGenome().getGene(0), (Atom) gene);
	}
	
	@Override
	public GeneBox getGeneBoxForGene(Gene gene) {
		return new SwingAtomGeneBox();
	}
}
