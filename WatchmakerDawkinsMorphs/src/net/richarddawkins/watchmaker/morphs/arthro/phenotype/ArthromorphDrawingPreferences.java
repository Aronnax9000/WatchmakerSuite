package net.richarddawkins.watchmaker.morphs.arthro.phenotype;

import net.richarddawkins.watchmaker.phenotype.SimpleDrawingPreferences;

public class ArthromorphDrawingPreferences extends SimpleDrawingPreferences {

	
	
	protected boolean centring = false;

	protected boolean sideways = false;

	protected boolean wantColor = true;

	public boolean isCentring() {
		return centring;
	}

	public boolean isSideways() {
		return sideways;
	}

	public boolean isWantColor() {
		return wantColor;
	}
	
	public void setSideways(boolean newValue) {
		boolean oldValue = sideways;
		sideways = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("sideways", oldValue, newValue);
		}
	}
	
	
	public void setCentring(boolean newValue) {
		boolean oldValue = centring;
		centring = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("centring", oldValue, newValue);
		}

	}

	
	public void setWantColor(boolean wantColor) {
		this.wantColor = wantColor;
	}
}
