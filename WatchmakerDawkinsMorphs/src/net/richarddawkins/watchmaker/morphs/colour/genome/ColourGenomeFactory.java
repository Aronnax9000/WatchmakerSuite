package net.richarddawkins.watchmaker.morphs.colour.genome;

import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.genome.mutation.AllowedMutations;
import net.richarddawkins.watchmaker.genome.mutation.Random;
import net.richarddawkins.watchmaker.morphs.colour.genome.mutation.ColourAllowedMutations;
import net.richarddawkins.watchmaker.morphs.colour.genome.mutation.ColourMutagen;
import net.richarddawkins.watchmaker.morphs.mono.Biomorph;
import net.richarddawkins.watchmaker.morphs.mono.genome.BiomorphGenomeFactory;
import net.richarddawkins.watchmaker.morphs.mono.genome.CompletenessGene;
import net.richarddawkins.watchmaker.morphs.mono.genome.IntegerGradientGene;
import net.richarddawkins.watchmaker.morphs.mono.genome.SpokesGene;
import net.richarddawkins.watchmaker.morphs.mono.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.morphs.mono.genome.type.SpokesType;
import net.richarddawkins.watchmaker.morphs.mono.genome.type.SwellType;

public class ColourGenomeFactory extends BiomorphGenomeFactory {
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

		ColourAllowedMutations muts = (ColourAllowedMutations) this.muts;
		IntegerGene segNoGene = genome.getSegNoGene();
		IntegerGradientGene segDistGene = genome.getSegDistGene();
		if (muts.getMut(0)) {
			// if Mut[1] then
			// begin
			segNoGene.setValue(Random.randInt(6));
			segDistGene.setValue(Random.randInt(20));

		} else {
			segNoGene.setValue(1);
			segDistGene.setValue(1);
		}
		int r = Random.randInt(100);

		CompletenessGene completenessGene = genome.getCompletenessGene();

		if (muts.getMut(2)) {
			if (r < 50) {
				completenessGene.setValue(CompletenessType.Single);
			} else {
				completenessGene.setValue(CompletenessType.Double);
			}
		} else {
			// Dawkins did this no matter what, above the top of the enclosing
			// if.
			completenessGene.setValue(CompletenessType.Double);
		}
		r = Random.randInt(100);
		SpokesGene spokesGene = genome.getSpokesGene();
		if (muts.getMut(3)) {
			if (r < 33) {
				spokesGene.setValue(SpokesType.Radial);
			} else if (r < 66) {
				spokesGene.setValue(SpokesType.NSouth);
			} else {
				spokesGene.setValue(SpokesType.NorthOnly);
			}
		} else {
			spokesGene.setValue(SpokesType.NorthOnly);
		}

		IntegerGene trickleGene = genome.getTrickleGene();

		if (muts.getMut(4)) {
			int newTrickleGeneValue = 1 + Random.randInt(100) / 10;
			trickleGene.setValue(newTrickleGeneValue);
			if (trickleGene.getValue() > 1) {
				int newMutSizeGeneValue = trickleGene.getValue() / 2;
				genome.getMutSizeGene().setValue(newMutSizeGeneValue);
			} else {
				logger.warning("MutSizeGene not set by deliverSaltation.");
			}
		}
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
		int maxGene;
		IntegerGradientGene gene = null;
		for (int j = 0; j < 8; j++) {

			gene = (IntegerGradientGene) genome.getGene(j);
			do {
				if (muts.getMut(12)) {
					gene.setValue(genome.getMutSizeGene().getValue() * (Random.randInt(19) - 10));
				}
				if (muts.getMut(1)) {
					gene.setGradient(ColourMutagen.randSwell(gene.getGradient()));
				} else {
					gene.setGradient(SwellType.Same);
				}
				int factor = 0;
				switch (((IntegerGradientGene) genome.getGene(j)).getGradient()) {
				case Shrink:
					factor = 1;
					break;
				case Same:
					factor = 0;
					break;
				case Swell:
					factor = 1;
					break;
				default:
				}
				maxGene = gene.getValue() * segNoGene.getValue() * factor;
			} while (!((maxGene <= 9 * trickleGene.getValue()) && (maxGene >= -9 * trickleGene.getValue())));


		}


		int counter = 0;
		do {

			if (muts.getMut(1)) {
				segDistGene.setGradient(ColourMutagen.randSwell(segDistGene.getGradient()));
			} else {
				segDistGene.setGradient(SwellType.Same);
			}
			int factor = 0;
			// Correcting what I think is a bug in original Blind Watchmaker, which
			// references dGene[j] following the for loop above. -- ABC  
			switch (segDistGene.getGradient()) { 
			case Shrink:
				factor = 1;
				break;
			case Same:
				factor = 0;
				break;
			case Swell:
				factor = 1;
				break;
			default:
			}
			maxGene = segDistGene.getValue() * segNoGene.getValue() * factor;

			if (counter++ > 1000) {
				logger.warning("Breaking after 1000 attempts. SegDistGene:" + segDistGene.getValue() + " SegNoGene:"
						+ segNoGene.getValue() + " factor:" + factor + " maxGene:" + maxGene);

				break;
			}
		} while (!((maxGene <= 100) && (maxGene >= -100)));
		IntegerGradientGene gene9 = genome.getGene9();
		gene9.setValue(Random.randInt(5) + 1); // 2..6
		gene9.setGradient(SwellType.Same);
		logger.fine("ColourGenomeFactory.deliverSaltation():" + genome.toString());
		return genome;
	}

}
