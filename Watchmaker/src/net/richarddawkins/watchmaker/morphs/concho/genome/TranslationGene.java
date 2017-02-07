package net.richarddawkins.watchmaker.morphs.concho.genome;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morphs.concho.genome.mutation.SnailMutagen;

public class TranslationGene extends DoubleGene {

	public TranslationGene(Genome genome) {
		super(genome, "Translation");
	}
	@Override
	public double getDoubleGooseSize() {
		return SnailMutagen.TMUTSIZE;
	}
}
