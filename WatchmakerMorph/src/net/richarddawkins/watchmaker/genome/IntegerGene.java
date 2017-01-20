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
	
	
	@Override
	public void setValue(int value) {
		int old = this.value;
		
		this.value = value;
		pcs.firePropertyChange("value", old, value);
	}

	@Override
	public void copy(Gene destinationGene) {
		super.copy((NumericGene) destinationGene);
		((NumericGene)destinationGene).setValue(getValue());
	}

	@Override
	void setValue(double value) {
		setValue(new Double(value).intValue());
		
	}

	public void addToGene(int summand) {
		int newValue = this.value + summand;
		setValue(newValue);
	}
	
	@Override
	public void goose(GooseDirection cursor) {
		if (cursor.equals(GooseDirection.leftArrow)) {
			addToGene(-getGooseSize());
		} else if (cursor.equals(GooseDirection.rightArrow)) {
			addToGene(getGooseSize());
		} 
	}


	
}
