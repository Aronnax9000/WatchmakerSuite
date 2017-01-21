package net.richarddawkins.watchmaker.morph.draw;

import java.awt.Dimension;

import net.richarddawkins.watchmaker.geom.LocatedMorph;

public interface MorphDrawer {

	void draw(LocatedMorph locatedMorph, Object graphicsContext);

	void setSize(Dimension boxSize);

}
