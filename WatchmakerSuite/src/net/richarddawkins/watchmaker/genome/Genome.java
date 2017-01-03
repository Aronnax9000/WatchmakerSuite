package net.richarddawkins.watchmaker.morph.common;

public interface Genome {
	Morph getMorph();
	Genome reproduce(Morph morph);
	void setBasicType(int i);
	void setMorph(Morph simpleMorphImpl);
	

}
