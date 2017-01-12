package net.richarddawkins.watchmaker.genome;

public class DoubleGene extends NumericGene {

	public DoubleGene(String name) {
		super(name);
	}

	double value;

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
    
}
