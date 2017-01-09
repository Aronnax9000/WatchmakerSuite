package net.richarddawkins.watchmaker.morph.biomorph.genome;

import net.richarddawkins.watchmaker.genome.SimpleGene;

public class CompletenessGene extends SimpleGene {
    protected CompletenessType value;

	public CompletenessType getValue() {
		return value;
	}

	public void setValue(CompletenessType value) {
		this.value = value;
	}
}
