package net.richarddawkins.watchmaker.morph.draw;

import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;

public interface MorphDrawer {


	void draw(BoxedMorph locatedMorph, Object graphicsContext, Dim d, boolean selectionState, boolean isShowBoundingBoxes, boolean clip);

}
