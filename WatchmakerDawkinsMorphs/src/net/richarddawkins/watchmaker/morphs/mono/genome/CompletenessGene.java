package net.richarddawkins.watchmaker.morphs.mono.genome;

import java.nio.ByteBuffer;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.GeneManipulationEvent;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GooseDirection;
import net.richarddawkins.watchmaker.genome.SimpleGene;
import net.richarddawkins.watchmaker.morphs.mono.genome.type.CompletenessType;

public class CompletenessGene extends SimpleGene {

	@Override
	public void readIndexedValueFromByteBuffer(ByteBuffer byteBuffer, int index) {
		if (index == 0) {
			setValue(CompletenessType.values()[byteBuffer.get()]);
		}
	}

	@Override
	public void writeIndexedValueToByteBuffer(ByteBuffer byteBuffer, int index) {
		if (index == 0) {
			byteBuffer.put((byte) value.ordinal());
		}
	}

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
	public String toString() {
		if (value != null)
			return value.name();
		else
			return "(Not Set)";
	}

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

    @Override
    public boolean genomicallyEquals(Gene gene) {
        if(! (gene instanceof CompletenessGene)) return false;
        CompletenessGene that = (CompletenessGene) gene;
        if(this.value != that.getValue()) return false;
        return true;
    }

}
