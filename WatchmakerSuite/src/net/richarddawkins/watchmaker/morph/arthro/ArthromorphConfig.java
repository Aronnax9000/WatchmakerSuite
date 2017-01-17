package net.richarddawkins.watchmaker.morph.arthro;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphType;
import net.richarddawkins.watchmaker.morph.Mutagen;
import net.richarddawkins.watchmaker.morph.SimpleMorphConfig;
import net.richarddawkins.watchmaker.morph.arthro.genome.ArthromorphMutagen;
import net.richarddawkins.watchmaker.morph.arthro.genome.type.Concentration;
import net.richarddawkins.watchmaker.morph.arthro.genome.type.Pressure;

public class ArthromorphConfig extends SimpleMorphConfig  {
	


	
	protected ArthromorphMutagen mutagen = new ArthromorphMutagen(this);
	public Mutagen getMutagen() {return mutagen;}
	
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


	
	public ArthromorphConfig() {
		super(MorphType.ARTHROMORPH);
		makeAllBodyMutations(true);
		makeAllAtomMutations(true);
		mutationPressure = Pressure.Zero;
		focusOfAttention = Concentration.AnySegment;
		setDefaultBreedingRows(3);
		setDefaultBreedingCols(5);
	}

	public Morph createMorph(int basicType) {
		return new Arthromorph(this, basicType);
	}

	public Concentration getFocusOfAttention() {
		return focusOfAttention;
	}



	public boolean[] getMut() {
		// TODO Auto-generated method stub
		return null;
	}

	public Pressure getMutationPressure() {
		return mutationPressure;
	}

	public boolean isAngleMut() {
		return angleMut;
	}

	public boolean isAnimalClawsMut() {
		return animalClawsMut;
	}

	public boolean isAnimalLegsMut() {
		return animalLegsMut;
	}

	public boolean isAnimalTrunkMut() {
		return animalTrunkMut;
	}

	public boolean isCentring() {
		return centring;
	}

	public boolean isClawsMut() {
		return clawsMut;
	}

	public boolean isDeletionMut() {
		return deletionMut;
	}

	public boolean isDuplicationMut() {
		return duplicationMut;
	}

	
	public boolean isHeightMut() {
		return heightMut;
	}

	
	public boolean isLegsMut() {
		return legsMut;
	}

	public boolean isSectionClawsMut() {
		return sectionClawsMut;
	}

	public boolean isSectionLegsMut() {
		return sectionLegsMut;
	}

	public boolean isSectionTrunkMut() {
		return sectionTrunkMut;
	}

	public boolean isSegmentClawsMut() {
		return segmentClawsMut;
	}

	
	public boolean isSegmentLegsMut() {
		return segmentLegsMut;
	}

	
	public boolean isSegmentTrunkMut() {
		return segmentTrunkMut;
	}

	
	public boolean isSideways() {
		return sideways;
	}

	
	public boolean isTrunkMut() {
		return trunkMut;
	}

	
	public boolean isWantColor() {
		return wantColor;
	}

	
	public boolean isWidthMut() {
		return widthMut;
	}

	
	public void makeAllAtomMutations(boolean state) {
		setWidthMut(state);
		setHeightMut(state);
		setAngleMut(state);
		setDuplicationMut(state);
		setDeletionMut(state);
	}

	
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

	
	public void setAngleMut(boolean newValue) {
		boolean oldValue = angleMut;
		angleMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("angleMut", oldValue, newValue);
		}
	}

	
	public void setAnimalClawsMut(boolean newValue) {
		boolean oldValue = animalClawsMut;
		animalClawsMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("animalClawsMut", oldValue, newValue);
		}
	}

	
	public void setAnimalLegsMut(boolean newValue) {
		boolean oldValue = animalLegsMut;
		animalLegsMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("animalLegsMut", oldValue, newValue);
		}
	}

	
	public void setAnimalTrunkMut(boolean newValue) {
		boolean oldValue = animalTrunkMut;
		animalTrunkMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("animalTrunkMut", oldValue, newValue);
		}
	}

	
	public void setCentring(boolean newValue) {
		boolean oldValue = clawsMut;
		centring = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("centring", oldValue, newValue);
		}

	}

	
	public void setClawsMut(boolean newValue) {
		boolean oldValue = clawsMut;
		clawsMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("clawsMut", oldValue, newValue);
		}
	}

	
	public void setDeletionMut(boolean newValue) {
		boolean oldValue = deletionMut;
		deletionMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("deletionMut", oldValue, newValue);
		}
	}

	
	public void setDuplicationMut(boolean newValue) {
		boolean oldValue = duplicationMut;
		duplicationMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("duplicationMut", oldValue, newValue);
		}
	}

	
	public void setFocusOfAttention(Concentration newValue) {
		Concentration oldValue = focusOfAttention;
		focusOfAttention = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("focusOfAttention", oldValue, newValue);
		}
	}

	
	public void setHeightMut(boolean newValue) {
		boolean oldValue = heightMut;
		heightMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("heightMut", oldValue, newValue);
		}
	}

	
	public void setLegsMut(boolean newValue) {
		boolean oldValue = legsMut;
		legsMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("legsMut", oldValue, newValue);
		}
	}

	
	public void setMutationPressure(Pressure newValue) {
		Pressure oldValue = mutationPressure;
		mutationPressure = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("mutationPressure", oldValue, newValue);
		}
	}

	
	public void setSectionClawsMut(boolean newValue) {
		boolean oldValue = sectionClawsMut;
		sectionClawsMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("sectionClawsMut", oldValue, newValue);
		}
	}

	
	public void setSectionLegsMut(boolean newValue) {
		boolean oldValue = sectionLegsMut;
		sectionLegsMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("sectionLegsMut", oldValue, newValue);
		}
	}

	
	public void setSectionTrunkMut(boolean newValue) {
		boolean oldValue = sectionTrunkMut;
		sectionTrunkMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("sectionTrunkMut", oldValue, newValue);
		}
	}

	
	public void setSegmentClawsMut(boolean newValue) {
		boolean oldValue = segmentClawsMut;
		segmentClawsMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("segmentClawsMut", oldValue, newValue);
		}
	}

	
	public void setSegmentLegsMut(boolean newValue) {
		boolean oldValue = segmentLegsMut;
		segmentLegsMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("segmentLegsMut", oldValue, newValue);
		}
	}

	
	public void setSegmentTrunkMut(boolean newValue) {
		boolean oldValue = segmentTrunkMut;
		segmentTrunkMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("segmentTrunkMut", oldValue, newValue);
		}
	}

	
	public void setSideways(boolean newValue) {
		boolean oldValue = clawsMut;
		sideways = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("sideways", oldValue, newValue);
		}
	}

	
	public void setTrunkMut(boolean newValue) {
		boolean oldValue = trunkMut;
		trunkMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("trunkMut", oldValue, newValue);
		}
	}

	
	public void setWantColor(boolean wantColor) {
		this.wantColor = wantColor;
	}

	
	public void setWidthMut(boolean newValue) {
		boolean oldValue = widthMut;
		widthMut = newValue;
		if (oldValue != newValue) {
			pcs.firePropertyChange("widthMut", oldValue, newValue);
		}
	}


	@Override
	public void setMutagen(Mutagen mutagen) {
		this.mutagen = (ArthromorphMutagen) mutagen;
		
	}

}
