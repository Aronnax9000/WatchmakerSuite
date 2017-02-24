package net.richarddawkins.watchmaker.morphs.concho.genome;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.GeneManipulationEvent;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GooseDirection;
import net.richarddawkins.watchmaker.genome.SimpleGene;
import net.richarddawkins.watchmaker.morphs.arthro.genome.Atom;
import net.richarddawkins.watchmaker.morphs.concho.genome.type.HandednessType;

public class HandednessGene extends SimpleGene {

	protected HandednessType value;

	public HandednessType getValue() {
		return value;
	}

	public void setValue(HandednessType newValue) {
		HandednessType oldValue = this.value;
		this.value = newValue;
		pcs.firePropertyChange("value", oldValue, newValue);
	}

	public void flipHandedness() {
		setValue(HandednessType.values()[1 - value.ordinal()]);
	}

	public HandednessGene(Genome genome, String name) {
		super(genome, name);

	}

	@Override
	public void geneManipulated(GeneManipulationEvent gbme) {
		if (gbme.getGooseDirection() == GooseDirection.leftArrow)
			setValue(HandednessType.Left);
		else if (gbme.getGooseDirection() == GooseDirection.rightArrow)
			setValue(HandednessType.Right);

	}

	@Override
	public void copy(Gene destinationGene) {
		((HandednessGene)destinationGene).setValue(getValue());
	}
	@Override
	public String toString() {
		return name + ":" + value.name();
	}

    @Override
    public boolean genomicallyEquals(Gene gene) {
        if(! (gene instanceof Atom)) return false;
        HandednessGene that = (HandednessGene) gene;
        if(this.value != that.getValue()) return false;
        return true;
    }

}
