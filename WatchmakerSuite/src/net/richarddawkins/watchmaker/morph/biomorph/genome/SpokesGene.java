package net.richarddawkins.watchmaker.morph.biomorph.genome;

import java.awt.Cursor;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.SimpleGene;
import net.richarddawkins.watchmaker.morph.biomorph.genome.type.SpokesType;
import net.richarddawkins.watchmaker.resourceloader.WatchmakerCursors;

public class SpokesGene extends SimpleGene {
	protected SpokesType value;
	public SpokesGene(String name) {
		super(name);
	}
	public SpokesType getValue() {
		return value;
	}

	public void setValue(SpokesType value) {
		SpokesType old = this.value;
		this.value = value;
		pcs.firePropertyChange("value", old, value);
	}
	@Override
	public void copy(Gene gene) {
		((SpokesGene)gene).value = this.value;
	}
	
	@Override
	public void goose(Cursor cursor) {
		if (cursor.equals(WatchmakerCursors.leftArrow)) {
			setValue(SpokesType.NorthOnly);
		} else if (cursor.equals(WatchmakerCursors.equalsArrow)) {
			setValue(SpokesType.NSouth);
		} else if (cursor.equals(WatchmakerCursors.rightArrow)) {
			setValue(SpokesType.Radial);
		}
	}
}
