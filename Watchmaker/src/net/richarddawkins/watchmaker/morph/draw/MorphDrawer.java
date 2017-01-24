package net.richarddawkins.watchmaker.morph.draw;

import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;

public interface MorphDrawer {

	void setSize(Dim boxSize);

	void draw(BoxedMorph locatedMorph, Object graphicsContext, Dim d);

}
