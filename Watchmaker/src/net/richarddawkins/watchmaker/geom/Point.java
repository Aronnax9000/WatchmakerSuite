package net.richarddawkins.watchmaker.geom;

public class Point implements Cloneable {

  public int h;
  public int v;
  
  public Point() {}
  
  public Point(int h, int v) {
    this.h = h;
    this.v = v;
  }
  public Point add(Point p) {
	  return new Point(this.h + p.h, this.v + p.v);
  }
  public Point subtract(Point p) {
	  return new Point(this.h - p.h, this.v - p.v);
  }
  
  public Point clone() {
    return new Point(h,v);
  }
  
  public String toString() {
	  return "(" + h + ", " + v + ")";
  }
  
}
