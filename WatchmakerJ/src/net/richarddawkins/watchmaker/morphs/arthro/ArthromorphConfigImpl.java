package net.richarddawkins.watchmaker.morphs.arthro;

import net.richarddawkins.watchmaker.MenuBuilder;
import net.richarddawkins.watchmaker.WatchmakerGUI;
import net.richarddawkins.watchmaker.morphs.Morph;
import net.richarddawkins.watchmaker.morphs.SimpleMorphConfig;

public class ArthromorphConfigImpl extends SimpleMorphConfig implements ArthromorphConfig {

	protected boolean angleMut;

	protected boolean animalClawsMut;

	protected boolean animalLegsMut;

	protected boolean animalTrunkMut;

	protected boolean centring = false;
	protected boolean clawsMut;
	protected boolean deletionMut;
	protected boolean duplicationMut;
	protected Concentration focusOfAttention;
	protected boolean heightMut;
	protected boolean legsMut;
	private ArthromorphMenuBuilder menuBuilder = new ArthromorphMenuBuilder(this);
	protected Pressure mutationPressure;
	protected boolean sectionClawsMut;
	protected boolean sectionLegsMut;
	protected boolean sectionTrunkMut;
	protected boolean segmentClawsMut;
	protected boolean segmentLegsMut;
	protected boolean segmentTrunkMut;
	protected boolean sideways;
	protected boolean trunkMut;
	protected boolean wantColor = true;
	protected boolean widthMut;

	public ArthromorphConfigImpl(WatchmakerGUI watchmakerGUI) {
		gui = watchmakerGUI;

		setIconFromFilename("Arthromorph_ALAN_00010_32x32");
		name = "Arthromorphs";
		toolTip = "Arthromorphs";
		makeAllBodyMutations(true);
		makeAllAtomMutations(true);
		mutationPressure = Pressure.Zero;
		focusOfAttention = Concentration.AnySegment;
		setDefaultBreedingRows(3);
		setDefaultBreedingCols(5);

	}

	public Morph createMorph(int basicType) {
		return new ArthromorphImpl(this, basicType);
	}

	@Override
	public Concentration getFocusOfAttention() {
		return focusOfAttention;
	}

	@Override
	public MenuBuilder getMenuBuilder() {
		return menuBuilder;
	}

	@Override
	public boolean[] getMut() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pressure getMutationPressure() {
		return mutationPressure;
	}

	@Override
	public boolean isAngleMut() {
		return angleMut;
	}

	@Override
	public boolean isAnimalClawsMut() {
		return animalClawsMut;
	}

	@Override
	public boolean isAnimalLegsMut() {
		return animalLegsMut;
	}

	@Override
	public boolean isAnimalTrunkMut() {
		return animalTrunkMut;
	}

	@Override
	public boolean isCentring() {
		return centring;
	}

	@Override
	public boolean isClawsMut() {
		return clawsMut;
	}

	@Override
	public boolean isDeletionMut() {
		return deletionMut;
	}

	@Override
	public boolean isDuplicationMut() {
		return duplicationMut;
	}

	@Override
	public boolean isHeightMut() {
		return heightMut;
	}

	@Override
	public boolean isLegsMut() {
		return legsMut;
	}

	@Override
	public boolean isSectionClawsMut() {
		return sectionClawsMut;
	}

	@Override
	public boolean isSectionLegsMut() {
		return sectionLegsMut;
	}

	@Override
	public boolean isSectionTrunkMut() {
		return sectionTrunkMut;
	}

	@Override
	public boolean isSegmentClawsMut() {
		return segmentClawsMut;
	}

	@Override
	public boolean isSegmentLegsMut() {
		return segmentLegsMut;
	}

	@Override
	public boolean isSegmentTrunkMut() {
		return segmentTrunkMut;
	}

	@Override
	public boolean isSideways() {
		return sideways;
	}

	@Override
	public boolean isTrunkMut() {
		return trunkMut;
	}

	@Override
	public boolean isWantColor() {
		return wantColor;
	}

	@Override
	public boolean isWidthMut() {
		return widthMut;
	}

	@Override
	public void makeAllAtomMutations(boolean state) {
		setWidthMut(state);
		setHeightMut(state);
		setAngleMut(state);
		setDuplicationMut(state);
		setDeletionMut(state);
	}

	@Override
	public void makeAllBodyMutations(boolean state) {
		setTrunkMut(state);
		setLegsMut(state);
		setClawsMut(state);
		setAnimalTrunkMut(state);
		setAnimalLegsMut(state);
		setAnimalClawsMut(state);
		setSectionTrunkMut(state);
		setSectionLegsMut(state);
		setSectionClawsMut(state);
		setSegmentTrunkMut(state);
		setSegmentLegsMut(state);
		setSegmentClawsMut(state);
	}

	@Override
	public void setAngleMut(boolean newValue) {
		boolean oldValue = angleMut;
		angleMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("angleMut", oldValue, newValue);
		}
	}

	@Override
	public void setAnimalClawsMut(boolean newValue) {
		boolean oldValue = animalClawsMut;
		animalClawsMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("animalClawsMut", oldValue, newValue);
		}
	}

	@Override
	public void setAnimalLegsMut(boolean newValue) {
		boolean oldValue = animalLegsMut;
		animalLegsMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("animalLegsMut", oldValue, newValue);
		}
	}

	@Override
	public void setAnimalTrunkMut(boolean newValue) {
		boolean oldValue = animalTrunkMut;
		animalTrunkMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("animalTrunkMut", oldValue, newValue);
		}
	}

	@Override
	public void setCentring(boolean newValue) {
		boolean oldValue = clawsMut;
		centring = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("centring", oldValue, newValue);
		}

	}

	@Override
	public void setClawsMut(boolean newValue) {
		boolean oldValue = clawsMut;
		clawsMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("clawsMut", oldValue, newValue);
		}
	}

	@Override
	public void setDeletionMut(boolean newValue) {
		boolean oldValue = deletionMut;
		deletionMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("deletionMut", oldValue, newValue);
		}
	}

	@Override
	public void setDuplicationMut(boolean newValue) {
		boolean oldValue = duplicationMut;
		duplicationMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("duplicationMut", oldValue, newValue);
		}
	}

	@Override
	public void setFocusOfAttention(Concentration newValue) {
		Concentration oldValue = focusOfAttention;
		focusOfAttention = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("focusOfAttention", oldValue, newValue);
		}
	}

	@Override
	public void setHeightMut(boolean newValue) {
		boolean oldValue = heightMut;
		heightMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("heightMut", oldValue, newValue);
		}
	}

	@Override
	public void setLegsMut(boolean newValue) {
		boolean oldValue = legsMut;
		legsMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("legsMut", oldValue, newValue);
		}
	}

	@Override
	public void setMutationPressure(Pressure newValue) {
		Pressure oldValue = mutationPressure;
		mutationPressure = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("mutationPressure", oldValue, newValue);
		}
	}

	@Override
	public void setSectionClawsMut(boolean newValue) {
		boolean oldValue = sectionClawsMut;
		sectionClawsMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("sectionClawsMut", oldValue, newValue);
		}
	}

	@Override
	public void setSectionLegsMut(boolean newValue) {
		boolean oldValue = sectionLegsMut;
		sectionLegsMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("sectionLegsMut", oldValue, newValue);
		}
	}

	@Override
	public void setSectionTrunkMut(boolean newValue) {
		boolean oldValue = sectionTrunkMut;
		sectionTrunkMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("sectionTrunkMut", oldValue, newValue);
		}
	}

	@Override
	public void setSegmentClawsMut(boolean newValue) {
		boolean oldValue = segmentClawsMut;
		segmentClawsMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("segmentClawsMut", oldValue, newValue);
		}
	}

	@Override
	public void setSegmentLegsMut(boolean newValue) {
		boolean oldValue = segmentLegsMut;
		segmentLegsMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("segmentLegsMut", oldValue, newValue);
		}
	}

	@Override
	public void setSegmentTrunkMut(boolean newValue) {
		boolean oldValue = segmentTrunkMut;
		segmentTrunkMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("segmentTrunkMut", oldValue, newValue);
		}
	}

	@Override
	public void setSideways(boolean newValue) {
		boolean oldValue = clawsMut;
		sideways = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("sideways", oldValue, newValue);
		}
	}

	@Override
	public void setTrunkMut(boolean newValue) {
		boolean oldValue = trunkMut;
		trunkMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("trunkMut", oldValue, newValue);
		}
	}

	@Override
	public void setWantColor(boolean wantColor) {
		this.wantColor = wantColor;
	}

	@Override
	public void setWidthMut(boolean newValue) {
		boolean oldValue = widthMut;
		widthMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("widthMut", oldValue, newValue);
		}
	}

}
