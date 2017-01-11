package net.richarddawkins.watchmaker.genome;

import net.richarddawkins.watchmaker.morph.biomorph.genome.CompletenessType;

public class CompletenessGene extends SimpleGene {
	protected CompletenessType completenessType;
	public void setValue(CompletenessType completenessType) {
		this.completenessType = completenessType;
	}
	public CompletenessType getValue() {
		return completenessType;
	}
}
