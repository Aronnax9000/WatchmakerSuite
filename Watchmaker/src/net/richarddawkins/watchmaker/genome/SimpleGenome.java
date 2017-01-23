package net.richarddawkins.watchmaker.genome;

import java.beans.PropertyChangeEvent;

public abstract class SimpleGenome implements Genome, Cloneable {
//
//	protected Morph morph;
//	@Override
//	public Morph getMorph() {
//		return morph;
//	}
//
//	@Override
//	public void setMorph(Morph morph) {
//		this.morph = morph;
//	}
	protected GenomeChangeSupport gcs = new GenomeChangeSupport(this);
	
	@Override
	public void copy(Genome childGenome) {
		Gene[] myGenes = toGeneArray();
		Gene[] childGenes = childGenome.toGeneArray();
		for(int i = 0; i < myGenes.length; i++) {
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




}
