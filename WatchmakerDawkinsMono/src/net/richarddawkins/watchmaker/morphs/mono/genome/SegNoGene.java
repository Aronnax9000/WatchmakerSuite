package net.richarddawkins.watchmaker.morphs.mono.genome;

import net.richarddawkins.watchmaker.genome.GeneManipulationEvent;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GooseDirection;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.util.Globals;

public class SegNoGene extends IntegerGeneOneOrGreater {

	public SegNoGene(Genome genome, String name) {
		super(genome, name);
	}

	@Override
	public void geneManipulated(GeneManipulationEvent gbme) {
		GooseDirection direction = gbme.getGooseDirection();
		if (direction.equals(GooseDirection.leftArrow)) {
			decrementGene();
		} else if (direction.equals(GooseDirection.rightArrow)) {
			MonochromeGenome MonochromeGenome = (MonochromeGenome) genome;

			long sizeWorry = (long) ((this.value + 1) * Math.pow(2, MonochromeGenome.getGene9().getValue()));
			if (sizeWorry <= Globals.worryMax) {
				incrementGene();
			}
		}
	}

	/**
	 * Add to segNoGene provided that the product segNoGene * gene[Gene9]^2 &lt;
	 * Globals.worryMax
	 * 
	 * @param summand
	 *            the amount to add to the SegNoGene.
	 */
	public void addToGene(int summand) {
		int newValue = this.value + summand;
		if (summand > 0) {
			int sizeWorry = newValue * 1 << ((IntegerGene) genome.getGene(8)).getValue();
			if (sizeWorry > Globals.worryMax)
				newValue--;
		}
		setValue(newValue);

	}
}
