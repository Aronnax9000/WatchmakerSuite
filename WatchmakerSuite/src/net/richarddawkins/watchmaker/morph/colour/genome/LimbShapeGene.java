package net.richarddawkins.watchmaker.morph.colour.genome;

import java.awt.Cursor;

import net.richarddawkins.watchmaker.genome.SimpleGene;
import net.richarddawkins.watchmaker.morph.colour.genome.type.LimbShapeType;

public class LimbShapeGene extends SimpleGene {
	protected LimbShapeType value;
	public LimbShapeGene(String name) {
		super(name);
	}
	public LimbShapeGene(String name, LimbShapeType value) {
		this(name);
		this.value = value;
	}
	public LimbShapeType getValue() {
		return value;
	}

	public void setValue(LimbShapeType value) {
		this.value = value;
	}
	@Override
	public void goose(Cursor cursor) {
		// TODO Auto-generated method stub
		
	}
}
