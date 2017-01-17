package net.richarddawkins.watchmaker.morphs.bio.genome;

import java.awt.Cursor;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.util.Globals;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SwellType;
import net.richarddawkins.watchmaker.resourceloader.WatchmakerCursors;

public class Gene9 extends IntegerGradientGene {
	public Gene9(Genome genome, String name) {
		super(genome, name);
	}

	public void goose(Cursor cursor) {
		BiomorphGenome biomorphGenome = (BiomorphGenome) genome;
		if (cursor.equals(WatchmakerCursors.leftArrow)) {
			decrementGene();
		} else if (cursor.equals(WatchmakerCursors.rightArrow)) {
			long sizeWorry 
				= (long) ((biomorphGenome.getSegNoGene().getValue() + 1) 
				* Math.pow(2, value));
			if (sizeWorry <= Globals.worryMax) {
				incrementGene();
			}
		} else if (cursor.equals(WatchmakerCursors.upArrow)) {
			setGradient(SwellType.Swell);
		} else if (cursor.equals(WatchmakerCursors.equalsArrow)) {
			setGradient(SwellType.Same);
		} else if (cursor.equals(WatchmakerCursors.downArrow)) {
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
