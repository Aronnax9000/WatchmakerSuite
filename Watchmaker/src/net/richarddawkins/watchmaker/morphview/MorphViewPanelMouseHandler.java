package net.richarddawkins.watchmaker.morphview;

import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.Point;

public interface MorphViewPanelMouseHandler {
    void processMouseClicked(Point point, Dim size);
    void processMouseDragged(Point watchmakerPoint, Dim watchmakerDim);
    void processMouseMotion(Point myPt, Dim size);
    void processMousePressed(Point watchmakerPoint, Dim watchmakerDim);
    void processMouseReleased(Point watchmakerPoint, Dim watchmakerDim);
}
