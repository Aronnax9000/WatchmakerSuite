package net.richarddawkins.watchmaker.morphs.concho.genome;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.GeneManipulationEvent;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GooseDirection;
import net.richarddawkins.watchmaker.genome.NumericGene;

public class DoubleGene extends NumericGene {

	public DoubleGene(Genome genome, String name) {
		super(genome, name);
	}
	
	public void addToGene(double summand) {
		setValue(this.value + summand);
	}

	double value;

	public double getValue() {
		return value;
	}

	public void setValue(double newValue) {
		double oldValue = this.value;
		this.value = newValue;
		pcs.firePropertyChange("value", oldValue, newValue);
	}

	@Override
	public void geneManipulated(GeneManipulationEvent gbme) {
		GooseDirection direction = gbme.getGooseDirection();
	}
	@Override
	public String toString() {
		return name + ":" + value;
	}
	@Override
	public void copy(Gene destinationGene) {
		((DoubleGene)destinationGene).setValue(getValue());
	}
}
