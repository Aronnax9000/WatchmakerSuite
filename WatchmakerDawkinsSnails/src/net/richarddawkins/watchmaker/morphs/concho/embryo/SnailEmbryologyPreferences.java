package net.richarddawkins.watchmaker.morphs.concho.embryo;

import net.richarddawkins.watchmaker.embryo.EmbryologyPreferences;

public class SnailEmbryologyPreferences implements EmbryologyPreferences {
	protected boolean sideView = false;
	
	public boolean isSideView() {
		return sideView;
	}

	public void setSideView(boolean sideView) {
		this.sideView = sideView;
	}
}
