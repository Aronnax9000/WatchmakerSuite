package net.richarddawkins.watchmaker.geom;

import net.richarddawkins.watchmaker.util.Globals;

/**
 * Java class to simulate a QuickDraw Rect (rectangle) with double coordinates
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
 * 
 * This implementation of Rect also includes convenience methods for determining
 * the midpoint of the Rect, and for expanding the boundaries of the rectangle
 * in response to supplied values.
 * 
 * 
 * @author alan
 *
 */
public class DoubleRect implements Cloneable {


	
	public Object clone() {
		DoubleRect r = new DoubleRect();
		r.left = left;
		r.right = right;
		r.top = top;
		r.bottom = bottom;
		return r;
	}
	
	public double left = 0;
	public double right = 0;
	public double top = 0;
	public double bottom = 0;

	public void zero() {
		left = 0;
		right = 0;
		top = 0;
		bottom = 0;
	}

	/**
	 * Push the Rect's left boundary leftward if necessary. If the left value
	 * was null, set it to the new left value.
	 * 
	 * @param left the potential new lefthand boundary
	 * @param thick the thickness of the drawing primitive
	 */
	public void expandLeft(double left, double thick) {
		double extent = left - thick / 2;
		if (extent < this.left)
			this.left = extent;
	}

	/**
	 * Push the Rect's righthand boundary rightward if necessary. If the
	 * righthand value was null, set it to the new righthand value.
	 * 
	 * @param right the potential new righthand boundary
	 * @param thick the thickness of the drawing primitive
	 */
	public void expandRight(double right, double thick) {
		double extent = right + thick / 2;
		if (extent > this.right)
			this.right = extent;
	}

	/**
	 * Push the Rect's top boundary upward if necessary. If the top value was
	 * null, set it to the new top value.
	 * 
	 * @param top the potential new top boundary
	 * @param thick the thickness of the drawing primitive
	 */
	public void expandTop(double top, double thick) {
		double extent = top - thick / 2;
		if (extent < this.top)
			this.top = extent;
	}

	/**
	 * Push the Rect's bottom boundary downward if necessary. If the bottom
	 * value was null, set it to the new top value.
	 * 
	 * @param bottom the potential new bottom boundary
	 * @param thick the thickness of the drawing primitive
	 */
	public void expandBottom(double bottom, double thick) {
		double extent = bottom + thick / 2;
		if (extent > this.bottom)
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

	public DoublePoint getMidPoint() {
		return new DoublePoint(left + (right - left) / 2, top + (bottom - top) / 2);
	}
	
	public Rect toRect(Dim scale) {
		
		Rect rect = new Rect();
		rect.left = (int) Math.round(scale.width * left);
		rect.top = (int) Math.round(scale.height * top);
		rect.right = (int) Math.round(scale.width * right);
		rect.bottom = (int) Math.round(scale.height * bottom);
		return rect;
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
		return "Rect topleft: (" + left + ", " + top + "), WxH:" + getWidth() + "x" + getHeight() + ")";
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

	public Dim getDim() {
		 return new Dim(getWidth(), getHeight());
	}

	public void setRect(double newleft, double newtop, double newright, double newbottom) {
		left = newleft;
		top = newtop;
		right = newright;
		bottom = newbottom;
	}

	public void zeroRect() {
		left = 0;
		right = 0;
		top = 0;
		bottom = 0;
	}

	public boolean contains(Point p) {
		return !(p.h < left || p.h > right || p.v < top || p.v > bottom);

	}

    public DoubleRect getScaled(int scale) {
        double scaleFactor = Math.pow(Globals.zoomBase, scale);
        DoublePoint midPoint = this.getMidPoint();
        double newHalfWidth = this.getWidth() * scaleFactor / 2;
        double newHalfHeight = this.getHeight() * scaleFactor / 2;
        
        
        DoubleRect scaledRect = new DoubleRect();
        scaledRect.left = midPoint.h - newHalfWidth;
        scaledRect.right = midPoint.h + newHalfWidth;
        scaledRect.top = midPoint.v - newHalfHeight;
        scaledRect.bottom = midPoint.v + newHalfHeight;
        
        
        return scaledRect;
    }
}