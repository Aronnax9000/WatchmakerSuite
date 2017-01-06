package net.richarddawkins.watchmaker.genome;

import java.awt.Dimension;
import java.awt.Graphics2D;

import net.richarddawkins.watchmaker.morph.Morph;

public interface Genome {
	Morph getMorph();
	Genome reproduce(Morph morph);
	void setBasicType(int i);
	void setMorph(Morph simpleMorphImpl);
	
	/**
	 * Attempt to mutate the genome represented by this Person.
	 * 
	 * @return true if the mutation was successful.
	 */

	void develop(Graphics2D g2, Dimension d, boolean zeroMargin);
	
}
