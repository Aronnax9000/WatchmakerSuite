package net.richarddawkins.watchmaker.morph.common;

import static net.richarddawkins.watchmaker.morph.util.Random.randInt;

import javax.swing.JMenuBar;

import net.richarddawkins.watchmaker.morph.common.geom.Pic;

public abstract class SimpleMorph implements Morph {
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



  protected MorphConfig config;

  @Override
  public void setMorphConfig(MorphConfig config) {
    this.config = config;
  }

  @Override
  public MorphConfig getMorphConfig() {
    return config;
  }

}
