package net.richarddawkins.watchmaker.genome;

import java.awt.Cursor;
import java.beans.PropertyChangeListener;

public interface Gene {
	Genome getGenome();
	void setGenome(Genome genome);
	String getName();
	void setName(String name);
	void copy(Gene gene);
	void removePropertyChangeListener(PropertyChangeListener listener);
	void addPropertyChangeListener(PropertyChangeListener listener);
	void goose(Cursor cursor);
	int getGooseSize();
}
