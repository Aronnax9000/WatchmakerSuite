package net.richarddawkins.watchmaker.genome.mutation;

import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeSupport;

public class SimpleAllowedMutations implements AllowedMutations {
	protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	protected VetoableChangeSupport vcs = new VetoableChangeSupport(this);
	protected int mutProb;

	public int getMutProb() {
		return mutProb;
	}

	public void setMutProb(int mutProb) {
		this.mutProb = mutProb;
	}
}
