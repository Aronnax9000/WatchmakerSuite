package net.richarddawkins.watchmaker.drawer;

import java.awt.Dimension;
import java.awt.Graphics2D;

public class MorphDrawerOld extends MorphDrawer {

	@Override
	public void drawChildren(LocatedMorph locatedMorph, Graphics2D g) {
		animate(locatedMorph, g);
		locatedMorph.getMorph().draw(g, new Dimension(0,0), false);
	}
}
