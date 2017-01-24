package net.richarddawkins.watchmaker.morphs.bio.genome;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.GeneManipulationEvent;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GooseDirection;
import net.richarddawkins.watchmaker.genome.SimpleGene;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SpokesType;
public class SpokesGene extends SimpleGene {
	protected SpokesType value;
	public SpokesGene(Genome genome, String name) {
		super(genome, name);
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
		super.copy(gene);
		((SpokesGene)gene).value = this.value;
	}
	
	@Override
	public void geneManipulated(GeneManipulationEvent gbme) {
		GooseDirection direction = gbme.getGooseDirection();
		if (direction.equals(GooseDirection.leftArrow)) {
			setValue(SpokesType.NorthOnly);
		} else if (direction.equals(GooseDirection.equalsSign)) {
			setValue(SpokesType.NSouth);
		} else if (direction.equals(GooseDirection.rightArrow)) {
			setValue(SpokesType.Radial);
		}
	}
}
