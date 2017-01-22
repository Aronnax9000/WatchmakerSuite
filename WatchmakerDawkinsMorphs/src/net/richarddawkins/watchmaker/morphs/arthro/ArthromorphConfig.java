package net.richarddawkins.watchmaker.morphs.arthro;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.SimpleMorphConfig;
import net.richarddawkins.watchmaker.morphs.arthro.genome.ArthromorphEmbryology;
import net.richarddawkins.watchmaker.morphs.arthro.genome.mutation.ArthromorphAllowedMutations;
import net.richarddawkins.watchmaker.morphs.arthro.genome.mutation.ArthromorphMutagen;

public class ArthromorphConfig extends SimpleMorphConfig  {

	protected boolean centring = false;
	protected boolean wantColor = true;
	protected boolean sideways;
	
	public ArthromorphConfig() {
		super();
		mutagen = new ArthromorphMutagen(new ArthromorphAllowedMutations());
		embryology = new ArthromorphEmbryology();
		setDefaultBreedingRows(3);
		setDefaultBreedingCols(5);
	}

	public Morph createMorph(int basicType) {
		return new Arthromorph(this, basicType);
	}

	public boolean isCentring() {
		return centring;
	}
	
	public boolean isSideways() {
		return sideways;
	}
	
	public boolean isWantColor() {
		return wantColor;
	}
	
	public void setCentring(boolean newValue) {
		boolean oldValue = centring;
		centring = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("centring", oldValue, newValue);
		}

	}
	
	public void setSideways(boolean newValue) {
		boolean oldValue = sideways;
		sideways = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("sideways", oldValue, newValue);
		}
	}
	
	public void setWantColor(boolean wantColor) {
		this.wantColor = wantColor;
	}

}
