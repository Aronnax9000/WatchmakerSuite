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
