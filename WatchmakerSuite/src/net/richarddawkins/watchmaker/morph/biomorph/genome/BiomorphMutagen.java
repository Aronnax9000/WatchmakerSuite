package net.richarddawkins.watchmaker.morph.biomorph.genome;

import static net.richarddawkins.watchmaker.morph.util.Random.randInt;

import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.morph.Mutagen;
import net.richarddawkins.watchmaker.morph.util.Globals;


public abstract class BiomorphMutagen 
	implements Mutagen {
	static protected SwellType randSwell(SwellType indGene) {
		switch(indGene) {
		case Shrink: return SwellType.Same;
		
		case Same: 
			if(randInt(2) == 1)
				return SwellType.Shrink; 
			else 
				return SwellType.Swell;
		case Swell: return SwellType.Same;
		default: return indGene; // Can't happen
		}
	}


	static public int direction(BiomorphGenome genome) {
		int mutSizeGene = genome.getMutSizeGene().getValue();
		if(randInt(2) == 2) {
			return mutSizeGene;
		} else {
			return -mutSizeGene;
		}		
	}


	static public int direction9() {
		return randInt(2) == 2 ? 1 : -1;
	}
	
	/**
	 * Add a quantity to the mutProbGene. If the the result is less than one, it
	 * is set to one. If the result is greater than 100, it is set to 100.
	 * @param summand the quantity to add to the MutProbGene.
	 */
	static public void addToMutProbGene(BiomorphGenome genome, int summand) {
		IntegerGene mutProbGene = genome.getMutProbGene();
		int newValue = mutProbGene.getValue() + summand;
		if (newValue < 1)
			newValue = 1;
		if (newValue > 100)
			newValue = 100;
		mutProbGene.setValue(newValue);
	}

	/**
	 * Gene9 is not permitted to fall below 1, or rise above 8 (the default, for
	 * Colour.) Monochrome sets 11 as the limit in its constructor.
	 * @param index the index of the gene to be modified
	 * @param summand the quantity to add to the gene.
	 */
	static public void addToGene(BiomorphGenome genome, int index, int summand) {
		int newValue = genome.getGene(index) + summand;
		
		if (index == 8) {
			if (newValue < 1)
				newValue = 1;
			else if (newValue > genome.getGene9Max())
				newValue = genome.getGene9Max();
		}
		
		genome.setGene(index, newValue);
	}
	
	static public void decrementSegNoGene(BiomorphGenome genome) {
		genome.setSegNoGene(genome.getSegNoGene() - 1);
	}

	static public void incrementSegNoGene(BiomorphGenome genome) {
		genome.setSegNoGene(genome.getSegNoGene() + 1);

	}
	
	static public void decrementGene(BiomorphGenome genome, int i) {
		genome.setGene(i, genome.getGene(i) - 1);
	}

	static public void incrementGene(BiomorphGenome genome, int i) {
		genome.setGene(i, genome.getGene(i) + 1);
	}
	
	/** mutSizeGene is not allowed to fall below 1. 
	 * @param summand the quantity to add to the MutSizeGene 
	 */
	static public void addToMutSizeGene(BiomorphGenome genome, int summand) {
		IntegerGene mutSizeGene = genome.getMutSizeGene();
		int newValue = mutSizeGene.getValue() + summand;
		
		if (newValue < 1)
			newValue = 1;
		mutSizeGene.setValue(newValue);
	}

	/**
	 * trickleGene is not allowed to fall below 1.
	 * @param summand the quantity to add to the TrickleGene.
	 */
	static public void addToTrickleGene(BiomorphGenome genome, int summand) {
		int newValue = genome.getTrickleGene() + summand;
		newValue += summand;
		if (newValue < 1)
			newValue = 1;
		genome.setTrickleGene(newValue);

	}

	/**
	 * Add to segNoGene provided that the product segNoGene * gene[Gene9]^2 &lt;
	 * Globals.worryMax
	 * @param summand the amount to add to the SegNoGene.
	 */
	static public void addToSegNoGene(BiomorphGenome genome, int summand) {
		
		int newValue = genome.getSegNoGene() + summand;
		if (summand > 0) {
			int sizeWorry = newValue * 1 << genome.getGene(8);
			if (sizeWorry > Globals.worryMax)
				newValue--;
			if (newValue < 1)
				newValue = 1;

		}
		genome.setSegNoGene(newValue);

	}

	static public void addToSegDistGene(BiomorphGenome genome, int summand) {
		int newValue = genome.getSegDistGene() + summand;
		genome.setSegDistGene(newValue);
	}

	static public void decrementTrickleGene(BiomorphGenome genome) {
		genome.setTrickleGene(genome.getTrickleGene() - 1);
	}

	static public void incrementTrickleGene(BiomorphGenome genome) {
		genome.setTrickleGene(genome.getTrickleGene() + 1);
	}

	static public void incrementMutSizeGene(BiomorphGenome genome) {
		genome.getMutSizeGene().setValue(genome.getMutSizeGene().getValue() + 1);
	}
	static public void decrementMutSizeGene(BiomorphGenome genome) {
		genome.getMutSizeGene().setValue(genome.getMutSizeGene().getValue() - 1);
	}

	static public void decrementMutProbGene(BiomorphGenome genome) {
		genome.getMutSizeGene().setValue(genome.getMutProbGene().getValue() - 1);
	}

	static public void incrementMutProbGene(BiomorphGenome genome) {
		genome.getMutSizeGene().setValue(genome.getMutProbGene().getValue() + 1);
	}



}
