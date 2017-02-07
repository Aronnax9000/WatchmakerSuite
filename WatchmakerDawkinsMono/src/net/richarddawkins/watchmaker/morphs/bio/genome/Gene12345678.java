package net.richarddawkins.watchmaker.morphs.bio.genome;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morphs.mono.genome.MonochromeGenome;

public class Gene12345678 extends IntegerGradientGene {
	public Gene12345678(Genome genome, String name) {
		super(genome, name);

	}

	@Override
	public int getGooseSize() {
		MonochromeGenome MonochromeGenome = (MonochromeGenome) genome;
		return MonochromeGenome.getMutSizeGene().getValue();
	}

}
