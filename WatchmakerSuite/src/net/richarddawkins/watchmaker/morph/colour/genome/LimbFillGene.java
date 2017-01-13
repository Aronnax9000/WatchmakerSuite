package net.richarddawkins.watchmaker.morph.colour.genome;

import java.awt.Cursor;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.SimpleGene;
import net.richarddawkins.watchmaker.morph.colour.genome.type.LimbFillType;

public class LimbFillGene extends SimpleGene {
	
	public LimbFillGene(Genome genome, String name) {
		super(genome, name);
	}
	
	public LimbFillGene(Genome genome, String name, LimbFillType value) {
		super(genome, name);
		this.value = value;
	}

	protected LimbFillType value;

	public LimbFillType getValue() {
		return value;
	}

	public void setValue(LimbFillType value) {
		this.value = value;
	}

	@Override
	public void goose(Cursor cursor) {
		// TODO Auto-generated method stub
		
	}
}
