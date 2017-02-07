package net.richarddawkins.watchmaker.morphs.bio.genome;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;

public class IntegerGene1To100 extends IntegerGene {

	public IntegerGene1To100(Genome genome, String name) {
		super(genome, name);
	}
	
	@Override
	public void incrementGene() {
		setValue(this.value + 1);
	}
	@Override
	public void decrementGene() {
		setValue(this.value - 1);
	}
	/**
	 * Add a quantity to the gene. If the the result is less than one, it
	 * is set to one. If the result is greater than 100, it is set to 100.
	 * @param summand the quantity to add to the gene.
	 */
	@Override
	public void addToGene(int summand) {
		setValue(this.value + summand);
	}

	@Override
	public void setValue(int newValue) {
		if (newValue < 1)
			newValue = 1;
		if (newValue > 100)
			newValue = 100;
		super.setValue(newValue);
	}
}
