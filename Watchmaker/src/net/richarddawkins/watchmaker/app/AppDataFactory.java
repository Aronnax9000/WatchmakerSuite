package net.richarddawkins.watchmaker.app;

import java.util.Vector;

public interface AppDataFactory {

	String getMorphType();
	Object getIcon();
	AppData newAppData();
	void setMorphType(String name);
	Vector<String> getMorphTypes();
	String getName();

}