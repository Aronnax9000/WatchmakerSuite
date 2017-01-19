package net.richarddawkins.watchmaker.morphs.bio.genome;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GooseDirection;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SwellType;
import net.richarddawkins.watchmaker.util.Globals;

public class Gene9 extends IntegerGradientGene {
	public Gene9(Genome genome, String name) {
		super(genome, name);
	}

	public void goose(GooseDirection direction) {
		BiomorphGenome biomorphGenome = (BiomorphGenome) genome;
		if (direction.equals(GooseDirection.leftArrow)) {
			decrementGene();
		} else if (direction.equals(GooseDirection.rightArrow)) {
			long sizeWorry 
				= (long) ((biomorphGenome.getSegNoGene().getValue() + 1) 
				* Math.pow(2, value));
			if (sizeWorry <= Globals.worryMax) {
				incrementGene();
			}
		} else if (direction.equals(GooseDirection.upArrow)) {
			setGradient(SwellType.Swell);
		} else if (direction.equals(GooseDirection.equalsSign)) {
			setGradient(SwellType.Same);
		} else if (direction.equals(GooseDirection.downArrow)) {
			setGradient(SwellType.Shrink);
		}
	}
	
	/**
	 * Gene9 is not permitted to fall below 1, or rise above 8 (the default, for
	 * Colour.) Monochrome sets 11 as the limit in its constructor.
	 * @param summand the quantity to add to the gene.
	 */
	public void addToGene(int summand) {
		int newValue = this.value + summand;
		if (newValue < 1)
			newValue = 1;
		else {
			int gene9Max = ((BiomorphGenome)this.getGenome()).getGene9Max();
			if (newValue > gene9Max) {
				newValue = gene9Max;
			}
 		}
		// Call the setter to make sure a PropertyChangeEvent is fired.
		this.setValue(newValue);
	}
}
