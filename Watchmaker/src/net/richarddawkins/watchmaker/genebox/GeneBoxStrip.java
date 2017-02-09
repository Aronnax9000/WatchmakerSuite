package net.richarddawkins.watchmaker.genebox;

import java.beans.PropertyChangeListener;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morphview.MorphViewWidget;

public interface GeneBoxStrip extends MorphViewWidget, PropertyChangeListener {

	void setGenome(Genome genome);
	
	GeneBox getGeneBoxForGene(Gene gene);

	void setEngineeringMode(boolean engineeringMode);

	void setGeneBoxToSide(boolean geneBoxToSide);
	boolean isGeneBoxToSide();
}
