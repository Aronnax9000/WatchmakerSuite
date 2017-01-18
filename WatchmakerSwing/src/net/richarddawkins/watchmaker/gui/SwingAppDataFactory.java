package net.richarddawkins.watchmaker.gui;

import java.util.Vector;

import javax.swing.Icon;

public interface SwingAppDataFactory {

	String getMorphType();
	Icon getIcon();
	SwingAppData newSwingAppData();

	void setMorphType(String name);

	Vector<String> getMorphTypes();

	String getName();

}