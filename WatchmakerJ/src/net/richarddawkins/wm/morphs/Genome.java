package net.richarddawkins.wm.morphs;

import java.awt.Dimension;
import java.awt.Graphics2D;

public interface Genome {
	Morph getMorph();
	Genome reproduce(Morph morph);
	/**
	 * Attempt to mutate the genome represented by this Person.
	 * 
	 * @return true if the mutation was successful.
	 */

	void setBasicType(int i);
	void develop(Graphics2D g2, Dimension d, boolean zeroMargin);
	void setMorph(Morph simpleMorphImpl);
	

}
