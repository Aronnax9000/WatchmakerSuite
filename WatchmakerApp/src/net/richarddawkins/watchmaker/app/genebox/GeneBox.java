package net.richarddawkins.watchmaker.app.genebox;

import net.richarddawkins.watchmaker.genome.Gene;

public interface GeneBox {

	Gene getGene();

	void setEngineeringMode(boolean engineeringMode);

	void setGene(Gene gene);
}