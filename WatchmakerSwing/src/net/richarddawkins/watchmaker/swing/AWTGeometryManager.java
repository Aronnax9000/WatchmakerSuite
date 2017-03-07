package net.richarddawkins.watchmaker.swing;

import java.awt.Dimension;
import java.awt.Rectangle;

import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.GeometryManager;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.geom.Point;

public class AWTGeometryManager implements GeometryManager {
    @Override
    public Point toWatchmakerPoint(Object awtPoint) {
        java.awt.Point point = (java.awt.Point) awtPoint;
        return new Point(point.x, point.y);
    }

    @Override
    public Dim toWatchmakerDim(Object awtDimension) {
        Dimension dimension = (Dimension) awtDimension;
        return new Dim(dimension.width, dimension.height);
    }

    @Override
    public Rect toWatchmakerRect(Object awtRectangle) {
        Rectangle r = (Rectangle) awtRectangle;
        return new Rect(r.x, r.y, (int) (r.x + r.getWidth()),
                (int) (r.y + r.getHeight()));
    }

}
