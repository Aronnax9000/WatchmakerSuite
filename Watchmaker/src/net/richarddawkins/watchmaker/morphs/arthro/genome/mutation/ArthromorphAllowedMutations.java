package net.richarddawkins.watchmaker.morphs.arthro.genome.mutation;

import net.richarddawkins.watchmaker.genome.mutation.SimpleAllowedMutations;
import net.richarddawkins.watchmaker.morphs.arthro.genome.type.Concentration;
import net.richarddawkins.watchmaker.morphs.arthro.genome.type.Pressure;

public class ArthromorphAllowedMutations extends SimpleAllowedMutations {
	
	public ArthromorphAllowedMutations() {

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


	
	public void setFocusOfAttention(Concentration newValue) {
		Concentration oldValue = this.focusOfAttention;
		this.focusOfAttention = newValue;
		pcs.firePropertyChange("", oldValue, newValue);
	}


	public void setMutationPressure(Pressure newValue) {
		Pressure oldValue = this.mutationPressure;
		this.mutationPressure = newValue;
		pcs.firePropertyChange("", oldValue, newValue);
	}


	public void setAnimalClawsMut(boolean newValue) {
		boolean oldValue = this.animalClawsMut;
		this.animalClawsMut = newValue;
		pcs.firePropertyChange("", oldValue, newValue);
	}


	public void setAnimalLegsMut(boolean newValue) {
		boolean oldValue = this.animalLegsMut;
		this.animalLegsMut = newValue;
		pcs.firePropertyChange("", oldValue, newValue);
	}


	public void setAnimalTrunkMut(boolean newValue) {
		boolean oldValue = this.animalTrunkMut;
		this.animalTrunkMut = newValue;
		pcs.firePropertyChange("", oldValue, newValue);
	}


	public void setClawsMut(boolean newValue) {
		boolean oldValue = this.clawsMut;
		this.clawsMut = newValue;
		pcs.firePropertyChange("", oldValue, newValue);
	}


	public void setDeletionMut(boolean newValue) {
		boolean oldValue = this.deletionMut;
		this.deletionMut = newValue;
		pcs.firePropertyChange("", oldValue, newValue);
	}


	public void setDuplicationMut(boolean newValue) {
		boolean oldValue = this.duplicationMut;
		this.duplicationMut = newValue;
		pcs.firePropertyChange("", oldValue, newValue);
	}


	public void setHeightMut(boolean newValue) {
		boolean oldValue = this.heightMut;
		this.heightMut = newValue;
		pcs.firePropertyChange("", oldValue, newValue);
	}


	public void setLegsMut(boolean newValue) {
		boolean oldValue = this.legsMut;
		this.legsMut = newValue;
		pcs.firePropertyChange("", oldValue, newValue);
	}


	public void setSectionClawsMut(boolean newValue) {
		boolean oldValue = this.sectionClawsMut;
		this.sectionClawsMut = newValue;
		pcs.firePropertyChange("", oldValue, newValue);
	}


	public void setSectionLegsMut(boolean newValue) {
		boolean oldValue = this.sectionLegsMut;
		this.sectionLegsMut = newValue;
		pcs.firePropertyChange("", oldValue, newValue);
	}


	public void setSectionTrunkMut(boolean newValue) {
		boolean oldValue = this.sectionTrunkMut;
		this.sectionTrunkMut = newValue;
		pcs.firePropertyChange("", oldValue, newValue);
	}


	public void setSegmentClawsMut(boolean newValue) {
		boolean oldValue = this.segmentClawsMut;
		this.segmentClawsMut = newValue;
		pcs.firePropertyChange("", oldValue, newValue);
	}


	public void setSegmentLegsMut(boolean newValue) {
		boolean oldValue = this.segmentLegsMut;
		this.segmentLegsMut = newValue;
		pcs.firePropertyChange("", oldValue, newValue);
	}


	public void setSegmentTrunkMut(boolean newValue) {
		boolean oldValue = this.segmentTrunkMut;
		this.segmentTrunkMut = newValue;
		pcs.firePropertyChange("", oldValue, newValue);
	}


	public void setTrunkMut(boolean newValue) {
		boolean oldValue = this.trunkMut;
		this.trunkMut = newValue;
		pcs.firePropertyChange("", oldValue, newValue);
	}


	public void setWidthMut(boolean newValue) {
		boolean oldValue = this.widthMut;
		this.widthMut = newValue;
		pcs.firePropertyChange("", oldValue, newValue);
	}



	public Concentration focusOfAttention = Concentration.AnySegment;
	public Pressure mutationPressure = Pressure.Zero;
	public boolean angleMut = true;
	public boolean animalClawsMut = true;
	public boolean animalLegsMut = true;
	public boolean animalTrunkMut = true;
	public boolean clawsMut = true;
	public boolean deletionMut = true;
	public boolean duplicationMut = true;
	public boolean heightMut = true;
	public boolean legsMut = true;
	public boolean sectionClawsMut = true;
	public boolean sectionLegsMut = true;
	public boolean sectionTrunkMut = true;
	public boolean segmentClawsMut = true;
	public boolean segmentLegsMut = true;
	public boolean segmentTrunkMut = true;
	public boolean trunkMut = true;
	public boolean widthMut = true;

}
