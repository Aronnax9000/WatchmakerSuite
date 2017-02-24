package net.richarddawkins.watchmaker.morphs.mono.genome;

import java.nio.ByteBuffer;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.GeneManipulationEvent;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GooseDirection;
import net.richarddawkins.watchmaker.genome.SimpleGene;
import net.richarddawkins.watchmaker.morphs.mono.genome.type.SpokesType;

public class SpokesGene extends SimpleGene {

	@Override
	public void readIndexedValueFromByteBuffer(ByteBuffer byteBuffer, int index) {
		if (index == 0) {
			setValue(SpokesType.values()[byteBuffer.get()]);
		}
	}

	@Override
	public void writeIndexedValueToByteBuffer(ByteBuffer byteBuffer, int index) {
		if (index == 0) {
			byteBuffer.put((byte) value.ordinal());
		}
	}

	@Override
	public String toString() {
		if (value != null)
			return value.name();
		else
			return "(Not Set)";
	}

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
		((SpokesGene) gene).value = this.value;
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

    @Override
    public boolean genomicallyEquals(Gene gene) {
        if(! (gene instanceof SpokesGene)) return false;
        SpokesGene that = (SpokesGene) gene;
        if(this.value != that.getValue()) return false;
        return true;
    }
}
