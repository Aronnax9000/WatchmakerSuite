package net.richarddawkins.watchmaker.genome;

import java.awt.Cursor;

import net.richarddawkins.watchmaker.resourceloader.WatchmakerCursors;

public class IntegerGene extends NumericGene {

	
	protected int value;

	public IntegerGene(String name) {
		super(name);
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
	public void goose(Cursor cursor) {
		if (cursor.equals(WatchmakerCursors.leftArrow)) {
			addToGene(-getGooseSize());
		} else if (cursor.equals(WatchmakerCursors.rightArrow)) {
			addToGene(getGooseSize());
		} 
	}


	
}
