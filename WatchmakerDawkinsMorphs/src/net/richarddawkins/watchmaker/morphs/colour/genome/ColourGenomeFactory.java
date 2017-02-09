package net.richarddawkins.watchmaker.morphs.colour.genome;

import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genome.mutation.AllowedMutations;
import net.richarddawkins.watchmaker.genome.mutation.Random;
import net.richarddawkins.watchmaker.morphs.colour.genome.mutation.ColourAllowedMutations;
import net.richarddawkins.watchmaker.morphs.colour.genome.mutation.ColourMutagen;
import net.richarddawkins.watchmaker.morphs.mono.Biomorph;
import net.richarddawkins.watchmaker.morphs.mono.genome.MonochromeGenomeFactory;

public class ColourGenomeFactory extends MonochromeGenomeFactory {
	private static Logger logger = Logger
			.getLogger("net.richarddawkins.watchmaker.morphs.colour.genome.ColourGenomeFactory");

	public ColourGenomeFactory(AllowedMutations muts) {
		super(muts);
	}

	public void makeGenes(ColourGenome genome, int a, int b, int c, int d, int e, int f, int g, int h, int i) {
		super.makeGenes(genome, a, b, c, d, e, f, g, h, i);
		genome.getSegDistGene().setValue(1);
	}

	@Override
	public ColourGenome basicTree() {
		ColourGenome genome = new ColourGenome();
		makeGenes(genome, -Biomorph.TRICKLE, -Biomorph.TRICKLE, -Biomorph.TRICKLE, -Biomorph.TRICKLE, -Biomorph.TRICKLE,
				0, Biomorph.TRICKLE, Biomorph.TRICKLE, 6);
		return genome;
	}

	@Override
	public ColourGenome chess() {
		ColourGenome genome = new ColourGenome();
		makeGenes(genome, -Biomorph.TRICKLE, 3 * Biomorph.TRICKLE, -3 * Biomorph.TRICKLE, -3 * Biomorph.TRICKLE,
				Biomorph.TRICKLE, -2 * Biomorph.TRICKLE, 6 * Biomorph.TRICKLE, -5 * Biomorph.TRICKLE, 7);
		return genome;
	}

	@Override
	protected ColourGenome insect() {
		ColourGenome genome = new ColourGenome();
		makeGenes(genome, Biomorph.TRICKLE, Biomorph.TRICKLE, -4 * Biomorph.TRICKLE, Biomorph.TRICKLE,
				-Biomorph.TRICKLE, -2 * Biomorph.TRICKLE, 8 * Biomorph.TRICKLE, -4 * Biomorph.TRICKLE, 6);
		return genome;
	}

	@Override
	public ColourGenome deliverSaltation() {

		ColourGenome genome = new ColourGenome();
		salt(genome);
		ColourAllowedMutations muts = (ColourAllowedMutations) this.muts;
		if (muts.getMut(9)) {
			ColourMutagen.randomForeColour(genome);
		}
		if (muts.getMut(7)) {
			genome.getLimbShapeGene().setValue(ColourMutagen.randLimbType());
		}
		if (muts.getMut(8)) {
			genome.getLimbFillGene().setValue(ColourMutagen.randLimbFill());
		}
		if (muts.getMut(10)) {
			ColourMutagen.randomBackColour(genome);
		}
		if (muts.getMut(11)) {
			genome.getThicknessGene().setValue(Random.randInt(3));
		}
		logger.fine("ColourGenomeFactory.deliverSaltation():" + genome.toString());
		return genome;
	}

}
