package net.richarddawkins.watchmaker.swing;

import java.awt.Dimension;
import java.awt.Point;

public class SwingGeom {
	public static net.richarddawkins.watchmaker.geom.Point toWatchmakerPoint(Point point) {
		return 
				new net.richarddawkins.watchmaker.geom.Point(point.x, point.y);
	}
	public static net.richarddawkins.watchmaker.geom.Dim toWatchmakerDim(Dimension dimension) {
		return 
				new net.richarddawkins.watchmaker.geom.Dim(dimension.width, dimension.height);
	}
}
