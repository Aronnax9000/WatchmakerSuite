package net.richarddawkins.watchmaker.phenotype;

import net.richarddawkins.watchmaker.geom.Rect;

public interface Phenotype {
	public int getBackgroundColor();
	public void zero();
	Rect getMargin();
	public int size();
}
