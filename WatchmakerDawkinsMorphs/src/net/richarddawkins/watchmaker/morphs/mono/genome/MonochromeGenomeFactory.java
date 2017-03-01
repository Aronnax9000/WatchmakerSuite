package net.richarddawkins.watchmaker.morphs.mono.genome;

import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.genome.mutation.AllowedMutations;
import net.richarddawkins.watchmaker.genome.mutation.Random;
import net.richarddawkins.watchmaker.morphs.colour.genome.mutation.ColourMutagen;
import net.richarddawkins.watchmaker.morphs.mono.Biomorph;
import net.richarddawkins.watchmaker.morphs.mono.genome.mutation.MonochromeAllowedMutations;
import net.richarddawkins.watchmaker.morphs.mono.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.morphs.mono.genome.type.SpokesType;
import net.richarddawkins.watchmaker.morphs.mono.genome.type.SwellType;
/**
 * ./arthromorphs
./arthromorphs/Springtail
./arthromorphs/Ant
./arthromorphs/Fly head on
./arthromorphs/Cartoon cat
./arthromorphs/Cyclops
./arthromorphs/Earwig
./arthromorphs/water scorpion
./arthromorphs/Anotger ant
./arthromorphs/Bent legs approaching
./arthromorphs/Stick Insect
./arthromorphs/Towards Grasshopper
./snails
./snails/White Pacific Atys
./snails/Glory of the Sea
./snails/Antelope horn
./snails/Square snail
./snails/TEST ALBUM
./snails/Spirula
./snails/Solid Pupa
./snails/Antelope horns
./snails/SQUASHED AMMONITE
./snails/Oyster
./snails/Large Album
./snails/Ivory Cone
./snails/Shell Album 420
./snails/Bubble
./snails/Shell Album 102
./snails/Moon
./snails/Modified whelk
./monochrome
./monochrome/Handkerchief with bows
./monochrome/Stunted
./monochrome/Chinese character
./monochrome/Exhibition zoo
./monochrome/Alphabet zoo

 * @author sven
 *
 */
public class MonochromeGenomeFactory extends BiomorphGenomeFactory {
	private static Logger logger = Logger
			.getLogger("net.richarddawkins.watchmaker.morphs.mono.genome.MonochromeGenomeFactory");

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
		makeGenes(genome, Biomorph.TRICKLE, Biomorph.TRICKLE, -4 * Biomorph.TRICKLE, Biomorph.TRICKLE,
				-Biomorph.TRICKLE, -2 * Biomorph.TRICKLE, 8 * Biomorph.TRICKLE, -4 * Biomorph.TRICKLE, 6);
		return genome;
	}

	protected void makeGenes(MonochromeGenome genome, int a, int b, int c, int d, int e, int f, int g, int h, int i) {
		super.makeGenes(genome, a, b, c, d, e, f, g, h, i);
		genome.getSegDistGene().setValue(150);
	}

	protected void salt(MonochromeGenome genome) {

		MonochromeAllowedMutations muts = (MonochromeAllowedMutations) this.muts;
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
				logger.warning("MutSizeGene not set by deliverSaltation because TrickleGene not greater than 1.");
			}
		}

		int maxGene;
		IntegerGradientGene gene = null;
		for (int j = 0; j < 8; j++) {

			gene = (IntegerGradientGene) genome.getGene(j);
			do {
				if (muts.MutTypeNo < 13 || muts.getMut(12)) {
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
			// Correcting what I think is a bug in original Blind Watchmaker,
			// which
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
		logger.fine("MonochromeGenomeFactory.deliverSaltation():" + genome.toString());
	}

	@Override
	public Genome deliverSaltation() {
		MonochromeGenome genome = new MonochromeGenome();
		salt(genome);
		return genome;
	}

}
