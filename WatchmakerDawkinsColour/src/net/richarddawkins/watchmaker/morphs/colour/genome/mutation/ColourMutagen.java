package net.richarddawkins.watchmaker.morphs.colour.genome.mutation;

import static net.richarddawkins.watchmaker.genome.mutation.Random.randInt;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.genome.mutation.AllowedMutations;
import net.richarddawkins.watchmaker.genome.mutation.Random;
import net.richarddawkins.watchmaker.morphs.bio.genome.BiomorphGenome;
import net.richarddawkins.watchmaker.morphs.bio.genome.CompletenessGene;
import net.richarddawkins.watchmaker.morphs.bio.genome.IntegerGradientGene;
import net.richarddawkins.watchmaker.morphs.bio.genome.SpokesGene;
import net.richarddawkins.watchmaker.morphs.bio.genome.mutation.BiomorphMutagen;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SpokesType;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SwellType;
import net.richarddawkins.watchmaker.morphs.colour.genome.ColorGene;
import net.richarddawkins.watchmaker.morphs.colour.genome.ColourGenome;
import net.richarddawkins.watchmaker.morphs.colour.genome.type.LimbFillType;
import net.richarddawkins.watchmaker.morphs.colour.genome.type.LimbShapeType;

public class ColourMutagen extends BiomorphMutagen {

	public ColourMutagen(AllowedMutations allowedMutations) {
		super(allowedMutations);

	}

	LimbShapeType randLimbType() {
		switch (randInt(3)) {
		case 1:
			return LimbShapeType.Stick;
		case 2:
			return LimbShapeType.Oval;
		case 3:
			return LimbShapeType.Rectangle;
		default:
			return null;
		}
	}

	LimbFillType randLimbFill() {
		switch (randInt(2)) {
		case 1:
			return LimbFillType.Open;
		case 2:
			return LimbFillType.Filled;
		default:
			return null;
		}
	}

	public boolean mutate(Genome genome) {
		ColourGenome target = (ColourGenome) genome;
		ColourAllowedMutations colourAllowedMutations = (ColourAllowedMutations) allowedMutations;
		boolean success = false;
		boolean[] mut = colourAllowedMutations.getMut();
		int mutProb = target.getMutProbGene().getValue();
		if (mut[6] && randInt(100) < mutProb) {
			target.getMutProbGene().addToGene(direction9());
			// Update local copy of MutProbGene
			mutProb = target.getMutProbGene().getValue();
			success = true;
		}
		if (mut[12]) {
			for (int j = 0; j < 8; j++)
				if (randInt(100) < mutProb) {
					((IntegerGene) target.getGene(j)).addToGene(direction((BiomorphGenome) genome));
					success = true;
				}
			if (randInt(100) < mutProb) {
				((IntegerGene) target.getGene(8)).addToGene(direction9());
				success = true;
			}

		}
		ColorGene[] colorGenes = target.getColorGenes();
		if (mut[9])
			for (int j = 0; j < 8; j++)
				if (randInt(100) < mutProb) {
					colorGenes[j].addToGene(direction9());
					success = true;
				}
		if (mut[7] && randInt(100) < mutProb) {
			target.getLimbShapeGene().setValue(randLimbType());
			success = true;
		}
		if (mut[8] && randInt(100) < mutProb) {
			target.getLimbFillGene().setValue(randLimbFill());
			success = true;
		}

		if (mut[10] && randInt(100) < mutProb) {
			target.addToBackColorGene(direction9());

			success = true;
		}

		if (mut[11] && randInt(100) < mutProb) {
			target.getThicknessGene().addToGene(direction9());
			success = true;
		}
		if (mut[0] && randInt(100) < mutProb) {
			target.getSegNoGene().addToGene(direction9());
			success = true;
		}
		IntegerGene segNoGene = target.getSegNoGene();
		if (mut[1] && segNoGene.getValue() > 1) {
			for (int j = 0; j < 8; j++)
				if (randInt(100) < mutProb / 2) {
					IntegerGradientGene gradGene = (IntegerGradientGene) target.getGene(j);
					gradGene.setGradient(randSwell(gradGene.getGradient()));
				}
			if (randInt(100) < mutProb / 2) {
				IntegerGradientGene gradGene = target.getSegDistGene();
				gradGene.setGradient(randSwell(gradGene.getGradient()));

			}

			success = true;
		}
		if (mut[0] && segNoGene.getValue() > 1 && randInt(100) < mutProb) {
			target.getSegDistGene().addToGene(direction9());
			success = true;
		}
		if (mut[2] && randInt(100) < mutProb / 2) {
			CompletenessGene completenessGene = target.getCompletenessGene();
			completenessGene.setValue(completenessGene.getValue() == CompletenessType.Single ? CompletenessType.Double
					: CompletenessType.Single);
			success = true;
		}
		if (mut[3] && randInt(100) < mutProb / 2) {
			SpokesGene spokesGene = target.getSpokesGene();
			switch (spokesGene.getValue()) {
			case NorthOnly:
				spokesGene.setValue(SpokesType.NSouth);
				break;
			case NSouth:
				spokesGene.setValue(direction9() == 1 ? SpokesType.Radial : SpokesType.NorthOnly);
				break;
			case Radial:
				spokesGene.setValue(SpokesType.NSouth);
				break;
			}
			success = true;
		}
		if (mut[4] && randInt(100) < mutProb) {
			target.getTrickleGene().addToGene(direction9());
			success = true;
		}
		if (mut[5] && randInt(100) < mutProb) {
			target.getMutSizeGene().addToGene(direction9());
			success = true;
		}
		return success;
	}

	protected void randomForeColour(ColourGenome genome) {
		for (int j = 0; j < 7; j++)
			genome.getColorGenes()[j].setValue(randInt(256)); // RAINBOW
	}

	protected void randomBackColour(ColourGenome genome) {
		genome.getBackColorGene().setValue(Random.randInt(256)); // RAINBOW
	}

	@Override
	public void deliverSaltation(Genome theGenome) {
		// procedure DeliverSaltation (var thebiomorph: person);
		// var
		// j, maxgene, r: INTEGER;
		// factor: -1..1;
		//
		// begin
		// DelayedDrawing := FALSE;
		// special := MidBox;
		// with theBiomorph do {bomb 5, range check failed, here after killing
		// top Adam}
		// begin
		ColourGenome genome = (ColourGenome) theGenome;
		ColourAllowedMutations muts = (ColourAllowedMutations) allowedMutations;
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
			trickleGene.setValue(1 + Random.randInt(100) / 10);
			if (trickleGene.getValue() > 1) {
				genome.getMutSizeGene().setValue(trickleGene.getValue() / 2);
			}
		}
		if (muts.getMut(9)) {
			randomForeColour(genome);
		}
		if (muts.getMut(7)) {
			genome.getLimbShapeGene().setValue(randLimbType());
		}
		if (muts.getMut(8)) {
			genome.getLimbFillGene().setValue(randLimbFill());
		}
		if (muts.getMut(10)) {
			randomBackColour(genome);
		}
		if (muts.getMut(11)) {
			genome.getThicknessGene().setValue(Random.randInt(3));
		}
		int maxGene;
		for (int j = 0; j < 7; j++) {

			IntegerGradientGene gene = (IntegerGradientGene) genome.getGene(j);
			do {
				if (muts.getMut(12)) {
					gene.setValue(genome.getMutSizeGene().getValue() * (Random.randInt(19) - 10));
				}
				if (muts.getMut(1)) {
					gene.setGradient(randSwell(gene.getGradient()));
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
			do {
				
				if (muts.getMut(1)) {
					segDistGene.setGradient(randSwell(segDistGene.getGradient()));
				} else {
					segDistGene.setGradient(SwellType.Same);
				}
				int factor = 0;
				switch (gene.getGradient()) {
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
			} while (!((maxGene <= 100) && (maxGene >= -100)));
		}
		IntegerGradientGene gene9 = genome.getGene9();
		gene9.setValue(Random.randInt(5) + 1); // 2..6
		gene9.setGradient(SwellType.Same);

	}

}
