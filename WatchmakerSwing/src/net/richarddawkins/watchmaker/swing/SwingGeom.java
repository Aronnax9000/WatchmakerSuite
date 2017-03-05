package net.richarddawkins.watchmaker.swing;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.Rect;

public class SwingGeom {
    public static net.richarddawkins.watchmaker.geom.Point toWatchmakerPoint(
            Point point) {
        return new net.richarddawkins.watchmaker.geom.Point(point.x, point.y);
    }

    public static net.richarddawkins.watchmaker.geom.Dim toWatchmakerDim(
            Dimension dimension) {
        return new net.richarddawkins.watchmaker.geom.Dim(dimension.width,
                dimension.height);
    }

    public static net.richarddawkins.watchmaker.geom.Rect toWatchmakerRect(
            Rectangle r) {
        return new net.richarddawkins.watchmaker.geom.Rect(r.x, r.y,
                (int) (r.x + r.getWidth()), (int) (r.y + r.getHeight()));
    }

    public static Rectangle toRectangle(Rect margin) {
        return new Rectangle(margin.getWidth(), margin.getHeight());
    }

    public static Rectangle toRectangle(Dim dim) {
        return new Rectangle(0, 0, dim.width, dim.height);
    }

}
