package net.richarddawkins.watchmaker.geom;

import java.util.Vector;
import java.util.logging.Logger;

abstract public class BoxManager {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.geom.BoxManager");
	/**
	 * Find the box within the array containing a particular point.
	 * 
	 * @param p
	 *            the point to locate within one of the particular boxes
	 * @param d
	 *            the size of the overall array of boxes, in pixels.
	 * @return the box number containing the given point.
	 */

	public int getBoxNoContainingPoint(Point p, Dim d) {
		int boxIndex = 0;
		for (Rect box : getBoxes(d)) {
			if (box.contains(p)) {
				return boxIndex;
			}
			boxIndex++;
		}
		return -1;
	}

	/**
	 * For the supplied dimension of the entire array of boxes, return the size
	 * of one (any) box. The returned box width is the dimension width divided
	 * by the number of columns, and the height is the dimension height divided
	 * by the number of rows.
	 * 
	 * @param dimension
	 *            the size of the overall array of boxes, in pixels
	 * @return the size of an individual box within the array.
	 */
	public abstract Dim getBoxSize(Dim dimension);

	/**
	 * For the given dimension of the entire array of boxes, return a vector of
	 * Rectangles describing the corners of each box, in row-major order.
	 * 
	 * @param dimension
	 *            the size of the overall array of boxes, in pixels.
	 * @return a Vector of Rectangles representing individual boxes, in
	 *         row-major order.
	 */
	public abstract Vector<Rect> getBoxes(Dim dimension);

	/**
	 * Given supplied dimension of the entire array of boxes, return the
	 * midpoint of the nth box (starting with n = 0).
	 * 
	 * @param dimension
	 *            the dimensions of the overall array of boxes.
	 * @param boxNo
	 *            the number of the box to retrieve (row major order)
	 * @return the midpoint of the nth Box.
	 */
	public abstract Point getMidPoint(Dim dimension, int boxNo);

	/**
	 * For the given dimension of the entire array of boxes, return a vector of
	 * Points describing the midpoints of each box, in row-major order.
	 * 
	 * @param dimension
	 *            the dimension of the entire array of boxes, in pixels
	 * @return a Vector of box midpoints, in row-major order.
	 */
	public abstract Vector<Point> getMidPoints(Dim dimension);

	public abstract int getMidBox();

	public abstract int getBoxCount();

	public void setMidPoint(Dim dim, Point point, int i) {
		
		
	}

	public void addBox(Rect margin, Dim dim) {
	}

	protected boolean accentuateMidBox = false;
	public boolean isAccentuateMidBox() {
		return accentuateMidBox;
	}

	public void setAccentuateMidBox(boolean accentuateMidBox) {
		this.accentuateMidBox = accentuateMidBox;
	}
}