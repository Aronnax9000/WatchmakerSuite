package net.richarddawkins.watchmaker.morph.colour.genome;

import net.richarddawkins.watchmaker.genome.SimpleGene;

public class LimbGene extends SimpleGene {
	protected LimbType value;
	public LimbGene(String name) {
		super(name);
	}
	public LimbType getValue() {
		return value;
	}

	public void setValue(LimbType value) {
		this.value = value;
	}
}
