package net.richarddawkins.watchmaker.morph.snail;

import java.awt.Dimension;
import java.awt.Graphics2D;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.SimpleGenome;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Point;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Rect;

public class SnailGenome extends SimpleGenome implements Cloneable {
  public static boolean sideView = false;

  int coarsegraininess;

  double dDisplacement;

  double dGradient;

  int generatingCurve;

  int handedness;

  int mutProb;

  int reach;

  double sShape;

  double translationGradient;

  double tTranslation;

  double wOpening;

  public SnailGenome(Morph morph) {
    super(morph);
  }

  /**
   * DDisplacement is not allowed to be less than zero or greater than 1.
   * @param summand the quantity to add to DDisplacement.
   */
  public void addToDDisplacement(double summand) {
    dDisplacement += summand;
    if (dDisplacement < 0)
      dDisplacement = 0;
    if (dDisplacement > 1)
      dDisplacement = 1;
  }

  public void addToTTranslation(double summand) {
    tTranslation += summand;
  }

  @Override
  public void develop(Graphics2D g2, Dimension d, boolean zeroMargin) {
    SnailDeveloperImpl developer = new SnailDeveloperImpl();
    Rect box = new Rect(0,0, d.width, d.height);
    Point where = new Point();
    where.h = d.width / 2;
    where.v = d.height / 2;
    developer.develop(g2, this, where, box);

  }

  void develop(Point where) {
  }

  public void flipHandedness() {
    handedness = -handedness;
  }

  public int getCoarsegraininess() {
    return coarsegraininess;
  }

  public double getdDisplacement() {
    return dDisplacement;
  }

  public double getdGradient() {
    return dGradient;
  }

  public int getGeneratingCurve() {
    return generatingCurve;
  }

  public int getHandedness() {
    return handedness;
  }

  public int getMutProb() {
    return mutProb;
  }

  public int getReach() {
    return reach;
  }

  public double getsShape() {
    return sShape;
  }

  public double getTranslationGradient() {
    return translationGradient;
  }

  public double gettTranslation() {
    return tTranslation;
  }

  public double getwOpening() {
    return wOpening;
  }
  @Override
  public Genome reproduce(Morph newMorph) {
    SnailGenome child = new SnailGenome(newMorph);
    child.wOpening = this.wOpening;
    child.dDisplacement = this.dDisplacement;
    child.sShape = this.sShape;
    child.tTranslation = this.tTranslation;
    child.coarsegraininess = this.coarsegraininess;
    child.reach = this.reach;
    child.generatingCurve = this.generatingCurve;
    child.translationGradient = this.translationGradient;
    child.dGradient = this.dGradient;
    child.handedness = this.handedness;
    newMorph.getMorphConfig().getMutagen().mutate(child);
    return child;

  }
  @Override
  public void setBasicType(int i) {
    // TODO Auto-generated method stub

  }
  public void setCoarsegraininess(int coarsegraininess) {
    this.coarsegraininess = coarsegraininess;
  }
  public void setdDisplacement(double dDisplacement) {
    this.dDisplacement = dDisplacement;
  }
  public void setdGradient(double dGradient) {
    this.dGradient = dGradient;
  }
  public void setGeneratingCurve(int generatingCurve) {
    this.generatingCurve = generatingCurve;
  }
  public void setHandedness(int handedness) {
    this.handedness = handedness;
  }
  public void setMutProb(int mutProb) {
    this.mutProb = mutProb;
  }
  public void setReach(int reach) {
    this.reach = reach;
  }

  public void setsShape(double sShape) {
    this.sShape = sShape;
  }

  public void setTranslationGradient(double translationGradient) {
    this.translationGradient = translationGradient;
  }

  public void settTranslation(double tTranslation) {
    this.tTranslation = tTranslation;
  }


  public void setwOpening(double wOpening) {
    this.wOpening = wOpening;
  }

}
