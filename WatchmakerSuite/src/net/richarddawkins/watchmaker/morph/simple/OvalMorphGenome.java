package net.richarddawkins.watchmaker.morph.simple;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.SimpleGenome;
import net.richarddawkins.watchmaker.morph.Morph;

public class OvalMorphGenome extends SimpleGenome implements Cloneable {
	protected OvalMorph morph;
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
@Override
public Genome reproduce(Morph morph) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public void setBasicType(int i) {
	// TODO Auto-generated method stub
	
}
@Override
public void develop(Graphics2D g2, Dimension d, boolean zeroMargin) {
	// TODO Auto-generated method stub
	
}

}
