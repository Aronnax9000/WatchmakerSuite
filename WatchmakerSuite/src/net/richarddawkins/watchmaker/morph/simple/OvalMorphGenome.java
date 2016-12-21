package net.richarddawkins.watchmaker.morph.simple;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

public class OvalMorphGenome {
  public Color color = Color.RED;
  public Point origin = new Point(0,0);
  public Dimension dimension = new Dimension(5,4);
  public Object clone() {
	  OvalMorphGenome clone = new OvalMorphGenome();
	  clone.color = color;
	  clone.origin = (Point) origin.clone();
	  clone.dimension = (Dimension) dimension.clone();
	  return clone;
  }
}
