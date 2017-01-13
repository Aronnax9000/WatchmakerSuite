package net.richarddawkins.watchmaker.morph.colour.genome;

import net.richarddawkins.watchmaker.genome.IntegerGene;

public class ColorGene extends IntegerGene {


	public ColorGene(String name) {
		super(name);
		this.value = ColourGenome.RAINBOW / 2;
		
	}

	public ColorGene(String name, int i) {
		this(name);
		this.value = i;
	}

	@Override
	public void addToGene(int summand) {
		int newValue = this.value + summand;
		if(newValue > ColourGenome.RAINBOW)
			newValue = ColourGenome.RAINBOW;
		else if(newValue < 0)
			newValue = 0;
		setValue(newValue);
	}
	
}
