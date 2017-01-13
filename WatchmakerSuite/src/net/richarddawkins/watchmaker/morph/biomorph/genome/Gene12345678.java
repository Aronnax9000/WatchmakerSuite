package net.richarddawkins.watchmaker.morph.biomorph.genome;

import java.awt.Cursor;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.biomorph.genome.type.SwellType;
import net.richarddawkins.watchmaker.resourceloader.WatchmakerCursors;

public class Gene12345678 extends IntegerGradientGene {
	public Gene12345678(Genome genome, String name) {
		super(genome, name);
		
	}
	@Override
	public void goose(Cursor cursor) {
		super.goose(cursor);
		if (cursor.equals(WatchmakerCursors.upArrow)) {
			setGradient(SwellType.Swell);
		} else if (cursor.equals(WatchmakerCursors.equalsArrow)) {
			setGradient(SwellType.Same);
		} else if (cursor.equals(WatchmakerCursors.downArrow)) {
			setGradient(SwellType.Shrink);
		}
	}
	@Override
	public int getGooseSize() {
		BiomorphGenome biomorphGenome = (BiomorphGenome) genome;
		return biomorphGenome.getMutSizeGene().getValue();
	}
	

	
}
