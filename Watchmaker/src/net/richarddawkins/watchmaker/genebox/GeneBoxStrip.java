package net.richarddawkins.watchmaker.genebox;

import java.beans.PropertyChangeListener;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morphview.MorphViewWidget;

public interface GeneBoxStrip extends MorphViewWidget, PropertyChangeListener {

	void setGenome(Genome genome);
	
	GeneBox getGeneBoxForGene(Gene gene, AppData appData);

	void setEngineeringMode(boolean engineeringMode);

	void setGeneBoxToSide(boolean geneBoxToSide);
	boolean isGeneBoxToSide();
	
	/**
	 * Some biomorph genomes have changeable structures, so that the gene box strip must be
	 * rebuilt from scratch each time it assigned a new genome.
	 * @return true or false, depending on whether this genebox should be rebuilt from scratch for
	 * each new genome.
	 */
	boolean isReusable();
}
