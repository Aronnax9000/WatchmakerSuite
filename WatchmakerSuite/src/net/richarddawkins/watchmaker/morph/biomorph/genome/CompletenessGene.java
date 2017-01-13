package net.richarddawkins.watchmaker.morph.biomorph.genome;

import java.awt.Cursor;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.SimpleGene;
import net.richarddawkins.watchmaker.morph.biomorph.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.resourceloader.WatchmakerCursors;

public class CompletenessGene extends SimpleGene {
	public CompletenessGene(Genome genome, String name) {
		super(genome, name);
	}
	
    protected CompletenessType value;

    public void goose(Cursor cursor) {
    	BiomorphGenome biomorphGenome = (BiomorphGenome) genome;
		if (cursor.equals(WatchmakerCursors.leftArrow)) {
			biomorphGenome.getCompletenessGene().setValue(CompletenessType.Single);
		} else if (cursor.equals(WatchmakerCursors.rightArrow)) {
			biomorphGenome.getCompletenessGene().setValue(CompletenessType.Double);
		}
    }
    
	public CompletenessType getValue() {
		return value;
	}

	public void setValue(CompletenessType value) {
		this.value = value;
	}

}
