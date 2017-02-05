package net.richarddawkins.watchmaker.morphs.concho.genome;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morphs.concho.genome.mutation.SnailMutagen;

public class ShapeGene extends DoubleGene {

	public ShapeGene(Genome genome) {
		super(genome, "Shape");
	}
	@Override
	public double getDoubleGooseSize() {
		return SnailMutagen.SMUTSIZE;
	}
}
