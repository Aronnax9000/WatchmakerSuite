package net.richarddawkins.watchmaker.morphs.bio.genome;

import java.awt.Cursor;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.SimpleGene;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.resourceloader.WatchmakerCursors;

public class CompletenessGene extends SimpleGene {
	public CompletenessGene(Genome genome, String name) {
		super(genome, name);
	}
	
    protected CompletenessType value;

    @Override
    public void goose(Cursor cursor) {
    	if (cursor.equals(WatchmakerCursors.leftArrow)) {
			setValue(CompletenessType.Single);
		} else if (cursor.equals(WatchmakerCursors.rightArrow)) {
			setValue(CompletenessType.Double);
		}
    }
    
	public CompletenessType getValue() {
		return value;
	}

	public void setValue(CompletenessType newValue) {
		CompletenessType oldValue = value;
	    this.value = newValue;
	    pcs.firePropertyChange("value", oldValue, newValue);
		
	}
	@Override
	public void copy(Gene gene) {
		super.copy(gene);
		((CompletenessGene)gene).setValue(value);
	}

}
