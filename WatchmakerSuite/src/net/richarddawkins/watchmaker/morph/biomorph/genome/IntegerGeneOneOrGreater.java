package net.richarddawkins.watchmaker.morph.biomorph.genome;

import net.richarddawkins.watchmaker.genome.IntegerGene;

public class IntegerGeneOneOrGreater extends IntegerGene {

	public IntegerGeneOneOrGreater(String name) {
		super(name);
	}

	public IntegerGeneOneOrGreater(String name, int value) {
		super(name);
		this.value = value;
	}

	@Override
	public void decrementGene() {
		int newValue = this.value - 1;
		
		if (newValue < 1)
			newValue = 1;
		setValue(newValue);
	}
	
	
	/** New value is not allowed to fall below 1. 
	 * @param summand the quantity to add to the MutSizeGene 
	 */
	@Override
	public void addToGene(int summand) {
		int newValue = this.value + summand;
		
		if (newValue < 1)
			newValue = 1;
		setValue(newValue);
	}
}
