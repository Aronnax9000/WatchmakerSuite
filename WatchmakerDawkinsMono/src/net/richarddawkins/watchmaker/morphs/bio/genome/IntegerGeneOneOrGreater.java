package net.richarddawkins.watchmaker.morphs.bio.genome;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;

public class IntegerGeneOneOrGreater extends IntegerGene {

	public IntegerGeneOneOrGreater(Genome genome, String name) {
		super(genome, name);
	}

	public IntegerGeneOneOrGreater(Genome genome, String name, int value) {
		super(genome, name);
		setValue(value);
	}

	@Override
	public void setValue(int newValue) {
		if (newValue < 1)
			newValue = 1;
		super.setValue(newValue);
	}
}
