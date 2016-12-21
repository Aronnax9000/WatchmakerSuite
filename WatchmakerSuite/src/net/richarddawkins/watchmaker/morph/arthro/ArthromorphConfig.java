package net.richarddawkins.watchmaker.morph.arthro;

import net.richarddawkins.watchmaker.gui.menu.MenuBuilder;
import net.richarddawkins.watchmaker.morph.common.MorphConfig;

public interface ArthromorphConfig extends MorphConfig {
	void makeAllBodyMutations(boolean state);

	void makeAllAtomMutations(boolean state);

	boolean isTrunkMut();

	void setTrunkMut(boolean newValue);

	boolean isLegsMut();

	void setLegsMut(boolean newValue);

	boolean isClawsMut();

	void setClawsMut(boolean newValue);

	boolean isAnimalTrunkMut();

	void setAnimalTrunkMut(boolean newValue);

	boolean isAnimalLegsMut();

	void setAnimalLegsMut(boolean newValue);

	boolean isAnimalClawsMut();

	void setAnimalClawsMut(boolean newValue);

	boolean isSectionTrunkMut();

	void setSectionTrunkMut(boolean newValue);

	boolean isSectionLegsMut();

	void setSectionLegsMut(boolean newValue);

	boolean isSectionClawsMut();

	void setSectionClawsMut(boolean newValue);

	boolean isSegmentTrunkMut();

	void setSegmentTrunkMut(boolean newValue);

	boolean isSegmentLegsMut();

	void setSegmentLegsMut(boolean newValue);

	boolean isSegmentClawsMut();

	void setSegmentClawsMut(boolean newValue);

	boolean isWidthMut();

	void setWidthMut(boolean newValue);

	boolean isHeightMut();

	void setHeightMut(boolean newValue);

	boolean isAngleMut();

	void setAngleMut(boolean newValue);

	boolean isDuplicationMut();

	void setDuplicationMut(boolean newValue);

	boolean isDeletionMut();

	void setDeletionMut(boolean newValue);

	Pressure getMutationPressure();

	void setMutationPressure(Pressure newValue);

	Concentration getFocusOfAttention();

	void setFocusOfAttention(Concentration newValue);

	boolean isWantColor();

	void setWantColor(boolean wantColor);

	boolean isSideways();

	void setSideways(boolean sideways);

	MenuBuilder getMenuBuilder();

	boolean[] getMut();
	
	boolean isCentring();
	void setCentring(boolean centring);

}