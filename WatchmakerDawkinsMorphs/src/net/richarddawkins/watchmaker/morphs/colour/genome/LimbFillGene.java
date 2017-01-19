package net.richarddawkins.watchmaker.morphs.colour.genome;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GooseDirection;
import net.richarddawkins.watchmaker.genome.SimpleGene;
import net.richarddawkins.watchmaker.morphs.colour.genome.type.LimbFillType;

public class LimbFillGene extends SimpleGene {
	
	public LimbFillGene(Genome genome, String name) {
		super(genome, name);
	}
	
	public LimbFillGene(Genome genome, String name, LimbFillType value) {
		super(genome, name);
		this.value = value;
	}

	protected LimbFillType value;

	public LimbFillType getValue() {
		return value;
	}

	public void setValue(LimbFillType newValue) {
		LimbFillType oldValue = this.value;
		this.value = newValue;
		pcs.firePropertyChange("value", oldValue, newValue);
	}

	@Override
	public void goose(GooseDirection direction) {
		if (direction.equals(GooseDirection.leftArrow)) {
			setValue(LimbFillType.Open);
		} else if (direction.equals(GooseDirection.rightArrow)) {
			setValue(LimbFillType.Filled);
		}
	}
	
	@Override
	public void copy(Gene gene) {
		super.copy(gene);
		((LimbFillGene)gene).value = this.value;
	}
}
