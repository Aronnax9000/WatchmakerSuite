package net.richarddawkins.watchmaker.drawer;

import java.awt.Dimension;
import java.awt.Graphics2D;

import net.richarddawkins.watchmaker.geom.LocatedMorph;

public interface GraphicsDrawer {

	void draw(LocatedMorph locatedMorph, Graphics2D g);

	void setSize(Dimension boxSize);

}
