package net.richarddawkins.watchmaker.morph.biomorph.genome;

import net.richarddawkins.watchmaker.genome.IntegerGene;

public class IntegerGeneZeroOrGreater extends IntegerGene {

	public IntegerGeneZeroOrGreater(String name) {
		super(name);
	}

	@Override
	public void decrementGene() {
		if (this.value > 0) {
			setValue(this.value - 1);
		}
	}
	
	
}
