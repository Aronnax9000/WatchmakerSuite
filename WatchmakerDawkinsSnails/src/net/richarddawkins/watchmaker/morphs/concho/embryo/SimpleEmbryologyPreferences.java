package net.richarddawkins.watchmaker.morphs.concho.embryo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import net.richarddawkins.watchmaker.embryo.EmbryologyPreferences;

public class SimpleEmbryologyPreferences implements EmbryologyPreferences {
	PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

}
