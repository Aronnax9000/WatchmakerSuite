package net.richarddawkins.watchmaker.geom;

import java.util.Vector;

public interface BoxesDrawer {
	public void draw(Object graphicsContext, Dim size, BoxManager boxes, boolean midBoxOnly,
			Vector<Integer> backgroundColors);

}
