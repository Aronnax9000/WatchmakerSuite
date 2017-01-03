package net.richarddawkins.watchmaker.genome;

import net.richarddawkins.watchmaker.morph.common.Morph;

public interface Genome {
	Morph getMorph();
	Genome reproduce(Morph morph);
	void setBasicType(int i);
	void setMorph(Morph simpleMorphImpl);
	

}
