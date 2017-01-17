package net.richarddawkins.watchmaker.gui;

import net.richarddawkins.watchmaker.morph.MorphType;

public interface SwingAppDataFactory {

	MorphType getMorphType();

	void setMorphType(MorphType morphType);

	SwingAppData newSwingAppData();

	String getName();

}