package net.richarddawkins.watchmaker.geom;

import java.util.Collections;
import java.util.Vector;
import java.util.logging.Logger;

abstract public class BoxManager {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.geom.BoxManager");
	protected boolean accentuateMidBox = false;

	/**
	 * 
	 * @param margin a Rect describing the left, right, top, and bottom of a box.
	 * @param dim the dimensions of the grid to which the Rect's coordinates are relative.
	 */
	public abstract void addBox(Rect margin, Dim dim);

	public abstract int getBoxCount();

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
	 * Find the box within the array containing a particular point, in reverse
	 * order to the natural (drawing) order. Operating on the reversed order
	 * means that offspring precede their parents in search order. This is
	 * useful in Pedigree mode, where it is used to ensure that click order
	 * follows the reverse of drawing order, so that box-selecting is sane.
	 * 
	 * @param p
	 *            the point to locate within one of the particular boxes
	 * @param d
	 *            the size of the overall array of boxes, in pixels.
	 * @return the box number containing the given point.
	 */

	public Rect getBoxNoContainingPoint(Point p, Dim d) {
		Vector<Rect> boxes = getBoxesReversed(d);
		for (Rect box : boxes) {
			if (box.contains(p)) {
			    // Reverse the sense of the index, since we were operating on a reversed copy.
				return box;
			}
		}
		return null;
	}

	/**
	 * For the supplied dimension of the entire array of boxes, return the size
	 * of one (any) box. The returned box width is the dimension width divided
	 * by the number of columns, and the height is the dimension height divided
	 * by the number of rows.
	 * @param boxNo the 0-based index of the box in the collection.
	 * @param dimension
	 *            the size of the overall array of boxes, in pixels
	 * @return the size of an individual box within the array.
	 */
	public abstract Dim getBoxSize(int boxNo, Dim dimension);

	public abstract Rect getMidBox();

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
	public abstract Point getMidPoint(Dim dimension, Rect box);

	/**
	 * For the given dimension of the entire array of boxes, return a vector of
	 * Points describing the midpoints of each box, in row-major order.
	 * 
	 * @param dimension
	 *            the dimension of the entire array of boxes, in pixels
	 * @return a Vector of box midpoints, in row-major order.
	 */
	public abstract Vector<Point> getMidPoints(Dim dimension);

	public boolean isAccentuateMidBox() {
		return accentuateMidBox;
	}
	public void setAccentuateMidBox(boolean accentuateMidBox) {
		this.accentuateMidBox = accentuateMidBox;
	}

    public void setBox(Rect box, Rect newBox, Dim size) {
        // TODO Auto-generated method stub
        
    }

    public Rect getBox(int boxNo, Dim size) {
        return getBoxes(size).elementAt(boxNo);
    }
    public void removeBox(Rect boxedMorphBox) {
        // TODO Auto-generated method stub
        
    }

    /**
     * Return the boxes in reverse order, so that offspring are "on top" of their
     * ancestors. Useful for z-ordering so that younger morphs overlap their
     * parents in click order as well as drawing order.
     * @param scale the size of the MorphView centre panel in pixels.
     * @return the same Vector as getBoxes() in reverse order.
     */
    @SuppressWarnings("unchecked")
    public Vector<Rect> getBoxesReversed(Dim scale) {
        Vector<Rect> rects = getBoxes(scale);
        Collections.reverse((Vector<Rect>)rects.clone());
        return rects;
    }

    public Point getOrigin(Dim size, int selectedBoxNo) {
        Rect rect = getBoxes(size).elementAt(selectedBoxNo);
        return new Point(rect.left, rect.top);
    }
    protected Vector<Rect> boxes = new Vector<Rect>();

    public Rect getBox(int boxNo) {
        
        return boxes.elementAt(boxNo);
    }


}