package net.richarddawkins.watchmaker.genome.mutation;

import java.beans.PropertyChangeSupport;

public class SimpleAllowedMutations implements AllowedMutations {
	protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	protected int mutProb;

	public int getMutProb() {
		return mutProb;
	}

	public void setMutProb(int mutProb) {
		this.mutProb = mutProb;
	}
}
