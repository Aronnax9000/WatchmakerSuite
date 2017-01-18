package net.richarddawkins.watchmaker.swing.genebox;

import net.richarddawkins.watchmaker.genome.Gene;

public interface GeneBox {

	Gene getGene();

	void setEngineeringMode(boolean engineeringMode);

	void setGene(Gene gene);


}