package net.richarddawkins.watchmaker.morphs.concho.embryo;

public class SnailEmbryologyPreferences extends SimpleEmbryologyPreferences {
	protected boolean sideView = false;

	public boolean isSideView() {
		return sideView;
	}

	public void setSideView(boolean newValue) {
		boolean oldValue = sideView;
		this.sideView = newValue;
		pcs.firePropertyChange("sideView", oldValue, newValue);
		
	}
}
