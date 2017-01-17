package net.richarddawkins.watchmaker.genome.gui;

import net.richarddawkins.watchmaker.genome.Gene;

public interface GeneBox {

	Gene getGene();

	GeneBoxStrip getGeneBoxStrip();

	void setEngineeringMode(boolean engineeringMode);

	void setGene(Gene gene);

	void setGeneBoxStrip(GeneBoxStrip geneBoxStrip);

}