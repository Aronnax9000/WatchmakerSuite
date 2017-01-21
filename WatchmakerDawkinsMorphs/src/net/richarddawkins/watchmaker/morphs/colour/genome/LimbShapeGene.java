package net.richarddawkins.watchmaker.morphs.colour.genome;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.GeneManipulationEvent;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GooseDirection;
import net.richarddawkins.watchmaker.genome.SimpleGene;
import net.richarddawkins.watchmaker.morphs.colour.genome.type.LimbShapeType;

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

	public void setValue(LimbShapeType newValue) {
		LimbShapeType oldValue = this.value;
		this.value = newValue;
		pcs.firePropertyChange("value", oldValue, newValue);
	}

	@Override
	public void geneManipulated(GeneManipulationEvent gbme) {
		GooseDirection direction = gbme.getGooseDirection();
		if (direction.equals(GooseDirection.leftArrow)) {
			setValue(LimbShapeType.Oval);
		} else if (direction.equals(GooseDirection.equalsSign)) {
			setValue(LimbShapeType.Stick);
		} else if (direction.equals(GooseDirection.rightArrow)) {
			setValue(LimbShapeType.Rectangle);
		}

	}

	@Override
	public void copy(Gene gene) {
		super.copy(gene);
		((LimbShapeGene) gene).value = this.value;
	}
}
