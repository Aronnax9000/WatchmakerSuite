package net.richarddawkins.watchmaker.morph.biomorph.genome;

import net.richarddawkins.watchmaker.genome.SimpleGene;

public class SpokesGene extends SimpleGene {
	protected SpokesType value;
	public SpokesGene(String name) {
		super(name);
	}
	public SpokesType getValue() {
		return value;
	}

	public void setValue(SpokesType value) {
		this.value = value;
	}
	
}
