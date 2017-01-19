package net.richarddawkins.watchmaker.morphs.bio.genome;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GooseDirection;
import net.richarddawkins.watchmaker.genome.SimpleGene;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.CompletenessType;

public class CompletenessGene extends SimpleGene {
	public CompletenessGene(Genome genome, String name) {
		super(genome, name);
	}
	
    protected CompletenessType value;

    @Override
    public void goose(GooseDirection direction) {
    	if (direction.equals(GooseDirection.leftArrow)) {
			setValue(CompletenessType.Single);
		} else if (direction.equals(GooseDirection.rightArrow)) {
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
