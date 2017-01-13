package net.richarddawkins.watchmaker.morph.colour.genome;

import java.awt.Cursor;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.SimpleGene;
import net.richarddawkins.watchmaker.morph.colour.genome.type.LimbShapeType;
import net.richarddawkins.watchmaker.resourceloader.WatchmakerCursors;

public class LimbShapeGene extends SimpleGene {
	protected LimbShapeType value;
	public LimbShapeGene(Genome genome, String name) {
		super(genome, name);
	}
	public LimbShapeGene(Genome genome, String name, LimbShapeType value) {
		this(genome, name);
		this.value = value;
	}
	public LimbShapeType getValue() {
		return value;
	}

	public void setValue(LimbShapeType value) {
		this.value = value;
	}
	@Override
	public void goose(Cursor cursor) {
		if (cursor.equals(WatchmakerCursors.leftArrow)) {
			setValue(LimbShapeType.Oval);
		} else if (cursor.equals(WatchmakerCursors.equalsArrow)) {
			setValue(LimbShapeType.Stick);
		} else if (cursor.equals(WatchmakerCursors.rightArrow)) {
			setValue(LimbShapeType.Rectangle);
		}
		
	}
	
	
	@Override
	public void copy(Gene gene) {
		super.copy(gene);
		((LimbShapeGene)gene).value = this.value;
	}
}
