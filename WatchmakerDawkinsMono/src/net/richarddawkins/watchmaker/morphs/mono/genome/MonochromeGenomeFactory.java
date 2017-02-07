package net.richarddawkins.watchmaker.morphs.mono.genome;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.mutation.AllowedMutations;
import net.richarddawkins.watchmaker.morphs.bio.Biomorph;
import net.richarddawkins.watchmaker.morphs.bio.genome.BiomorphGenomeFactory;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SwellType;

public class MonochromeGenomeFactory extends BiomorphGenomeFactory {

	public MonochromeGenomeFactory(AllowedMutations muts) {
		super(muts);
	}
	@Override
	public Genome basicTree() {
		MonochromeGenome genome = new MonochromeGenome();
		makeGenes(genome, -10, -20, -20, -15, -15, 0, 15, 15, 7);
		genome.getSegNoGene().setValue(2);
		genome.getSegDistGene().setValue(150);
		genome.getCompletenessGene().setValue(CompletenessType.Single);
		genome.getGene4().setGradient(SwellType.Shrink);
		genome.getGene5().setGradient(SwellType.Shrink);
		genome.getGene6().setGradient(SwellType.Shrink);
		genome.getGene9().setGradient(SwellType.Shrink);
		genome.getTrickleGene().setValue(9);
		return genome;
	}
	@Override
	protected Genome chess() {
		MonochromeGenome genome = new MonochromeGenome();
		makeGenes(genome, -Biomorph.TRICKLE, 3 * Biomorph.TRICKLE, -3 * Biomorph.TRICKLE, -3 * Biomorph.TRICKLE,
				Biomorph.TRICKLE, -2 * Biomorph.TRICKLE, 6 * Biomorph.TRICKLE, -5 * Biomorph.TRICKLE, 7);
		return genome;
	}
	@Override
	protected Genome insect() {
		MonochromeGenome genome = new MonochromeGenome();
		makeGenes(genome, Biomorph.TRICKLE, Biomorph.TRICKLE, -4 * Biomorph.TRICKLE, Biomorph.TRICKLE, -Biomorph.TRICKLE,
				-2 * Biomorph.TRICKLE, 8 * Biomorph.TRICKLE, -4 * Biomorph.TRICKLE, 6);
		return genome;
	}

	protected void makeGenes(MonochromeGenome genome, int a, int b, int c, int d, int e, int f, int g, int h, int i) {
		super.makeGenes(genome, a, b, c, d, e, f, g, h, i);
		genome.getSegDistGene().setValue(150);
	}
	@Override
	public Genome deliverSaltation() {
		MonochromeGenome genome = new MonochromeGenome();
		return genome;
	}

}
