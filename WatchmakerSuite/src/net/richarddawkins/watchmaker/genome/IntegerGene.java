package net.richarddawkins.watchmaker.genome;

public class IntegerGene extends NumericGene {

	
	protected int value;

	public IntegerGene(String name) {
		super(name);
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void copy(IntegerGene destinationGene) {
		super.copy((NumericGene) destinationGene);
		destinationGene.setValue(getValue());
		
	}
	
}
