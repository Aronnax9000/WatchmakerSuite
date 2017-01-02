package net.richarddawkins.watchmaker.morphs;

import static net.richarddawkins.watchmaker.Random.randInt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JMenuBar;

import net.richarddawkins.watchmaker.geom.Point;

public abstract class SimpleMorphImpl implements Morph {
  Morph parent;
  Morph firstBorn;
  Morph lastBorn;
  Morph elderSib;
  Morph youngerSib;
  Morph prec;
  Morph next;

  public Morph getParent() {
    return parent;
  }

  public int getOffspringCount(boolean deep) {
    int count = 0;
    Morph child = firstBorn;
    while (child != null) {
      count++;
      if (deep)
        count += child.getOffspringCount(true);
      child = child.getYoungerSib();
    }

    return count;
  }

  public void setParent(Morph parent) {
    this.parent = parent;
    if (parent.getFirstBorn() == null)
      parent.setFirstBorn(this);
    else {
      parent.getLastBorn().setYoungerSib(this);
      this.elderSib = lastBorn;
    }
    this.parent.setLastBorn(this);
  }

  public Morph getFirstBorn() {
    return firstBorn;
  }

  public void setFirstBorn(Morph firstBorn) {
    this.firstBorn = firstBorn;
  }

  public Morph getLastBorn() {
    return lastBorn;
  }

  public void setLastBorn(Morph lastBorn) {
    this.lastBorn = lastBorn;
  }

  public Morph getElderSib() {
    return elderSib;
  }

  public void setElderSib(Morph elderSib) {
    this.elderSib = elderSib;
  }

  public Morph getYoungerSib() {
    return youngerSib;
  }

  public void setYoungerSib(Morph youngerSib) {
    this.youngerSib = youngerSib;
  }

  public Morph getPrec() {
    return prec;
  }

  public void setPrec(Morph prec) {
    this.prec = prec;
  }

  public Morph getNext() {
    return next;
  }

  public void setNext(Morph next) {
    this.next = next;
  }

  public void draw(Graphics2D g2, Dimension d, boolean midBox) {
    g2.setColor(Color.BLACK);
//    g2.drawString("Offspring " + this.getOffspringCount(false), 10, 20);
//    g2.drawString(d.getWidth() + "x" + d.getHeight(), 10, 40);
    delayvelop(g2, d, midBox);
  }

  static void populateMenuBar(JMenuBar menuBar) {
    System.out.println("Morph populateMenuBar");
  }

  public int mutSizeGene;

  protected int direction() {
    return randInt(2) == 2 ? mutSizeGene : -mutSizeGene;
  }

  protected Pic pic;

  public Pic getPic() {
    return pic;
  }

  public void setPic(Pic pic) {
    this.pic = pic;
  }

  protected Person genome;

  public Person getGenome() {
    return genome;
  }

  public void setGenome(Person genome) {
    this.genome = genome;
  }

  protected MorphConfig config;

  @Override
  public void setMorphConfig(MorphConfig config) {
    this.config = config;
  }

  @Override
  public MorphConfig getMorphConfig() {
    return config;
  }

  public void delayvelop(Graphics2D g2, Dimension d, boolean midBox) {
    int margcentre, offset;
    Point offCentre = new Point();
    // Zeromargin := TRUE;
    Point p = new Point();
    p.h = d.width / 2;
    p.v = d.height / 2;
    
    genome.develop(null, d, true); // null equivalent to classic DelayedDrawing := TRUE;
    // DelayedDrawing := FALSE;
    margcentre = pic.margin.top + (pic.margin.bottom - pic.margin.top) / 2;
    offset = margcentre - p.v;
    pic.margin.top -= offset;
    pic.margin.bottom -= offset;
    offCentre.h = p.h;
    offCentre.v = p.v - offset;
    pic.drawPic(g2, d, offCentre, this);
    if(config.isShowBoundingBoxes()) {
      g2.setColor(Color.RED);
      Rectangle rectangle = pic.margin.toRectangle();
      g2.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
    }

}
