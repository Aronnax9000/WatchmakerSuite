package net.richarddawkins.watchmaker.morphs.bio.genome;

import java.awt.Cursor;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.resourceloader.WatchmakerCursors;
import net.richarddawkins.watchmaker.util.Globals;

public class SegNoGene extends IntegerGene {

	public SegNoGene(Genome genome, String name) {
		super(genome, name);
	}
	@Override
	public void goose(Cursor cursor) {
		
		if (cursor.equals(WatchmakerCursors.leftArrow)) {
			decrementGene();
		} else if (cursor.equals(WatchmakerCursors.rightArrow)) {
			BiomorphGenome biomorphGenome = (BiomorphGenome) genome;
			
			long sizeWorry = (long) ((this.value + 1) * Math.pow(2, 
				biomorphGenome.gene9.getValue()));
			if (sizeWorry <= Globals.worryMax) {
				incrementGene();
			}
		}
	}
	
	/**
	 * Add to segNoGene provided that the product segNoGene * gene[Gene9]^2 &lt;
	 * Globals.worryMax
	 * @param summand the amount to add to the SegNoGene.
	 */
	public void addToGene(int summand) {
		int newValue = this.value + summand;
		if (summand > 0) {
			int sizeWorry = newValue * 1 << ((IntegerGene) genome.getGene(8)).getValue();
			if (sizeWorry > Globals.worryMax)
				newValue--;
			if (newValue < 1)
				newValue = 1;

		}
		setValue(newValue);

	}
}
