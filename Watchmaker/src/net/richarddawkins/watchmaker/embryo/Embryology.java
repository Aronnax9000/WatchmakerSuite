package net.richarddawkins.watchmaker.embryo;

import java.beans.PropertyChangeListener;

import net.richarddawkins.watchmaker.morph.Morph;

public interface Embryology extends PropertyChangeListener {
	void develop(Morph morph);
	public EmbryologyPreferences getEmbryologyPreferences();
}
