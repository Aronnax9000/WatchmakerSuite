package net.richarddawkins.watchmaker.morphs.concho.genome;

import net.richarddawkins.watchmaker.genome.mutation.SimpleAllowedMutations;

public class SnailAllowedMutations extends SimpleAllowedMutations {

	protected boolean sideView = false;

	public boolean isSideView() {
		return sideView;
	}

	public void setSideView(boolean sideView) {
		this.sideView = sideView;
	}
}
