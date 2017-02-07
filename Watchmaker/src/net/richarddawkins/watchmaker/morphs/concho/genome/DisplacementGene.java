package net.richarddawkins.watchmaker.morphs.concho.genome;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morphs.concho.genome.mutation.SnailMutagen;

public class DisplacementGene extends Double0To1Gene {

	public DisplacementGene(Genome genome) {
		super(genome, "Displacement");
	}

	@Override
	public double getDoubleGooseSize() {
		return SnailMutagen.DMUTSIZE;
	}
}
