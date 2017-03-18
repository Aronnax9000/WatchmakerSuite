package net.richarddawkins.watchmaker.geom;

import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;

public class FreeBoxManager extends BoxManager {
    @SuppressWarnings("unused")
    private static Logger logger = Logger
            .getLogger("net.richarddawkins.watchmaker.geom.FreeBoxManager");

    protected Vector<DoubleRect> doubleBoxes = new Vector<DoubleRect>();

    public void addBox(Rect r) {
        DoubleRect newRect = new DoubleRect();

        newRect.left = r.left;
        newRect.right = r.right;
        newRect.top = r.top;
        newRect.bottom = r.bottom;

        doubleBoxes.add(newRect);
        boxes.add(r);
    }
    
    public void addBox(Rect r, Dim scale) {
        DoubleRect newRect = new DoubleRect();

        newRect.left = (double) r.left / scale.width;
        newRect.right = (double) r.right / scale.width;
        newRect.top = (double) r.top / scale.height;
        newRect.bottom = (double) r.bottom / scale.height;

        doubleBoxes.add(newRect);
        boxes.add(r);
    }

    /**
     * Returns null, as doubleBoxes are not of uniform size.
     * 
     * @return the size of the box requested scaled to the specified full-screen
     *         dimension.
     */
    @Override
    public Dim getBoxSize(Rect box, Dim dimension) {
        DoubleRect rect = doubleBoxes.elementAt(boxes.indexOf(box));
        Dim boxSize = new Dim(rect.getWidth() * dimension.width,
                rect.getHeight() * dimension.height);
        return boxSize;
    }
    @Override
    public void moveToEnd(Rect box) {
        DoubleRect doubleRect = doubleBoxes.elementAt(boxes.indexOf(box));
        doubleBoxes.remove(doubleRect);
        boxes.remove(box);
        doubleBoxes.add(doubleRect);
        boxes.add(box);
    }
    
    @Override
    public Vector<Rect> getBoxes(Dim screenSize) {
        Iterator<Rect> rects = boxes.iterator();
        for (DoubleRect doubleRect : doubleBoxes) {
            Rect rect = rects.next();
            Rect r = doubleRect.getScaled(scale).toRect(screenSize);
            rect.setRect(r.left, r.top, r.right, r.bottom);
        }
        return boxes;
    }

    @Override
    public void setBox(Rect shadowBox, Rect newBox, Dim size) {
        DoubleRect newRect = doubleBoxes.elementAt(boxes.indexOf(shadowBox));
        newRect.left = (double) newBox.left / size.width;
        newRect.right = (double) newBox.right / size.width;
        newRect.top = (double) newBox.top / size.height;
        newRect.bottom = (double) newBox.bottom / size.height;
    }

    @Override
    public Point getMidPoint(Dim scale, Rect box) {
        DoubleRect internalRect = doubleBoxes.elementAt(boxes.indexOf(box));
        Rect scaledRect = internalRect.toRect(scale);
        Point midPoint = scaledRect.getMidPoint();
        return midPoint;
    }

    @Override
    public void removeBox(Rect box) {
        doubleBoxes.remove(boxes.indexOf(box));
        boxes.remove(box);
    }

    @Override
    public Vector<Point> getMidPoints(Dim scale) {
        Vector<Point> midPoints = new Vector<Point>();
        for (DoubleRect r : doubleBoxes) {
            midPoints.add(r.toRect(scale).getMidPoint());
        }
        return midPoints;
    }

    @Override
    public Rect getMidBox() {
        return null;
    }

    @Override
    public int getBoxCount() {
        return doubleBoxes.size();
    }

    @Override
    public Rect firstElement() {
        
        return getBox(0);
    }

}
