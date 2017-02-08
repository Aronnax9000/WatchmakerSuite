package net.richarddawkins.watchmaker.morphs.mono.genome;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.GeneManipulationEvent;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GooseDirection;
import net.richarddawkins.watchmaker.genome.SimpleGene;
import net.richarddawkins.watchmaker.morphs.mono.genome.type.CompletenessType;

public class CompletenessGene extends SimpleGene {
	public CompletenessGene(Genome genome, String name) {
		super(genome, name);
	}

	protected CompletenessType value;

	public CompletenessType getValue() {
		return value;
	}

	public void setValue(CompletenessType newValue) {
		CompletenessType oldValue = value;
		this.value = newValue;
		pcs.firePropertyChange("value", oldValue, newValue);

	}
	
	@Override
	public String toString() {return value.name();}

	@Override
	public void copy(Gene gene) {
		super.copy(gene);
		((CompletenessGene) gene).setValue(value);
	}

	@Override
	public void geneManipulated(GeneManipulationEvent gbme) {
		GooseDirection direction = gbme.getGooseDirection();
		if (direction.equals(GooseDirection.leftArrow)) {
			setValue(CompletenessType.Single);
		} else if (direction.equals(GooseDirection.rightArrow)) {
			setValue(CompletenessType.Double);
		} 

	}

}
