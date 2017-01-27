package net.richarddawkins.watchmaker.geom;

import java.util.Vector;
import java.util.logging.Logger;

public class FreeBoxManager extends BoxManager {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.geom.FreeBoxManager");

	protected Vector<Rect> boxes = new Vector<Rect>();
	
	public void addBox(Rect r, Dim screenSize) {
		Rect newRect = new Rect();
		
		newRect.left = Integer.MAX_VALUE / screenSize.width * r.left;
		newRect.right = Integer.MAX_VALUE / screenSize.width * r.right;
		newRect.top = Integer.MAX_VALUE / screenSize.height * r.top;
		newRect.bottom = Integer.MAX_VALUE / screenSize.height * r.bottom;

		boxes.add(newRect); 
	}
	public void removeBox(Rect r) { boxes.remove(r); }
	
	/**
	 * Returns null, as boxes are not of uniform size.
	 */
	@Override
	public Dim getBoxSize(Dim dimension) {
		return null;
	}

	@Override
	public Vector<Rect> getBoxes(Dim dimension) {
		Vector<Rect> rects = new Vector<Rect>();
		for(Rect r: boxes) {
			rects.add(this.scaleInternalRectToDimension(r, dimension));
		}
		return rects;
	}

	public void setMidPoint(Dim screenSize, Point newMidPoint, int boxNo) {
		Rect box = boxes.elementAt(boxNo);
		Point internalOldMidPoint = box.getMidPoint();
		Point internalNewMidPoint = scaleExternalPointByDimension(newMidPoint, screenSize);
		
		Point displacement = internalNewMidPoint.subtract(internalOldMidPoint);
		box.left += displacement.h;
		box.right += displacement.h;
		box.top += displacement.v;
		box.bottom += displacement.v;
	}

	protected Point scaleExternalPointByDimension(Point p, Dim dimension) {
		Point outputPoint = new Point();
		outputPoint.h = (int)((long) p.h * dimension.getWidth() / Integer.MAX_VALUE);
		outputPoint.v = (int)((long) p.v * dimension.getHeight() / Integer.MAX_VALUE);
		return outputPoint;
	}
	
	
	protected Point scaleInternalPointToDimension(Point p, Dim dimension) {
		Point outputPoint = new Point();
		outputPoint.h = (int)((long) p.h * Integer.MAX_VALUE / dimension.getWidth());
		outputPoint.v = (int)((long) p.v * Integer.MAX_VALUE / dimension.getHeight());
		return outputPoint;
	}
	
	
	protected  Rect scaleInternalRectToDimension(Rect r, Dim dimension) {
		Rect outputRect = new Rect();
		outputRect.left = (int)((long) r.left  * dimension.getWidth() / Integer.MAX_VALUE);
		outputRect.right = (int)((long) r.right * dimension.getWidth() / Integer.MAX_VALUE);
		outputRect.top = (int)((long) r.top * dimension.getHeight() / Integer.MAX_VALUE);
		outputRect.bottom = (int)((long) r.bottom * dimension.getHeight() / Integer.MAX_VALUE);
		return outputRect;
	}
	
	@Override
	public Point getMidPoint(Dim dimension, int boxNo) {
		Rect internalRect = boxes.elementAt(boxNo);
		Rect scaledRect = scaleInternalRectToDimension(internalRect, dimension);
		Point midPoint = scaledRect.getMidPoint();
		return midPoint;
	}

	@Override
	public Vector<Point> getMidPoints(Dim dimension) {
		Vector<Point> midPoints = new Vector<Point>();
		for(Rect r: boxes) {
			midPoints.add(scaleInternalRectToDimension(r, dimension).getMidPoint());
		}
		return midPoints;
	}

	@Override
	public int getMidBox() {
		return 0;
	}

	@Override
	public int getBoxCount() {
		return boxes.size();
	}

}
