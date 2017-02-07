package net.richarddawkins.watchmaker.morphs.bio.genome;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GooseDirection;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SwellType;
import net.richarddawkins.watchmaker.morphs.mono.genome.MonochromeGenome;
import net.richarddawkins.watchmaker.util.Globals;

public class Gene9 extends IntegerGradientGene {
	
	public Gene9(Genome genome, String name) {
		super(genome, name);
	}

	protected int gene9Max = 8;
	public int getGene9Max() {
		return gene9Max;
	}

	public void setGene9Max(int max) {
		gene9Max = max;
	}
	
	public void goose(GooseDirection direction) {
		MonochromeGenome MonochromeGenome = (MonochromeGenome) genome;
		if (direction.equals(GooseDirection.leftArrow)) {
			decrementGene();
		} else if (direction.equals(GooseDirection.rightArrow)) {
			long sizeWorry 
				= (long) ((MonochromeGenome.getSegNoGene().getValue() + 1) 
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
		this.setValue(this.value + summand);
	}
	
	@Override
	public void setValue(int newValue) {
		if (newValue < 1)
			newValue = 1;
		if (newValue > gene9Max)
			newValue = gene9Max;
		super.setValue(newValue);
	}
	
}
