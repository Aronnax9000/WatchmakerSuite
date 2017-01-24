package net.richarddawkins.watchmaker.morphs.concho.genome;

import net.richarddawkins.watchmaker.genome.Genome;

public class Double0To1Gene extends DoubleGene {
	public Double0To1Gene(Genome genome, String name) {
		super(genome, name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Value is not allowed to be less than zero or greater than 1.
	 * 
	 * @param summand
	 *            the quantity to add.
	 */
	public void addToGene(double summand) {
		double oldValue = this.value;
		double newValue = oldValue + summand;

		if (newValue < 0)
			newValue = 0;
		if (newValue > 1)
			newValue = 1;
		
		setValue(newValue);
	}

}
