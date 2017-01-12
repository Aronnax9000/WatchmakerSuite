package net.richarddawkins.watchmaker.morph.biomorph.genome;

import net.richarddawkins.watchmaker.genome.SimpleGene;

public class CompletenessGene extends SimpleGene {
	public CompletenessGene(String name) {
		super(name);
	}

	
    protected CompletenessType value;

	public CompletenessType getValue() {
		return value;
	}

	public void setValue(CompletenessType value) {
		this.value = value;
	}
}
