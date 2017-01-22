package net.richarddawkins.watchmaker.app;

import java.util.Vector;

import javax.swing.Icon;

public interface AppDataFactory {

	String getMorphType();
	Icon getIcon();
	AppData newAppData();

	void setMorphType(String name);

	Vector<String> getMorphTypes();

	String getName();

}