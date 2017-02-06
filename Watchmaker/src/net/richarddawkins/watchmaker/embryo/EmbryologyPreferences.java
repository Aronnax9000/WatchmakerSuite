package net.richarddawkins.watchmaker.embryo;

import java.beans.PropertyChangeListener;

public interface EmbryologyPreferences {

	public void addPropertyChangeListener(PropertyChangeListener listener);
	public void removePropertyChangeListener(PropertyChangeListener listener);
}
