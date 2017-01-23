package net.richarddawkins.watchmaker.genome;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeSupport;

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
	protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	protected VetoableChangeSupport vcs = new VetoableChangeSupport(this);

	
	
	
	@Override
	public void copy(Genome childGenome) {
		Gene[] myGenes = toGeneArray();
		Gene[] childGenes = childGenome.toGeneArray();
		for(int i = 0; i < myGenes.length; i++) {
			myGenes[i].copy(childGenes[i]);
		}
	}
	
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);

	}
	@Override
	public Gene getGene(int i) {
		return toGeneArray()[i];
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		pcs.firePropertyChange(evt);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);

	}



}
