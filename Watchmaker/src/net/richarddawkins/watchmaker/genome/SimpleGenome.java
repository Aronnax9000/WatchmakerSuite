package net.richarddawkins.watchmaker.genome;

import java.beans.PropertyChangeEvent;
import java.nio.ByteBuffer;

public abstract class SimpleGenome implements Genome, Cloneable {
	@Override
    public boolean genomicallyEquals(Genome thatGenome) {
        Gene[] thoseGenes = thatGenome.toGeneArray();
        Gene[] genes = this.toGeneArray();
        if(thoseGenes.length != genes.length) {
            return false;
        }
        for(int index = 0; index < genes.length; index++) {
            if(! genes[index].genomicallyEquals(thoseGenes[index])) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }

    //
	// protected Morph morph;
	// @Override
	// public Morph getMorph() {
	// return morph;
	// }
	//
	// @Override
	// public void setMorph(Morph morph) {
	// this.morph = morph;
	// }
	protected GenomeChangeSupport gcs = new GenomeChangeSupport(this);

	@Override
	public void copy(Genome childGenome) {
		Gene[] myGenes = toGeneArray();
		Gene[] childGenes = childGenome.toGeneArray();
		for (int i = 0; i < myGenes.length; i++) {
			myGenes[i].copy(childGenes[i]);
		}
	}

	@Override
	public void addGenomeChangeListener(GenomeChangeListener listener) {
		gcs.addGenomeChangeListener(listener);

	}

	@Override
	public void removeGenomeChangeListener(GenomeChangeListener listener) {
		gcs.addGenomeChangeListener(listener);

	}

	@Override
	public Gene getGene(int i) {
		return toGeneArray()[i];
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		gcs.fireGenomeChangeEvent(this, evt);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Gene gene : this.toGeneArray()) {
			sb.append(gene.toString() + " ");
		}
		return sb.toString();
	}

	@Override
	public void readFromByteBuffer(ByteBuffer byteBuffer) {
	}

	@Override
	public void writeToByteBuffer(ByteBuffer byteBuffer) {
	}

}
