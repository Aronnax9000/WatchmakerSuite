package net.richarddawkins.watchmaker.drawer;

import java.awt.Graphics2D;

import net.richarddawkins.watchmaker.morph.biomorph.geom.Point;

public class MorphDrawerOld extends MorphDrawer {

	@Override
	public void drawChildren(LocatedMorph locatedMorph, Graphics2D g) {
		animate(locatedMorph, g);
		locatedMorph.getMorph().draw(g, new Point(0,0), false);
	}
}
