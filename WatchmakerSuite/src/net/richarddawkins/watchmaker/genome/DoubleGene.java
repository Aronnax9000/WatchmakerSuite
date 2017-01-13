package net.richarddawkins.watchmaker.genome;

import java.awt.Cursor;

public class DoubleGene extends NumericGene {

	public DoubleGene(Genome genome, String name) {
		super(genome, name);
	}

	double value;

	public double getValue() {
		return value;
	}
	@Override
	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public void setValue(int value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void goose(Cursor cursor) {
		// TODO Auto-generated method stub
		
	}
    
}
