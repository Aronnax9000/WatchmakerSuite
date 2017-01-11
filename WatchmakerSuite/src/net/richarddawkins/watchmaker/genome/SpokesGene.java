package net.richarddawkins.watchmaker.genome;

import net.richarddawkins.watchmaker.morph.biomorph.genome.SpokesType;

public class SpokesGene extends SimpleGene {
	protected SpokesType spokesType;
	public void setValue(SpokesType spokesType) {
		this.spokesType = spokesType;
	}
	public SpokesType getValue() {
		return spokesType;
	}
}
