package net.richarddawkins.watchmaker.genome;

import java.beans.PropertyChangeListener;

public interface Gene extends GeneManipulationListener {
	Genome getGenome();
	void setGenome(Genome genome);
	String getName();
	void setName(String name);
	void copy(Gene gene);
	void removePropertyChangeListener(PropertyChangeListener listener);
	void addPropertyChangeListener(PropertyChangeListener listener);
	int getGooseSize();
	double getDoubleGooseSize();
}
