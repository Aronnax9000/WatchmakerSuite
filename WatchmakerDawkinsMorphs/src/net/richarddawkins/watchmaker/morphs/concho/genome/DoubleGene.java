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
		double newValue = this.value + summand;
		setValue(newValue);
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
		if (direction.equals(GooseDirection.leftArrow)) {
			addToGene(-getDoubleGooseSize());
		} else if (direction.equals(GooseDirection.rightArrow)) {
			addToGene(getDoubleGooseSize());
		} 
	}
	@Override
	public String toString() {
		return name + ":" + value;
	}
	@Override
	public void copy(Gene destinationGene) {
		((DoubleGene)destinationGene).setValue(getValue());
	}


    @Override
    public boolean genomicallyEquals(Gene gene) {
        if(! (gene instanceof DoubleGene)) return false;
        DoubleGene that = (DoubleGene) gene;
        if(this.value != that.getValue()) return false;
        return true;
    }
}
