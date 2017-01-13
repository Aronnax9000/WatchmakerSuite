package net.richarddawkins.watchmaker.morph.biomorph.geom;

public class Point implements Cloneable {

  public int h;
  public int v;
  
  public Point() {}
  
  public Point(int h, int v) {
    this.h = h;
    this.v = v;
  }

  public Point clone() {
    return new Point(h,v);
  }
  
  public java.awt.Point toPoint() { return new java.awt.Point(h,v); }
}
