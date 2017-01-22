package net.richarddawkins.watchmaker.morphs.mono.genome;

import static net.richarddawkins.watchmaker.genome.mutation.Random.randInt;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.genome.mutation.AllowedMutations;
import net.richarddawkins.watchmaker.morphs.bio.genome.BiomorphGenome;
import net.richarddawkins.watchmaker.morphs.bio.genome.CompletenessGene;
import net.richarddawkins.watchmaker.morphs.bio.genome.Gene12345678;
import net.richarddawkins.watchmaker.morphs.bio.genome.Gene9;
import net.richarddawkins.watchmaker.morphs.bio.genome.IntegerGradientGene;
import net.richarddawkins.watchmaker.morphs.bio.genome.SpokesGene;
import net.richarddawkins.watchmaker.morphs.bio.genome.mutation.BiomorphMutagen;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SpokesType;
import net.richarddawkins.watchmaker.morphs.mono.MonochromeAllowedMutations;

public class MonochromeMutagen extends BiomorphMutagen  {

	public MonochromeMutagen(AllowedMutations allowedMutations) {
		super(allowedMutations);
	}

	public boolean mutate(Genome genome) {
		MonochromeAllowedMutations monochromeAllowedMutations 
		= (MonochromeAllowedMutations) allowedMutations;
		MonochromeGenome target = (MonochromeGenome) genome;
		IntegerGene segNoGene = target.getSegNoGene();
		IntegerGene segDistGene = target.getSegDistGene();
		boolean success = false;
		boolean[] mut = monochromeAllowedMutations.getMut();
		int mutProb = target.getMutProbGene().getValue();
		if (mut[6]) {
			if (randInt(100) < mutProb) {
				target.getMutProbGene().addToGene(direction9());
				success = true;
			}
		}
		for (int j = 0; j < 8; j++) {
			if (randInt(100) < mutProb) {
				((Gene12345678) target.getGene(j)).addToGene(direction((BiomorphGenome) genome));
				success = true;
			}
		}
		if (randInt(100) < mutProb) {
			((Gene9) target.getGene(8)).addToGene(direction9());

			success = true;
		}

		if (mut[0] && randInt(100) < mutProb) {
			int j = direction9();
			target.getSegNoGene().addToGene(j);

			success = true;
		}
		if (mut[1] && segNoGene.getValue() > 1) {
			for (int j = 0; j < 7; j++)
				if (randInt(100) < mutProb / 2) {
					IntegerGradientGene gradGene = (IntegerGradientGene) target.getGene(j);
					gradGene.setGradient(randSwell(gradGene.getGradient()));

					success = true;
				}
			if (randInt(100) < mutProb / 2) {
				target.getSegDistGene().setGradient(randSwell(target.getSegDistGene().getGradient()));
				success = true;
			}
		}
		if (mut[7] && mut[8] && randInt(100) < mutProb) {
			Gene9 gene9 = (Gene9) target.getGene(8);
			gene9.setGradient(randSwell(gene9.getGradient()));
			success = true;
		}
		if (mut[0] && segNoGene.getValue() > 1 && randInt(100) < mutProb) {
			segDistGene.setValue(direction9());
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
			}
			success = true;
		}
		if (mut[4] && randInt(100) < Math.abs(mutProb)) {
			target.getTrickleGene().addToGene(direction9());
			success = true;
		}
		if (mut[5] && randInt(100) < Math.abs(mutProb)) {
			target.getMutSizeGene().addToGene(direction9());
			success = true;
		}
		return success;
	}

}
