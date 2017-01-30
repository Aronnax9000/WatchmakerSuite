package net.richarddawkins.watchmaker.morphview;

import java.util.Vector;

import net.richarddawkins.watchmaker.morph.Morph;

public interface MorphViewPanel   {

	Vector<Morph> getMorphs();
	void seed(Morph morph);
}
