package net.richarddawkins.watchmaker.genebox;

import java.beans.PropertyChangeListener;

import net.richarddawkins.watchmaker.genome.Gene;

public interface GeneBox extends PropertyChangeListener {

	Gene getGene();

	void setGene(Gene gene);
	
	public void setEngineeringMode();
	
	public void setText(String text);
	
}