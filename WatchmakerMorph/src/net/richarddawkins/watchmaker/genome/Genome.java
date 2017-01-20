package net.richarddawkins.watchmaker.genome;

import java.beans.PropertyChangeListener;

import net.richarddawkins.watchmaker.morph.Morph;

public interface Genome extends PropertyChangeListener {
	Morph getMorph();
	Genome reproduce(Morph morph);
	void setBasicType(int i);
	void setMorph(Morph simpleMorphImpl);
	
	/**
	 * Attempt to mutate the genome represented by this Person.
	 */

	void develop();
	Gene[] toGeneArray();
	Gene getGene(int geneBoxIndex);
	void addPropertyChangeListener(PropertyChangeListener listener);
	void removePropertyChangeListener(PropertyChangeListener listener);
	
	void copy(Genome genome);
	
}
