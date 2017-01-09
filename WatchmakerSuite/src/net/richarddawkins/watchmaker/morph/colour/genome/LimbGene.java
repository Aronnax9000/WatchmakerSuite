package net.richarddawkins.watchmaker.morph.colour.genome;

import net.richarddawkins.watchmaker.genome.SimpleGene;

public class LimbGene extends SimpleGene {
	protected LimbType value;

	public LimbType getValue() {
		return value;
	}

	public void setValue(LimbType value) {
		this.value = value;
	}
}
