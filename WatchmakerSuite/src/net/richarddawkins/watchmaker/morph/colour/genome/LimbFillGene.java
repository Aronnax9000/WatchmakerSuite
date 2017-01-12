package net.richarddawkins.watchmaker.morph.colour.genome;

import net.richarddawkins.watchmaker.genome.SimpleGene;

public class LimbFillGene extends SimpleGene {
	
	public LimbFillGene(String name) {
		super(name);
	}
	
	protected LimbFillType value;

	public LimbFillType getValue() {
		return value;
	}

	public void setValue(LimbFillType value) {
		this.value = value;
	}
}
