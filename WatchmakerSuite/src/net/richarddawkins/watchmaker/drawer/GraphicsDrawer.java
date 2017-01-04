package net.richarddawkins.watchmaker.drawer;

import java.awt.Dimension;
import java.awt.Graphics2D;

public interface GraphicsDrawer {

	void draw(LocatedMorph locatedMorph, Graphics2D g);

	void setSize(Dimension boxSize);

}
