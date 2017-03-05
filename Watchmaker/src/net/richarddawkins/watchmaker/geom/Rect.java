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
public class Rect implements Cloneable {
    public Object clone() {
        Rect r = new Rect();
        r.left = left;
        r.right = right;
        r.top = top;
        r.bottom = bottom;
        return r;
    }

    public int getArea() {
        return getWidth() * getHeight();
    }

    public int left = 0;
    public int right = 0;
    public int top = 0;
    public int bottom = 0;

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
     * @param left
     *            the potential new lefthand boundary
     * @param thick
     *            the thickness of the drawing primitive
     */
    public void expandLeft(int left, int thick) {
        int extent = left - thick / 2;
        if (extent < this.left)
            this.left = extent;
    }

    /**
     * Push the Rect's righthand boundary rightward if necessary. If the
     * righthand value was null, set it to the new righthand value.
     * 
     * @param right
     *            the potential new righthand boundary
     * @param thick
     *            the thickness of the drawing primitive
     */
    public void expandRight(int right, int thick) {
        int extent = right + thick / 2;
        if (extent > this.right)
            this.right = extent;
    }

    /**
     * Push the Rect's top boundary upward if necessary. If the top value was
     * null, set it to the new top value.
     * 
     * @param top
     *            the potential new top boundary
     * @param thick
     *            the thickness of the drawing primitive
     */
    public void expandTop(int top, int thick) {
        int extent = top - thick / 2;
        if (extent < this.top)
            this.top = extent;
    }

    /**
     * Push the Rect's bottom boundary downward if necessary. If the bottom
     * value was null, set it to the new top value.
     * 
     * @param bottom
     *            the potential new bottom boundary
     * @param thick
     *            the thickness of the drawing primitive
     */
    public void expandBottom(int bottom, int thick) {
        int extent = bottom + thick / 2;
        if (extent > this.bottom)
            this.bottom = extent;
    }

    public void expandPoint(Point point, int thick) {
        expandLeft(point.h, thick);
        expandRight(point.h, thick);
        expandTop(point.v, thick);
        expandBottom(point.v, thick);
    }

    public void expandHorizontal(int h, int thick) {
        expandLeft(h, thick);
        expandRight(h, thick);
    }

    public void expandVertical(int v, int thick) {
        expandTop(v, thick);
        expandBottom(v, thick);
    }

    public Point getMidPoint() {
        return new Point(left + (right - left) / 2, top + (bottom - top) / 2);
    }

    public Rect() {
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
    public Rect(int left, int top, int right, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public Rect(Rect r) {
        this.left = r.left;
        this.right = r.right;
        this.top = r.top;
        this.bottom = r.bottom;

    }

    @Override
    public String toString() {
        return "Rect (" + left + ", " + top + "), (" + right + ", " + bottom + ") WxH:" + getWidth()
                + "x" + getHeight() + "=" + getArea();
    }

    /**
     * Expand this rectangle where necessary so that it encompasses the given
     * rectangle.
     * 
     * @param r
     *            the rectangle to be union'ed with this one.
     * @return the union'ed rectangle
     */
    public Rect unionRect(Rect r) {
        this.left = Math.min(left, r.left);
        this.top = Math.min(top, r.top);
        this.right = Math.max(right, r.right);
        this.bottom = Math.max(bottom, r.bottom);
        return this;
    }

    public int getWidth() {
        return Math.abs(right - left);
    }

    public int getHeight() {
        return Math.abs(bottom - top);
    }

    public Dim getDim() {
        return new Dim(getWidth(), getHeight());
    }

    public void setRect(int newleft, int newtop, int newright, int newbottom) {
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
}