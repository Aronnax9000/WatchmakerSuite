package net.richarddawkins.watchmaker.morph.colour.genome;

import net.richarddawkins.watchmaker.genome.SimpleGene;

public class LimbFillGene extends SimpleGene {
	protected LimbFillType value;

	public LimbFillType getValue() {
		return value;
	}

	public void setValue(LimbFillType value) {
		this.value = value;
	}
}
