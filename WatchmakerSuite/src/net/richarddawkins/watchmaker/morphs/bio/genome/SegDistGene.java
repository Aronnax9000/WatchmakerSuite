package net.richarddawkins.watchmaker.morphs.bio.genome;

import net.richarddawkins.watchmaker.genome.Genome;

public class SegDistGene extends Gene12345678 {

	public SegDistGene(Genome genome, String name) {
		super(genome, name);
	}

	@Override
    public int getGooseSize() {
		BiomorphGenome biomorphGenome = (BiomorphGenome) genome;
		return biomorphGenome.trickleGene.getValue();
	}

}
