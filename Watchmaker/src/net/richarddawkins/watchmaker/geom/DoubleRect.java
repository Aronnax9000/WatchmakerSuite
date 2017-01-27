package net.richarddawkins.watchmaker.geom;


/**
 * Java class to simulate a QuickDraw Rect (rectangle)
 * 
 * In Pascal, a QuickDraw Rect is defined this way:
 * 
 * TYPE RECT = RECORD (left, top, right, bottom: INTEGER);
 * 
 * The bounding box calculations in Classic Watchmaker use a Pascal Rect record,
 * which is defined by left, top, right, and bottom coordinates. This is at
 * variance with the Java way of doing things, where Rectangles are defined by
 * their upper left corner (x,y) and (width,height). This class bridges the two.
 * 
 * The fields (left, right, top, bottom) are Integers, and start as nulls. This
 * is useful for margin calculations.
 * 
 * 
 * This implementation of Rect also includes convenience methods for determining
 * the midpoint of the Rect, and for expanding the boundaries of the rectangle
 * in response to supplied values.
 * 
 * 
 * @author alan
 *
 */
public class DoubleRect {

	public Double left = 0d;
	public Double right = 0d;
	public Double top = 0d;
	public Double bottom = 0d;

	public void zero() {
		left = 0d;
		right = 0d;
		top = 0d;
		bottom = 0d;
	}

	/**
	 * Push the Rect's left boundary leftward if necessary. If the left value
	 * was null, set it to the new left value.
	 * 
	 * @param left
	 *            the potential new lefthand boundary
	 */
	public void expandLeft(double left, double thick) {
		double extent = left - thick / 2;
		if (this.left == null || extent < this.left)
			this.left = extent;
	}

	/**
	 * Push the Rect's righthand boundary rightward if necessary. If the
	 * righthand value was null, set it to the new righthand value.
	 * 
	 * @param right
	 *            the potential new righthand boundary
	 */
	public void expandRight(double right, double thick) {
		double extent = right + thick / 2;
		if (this.right == null || extent > this.right)
			this.right = extent;
	}

	/**
	 * Push the Rect's top boundary upward if necessary. If the top value was
	 * null, set it to the new top value.
	 * 
	 * @param top
	 *            the potential new top boundary
	 */
	public void expandTop(double top, double thick) {
		double extent = top - thick / 2;
		if (this.top == null || extent < this.top)
			this.top = extent;
	}

	/**
	 * Push the Rect's bottom boundary downward if necessary. If the bottom
	 * value was null, set it to the new top value.
	 * 
	 * @param bottom
	 *            the potential new bottom boundary
	 */
	public void expandBottom(double bottom, double thick) {
		double extent = bottom + thick / 2;
		if (this.bottom == null || extent > this.bottom)
			this.bottom = extent;
	}

	public void expandPoint(Point point, double thick) {
		expandLeft(point.h, thick);
		expandRight(point.h, thick);
		expandTop(point.v, thick);
		expandBottom(point.v, thick);
	}

	public void expandHorizontal(double h, double thick) {
		expandLeft(h, thick);
		expandRight(h, thick);
	}

	public void expandVertical(double v, double thick) {
		expandTop(v, thick);
		expandBottom(v, thick);
	}

	public Point getMidPoint() {
		return new Point((int)(left + (right - left) / 2), (int)(top + (bottom - top) / 2));
	}

	public DoubleRect() {
	}

	/**
	 * Construct a new Rect with specified left, top, right, and bottom. This is
	 * the same order of parameters with which Quickdraw Rects are initialized
	 * using SetRect.
	 * 
	 * @param left
	 *            left edge of the rectangle.
	 * @param top
	 *            top edge of the rectangle.
	 * @param right
	 *            right edge of the rectangle.
	 * @param bottom
	 *            bottom edge of the rectangle.
	 */
	public DoubleRect(double left, double top, double right, double bottom) {
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}

	@Override
	public String toString() {
		return "Rect((" + left + ", " + top + "), " + getWidth() + "x" + getHeight() + ")";
	}
	


	/**
	 * Expand this rectangle where necessary so that it encompasses the given
	 * rectangle.
	 * 
	 * @param r
	 *            the rectangle to be union'ed with this one.
	 * @return the union'ed rectangle
	 */
	public DoubleRect unionRect(DoubleRect r) {
		this.left = Math.min(left, r.left);
		this.top = Math.min(top, r.top);
		this.right = Math.max(right, r.right);
		this.bottom = Math.max(bottom, r.bottom);
		return this;
	}

	public double getWidth() {
		return Math.abs(right - left);
	}

	public double getHeight() {
		return Math.abs(bottom - top);
	}


	public void setRect(double newleft, double newtop, double newright, double newbottom) {
		left = newleft;
		top = newtop;
		right = newright;
		bottom = newbottom;
	}

	public boolean contains(Point p) {
		return !(p.h < left || p.h > right || p.v < top || p.v > bottom);

	}
}