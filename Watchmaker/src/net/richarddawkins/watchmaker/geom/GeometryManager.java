package net.richarddawkins.watchmaker.geom;

public interface GeometryManager {

    Point toWatchmakerPoint(Object awtPoint);

    Dim toWatchmakerDim(Object awtDimension);

    Rect toWatchmakerRect(Object awtRectangle);

}