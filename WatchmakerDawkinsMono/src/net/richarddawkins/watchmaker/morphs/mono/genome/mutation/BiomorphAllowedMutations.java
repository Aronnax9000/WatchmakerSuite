package net.richarddawkins.watchmaker.morphs.mono.genome.mutation;

import net.richarddawkins.watchmaker.genome.mutation.SimpleAllowedMutations;

public class BiomorphAllowedMutations extends SimpleAllowedMutations {
	protected boolean[] mut;

	public void setMut(int i, boolean newValue) {
		boolean oldValue = mut[i];
		mut[i] = newValue;
		if (oldValue != newValue) {
			pcs.fireIndexedPropertyChange("mut", i, oldValue, newValue);
		}
	}

	public boolean getMut(int i) {
		return mut[i];
	}

	public boolean[] getMut() {
		return mut;
	}

}
