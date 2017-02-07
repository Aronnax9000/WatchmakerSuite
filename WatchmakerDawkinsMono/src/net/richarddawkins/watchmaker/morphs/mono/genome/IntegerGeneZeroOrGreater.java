package net.richarddawkins.watchmaker.morphs.mono.genome;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;

public class IntegerGeneZeroOrGreater extends IntegerGene {

	public IntegerGeneZeroOrGreater(Genome genome, String name) {
		super(genome, name);
	}

	@Override
	public void decrementGene() {
		if (this.value > 0) {
			setValue(this.value - 1);
		}
	}
	
	
}
