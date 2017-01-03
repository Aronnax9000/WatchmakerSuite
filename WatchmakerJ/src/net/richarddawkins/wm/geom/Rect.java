package net.richarddawkins.watchmaker.geom;

import java.awt.Rectangle;

/**
 * Java class to simulate a QuickDraw Rect (rectangle)
 * 
 * In Pascal, a QuickDraw Rect is defined this way:
 * 
 * TYPE RECT = RECORD (left, top, right, bottom: INTEGER);
 * 
 * The bounding box calculations in Classic Watchmaker use a Pascal Rect record, which is defined by
 * left, top, right, and bottom coordinates. This is at variance with the Java way of doing things,
 * where Rectangles are defined by their upper left corner (x,y) and (width,height.) This class
 * bridges the two.
 * 
 * 
 * 
 * @author alan
 *
 */
public class Rect {

  public int left = 0;
  public int right = 0;
  public int top = 0;
  public int bottom = 0;

  public Rect() {
  }

  /**
   * Construct a new Rect with specified left, top, right, and bottom. This is the same order of
   * parameters with which Quickdraw Rects are initialized using SetRect.
   * 
   * @param left
   * @param top
   * @param right
   * @param bottom
   */
  public Rect(int left, int top, int right, int bottom) {
    this.left = left;
    this.right = right;
    this.top = top;
    this.bottom = bottom;
  }

  /**
   * Create a Rect object with the same origin and dimensions as a Java Rectangle.
   * 
   * @param rectangle
   */
  public Rect(Rectangle rectangle) {
    this.left = rectangle.x;
    this.right = rectangle.x + rectangle.width;
    this.top = rectangle.y;
    this.bottom = rectangle.y + rectangle.height;

  }

  /**
   * Expand this rectangle where necessary so that it encompasses the given rectangle.
   * 
   * @param r
   */
  public Rect unionRect(Rect r) {
    this.left = Math.min(left, r.left);
    this.top = Math.min(top, r.top);
    this.right = Math.max(right, r.right);
    this.bottom = Math.max(bottom, r.bottom);
    return this;
  }

  public int getWidth() {
    return right - left;
  }

  public int getHeight() {
    return bottom - top;
  }

  /**
   * Create a Java Rectangle with the equivalent origin and dimensions of this Rect.
   * 
   * @return
   */
  public Rectangle toRectangle() {
    return new Rectangle(left, top, right - left, bottom - top);
  }

  public void setRect(int newleft, int newtop, int newright, int newbottom) {
    left = newleft;
    top = newtop;
    right = newright;
    bottom = newbottom;
    
  }
}