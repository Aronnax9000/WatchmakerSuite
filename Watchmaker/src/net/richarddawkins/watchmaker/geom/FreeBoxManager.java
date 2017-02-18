package net.richarddawkins.watchmaker.geom;

import java.util.Vector;
import java.util.logging.Logger;

public class FreeBoxManager extends BoxManager {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.geom.FreeBoxManager");

	protected Vector<DoubleRect> boxes = new Vector<DoubleRect>();
	
	public void addBox(Rect r, Dim screenSize) {
		DoubleRect newRect = new DoubleRect();
		
		newRect.left = (double) r.left / screenSize.width;
		newRect.right = (double) r.right / screenSize.width;
		newRect.top = (double) r.top / screenSize.height;
		newRect.bottom = (double) r.bottom / screenSize.height;

		boxes.add(newRect); 
	}
	public void removeBox(Rect r) { boxes.remove(r); }
	
	/**
	 * Returns null, as boxes are not of uniform size.
	 * @return the size of the box requested scaled to the specified full-screen dimension.
	 */
	@Override
	public Dim getBoxSize(int boxNo, Dim dimension) {
		DoubleRect rect = boxes.elementAt(boxNo);
		Dim boxSize = new Dim(rect.getWidth() * dimension.width, rect.getHeight() * dimension.height);
		return boxSize;
	}

	@Override
	public Vector<Rect> getBoxes(Dim scale) {
		Vector<Rect> rects = new Vector<Rect>();
		for(DoubleRect r: boxes) {
			rects.add(r.toRect(scale));
		}
		return rects;
	}
	

	
	@Override
	public Point getMidPoint(Dim scale, int boxNo) {
		DoubleRect internalRect = boxes.elementAt(boxNo);
		Rect scaledRect = internalRect.toRect(scale);
		Point midPoint = scaledRect.getMidPoint();
		return midPoint;
	}

	@Override
	public Vector<Point> getMidPoints(Dim scale) {
		Vector<Point> midPoints = new Vector<Point>();
		for(DoubleRect r: boxes) {
			midPoints.add(r.toRect(scale).getMidPoint());
		}
		return midPoints;
	}

	@Override
	public int getMidBox() {
		return -1;
	}

	@Override
	public int getBoxCount() {
		return boxes.size();
	}

}
