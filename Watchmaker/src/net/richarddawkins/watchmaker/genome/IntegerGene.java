package net.richarddawkins.watchmaker.genome;

public class IntegerGene extends NumericGene {

	
	protected int value;

	public IntegerGene(Genome genome, String name) {
		super(genome, name);
	}
	
	public int getValue() {
		return value;
	}

	public void decrementGene() {
		setValue(value - 1);
	}

	public void incrementGene() {
		setValue(value + 1);
	}
	
	
	public void setValue(int newValue) {
		int oldValue = this.value;
		this.value = newValue;
		pcs.firePropertyChange("value", oldValue, newValue);
	}

	@Override
	public void copy(Gene destinationGene) {
		super.copy((NumericGene) destinationGene);
		((IntegerGene)destinationGene).setValue(getValue());
	}


	public void addToGene(int summand) {
		int newValue = this.value + summand;
		setValue(newValue);
	}

	@Override
	public void geneManipulated(GeneManipulationEvent gbme) {
		GooseDirection direction = gbme.getGooseDirection();
		if (direction.equals(GooseDirection.leftArrow)) {
			addToGene(-getGooseSize());
		} else if (direction.equals(GooseDirection.rightArrow)) {
			addToGene(getGooseSize());
		} 
	}
	
}
