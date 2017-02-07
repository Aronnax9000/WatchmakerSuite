package net.richarddawkins.watchmaker.morphs.colour.genome;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;

public class ColorGene extends IntegerGene {


	public ColorGene(Genome genome, String name) {
		super(genome, name);
		this.value = ColourGenome.RAINBOW / 2;
		
	}

	public ColorGene(Genome genome, String name, int i) {
		this(genome, name);
		setValue(i);
	}

	@Override
	public void addToGene(int summand) {
		setValue(this.value + summand);
	}

	@Override
	public void setValue(int newValue) {
		if(newValue > ColourGenome.RAINBOW - 1)
			newValue = ColourGenome.RAINBOW - 1;
		else if(newValue < 0)
			newValue = 0;
		super.setValue(newValue);
	}	
}
