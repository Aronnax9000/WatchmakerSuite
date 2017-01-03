package net.richarddawkins.wm.morphs.snail;

import java.awt.Dimension;
import java.awt.Graphics2D;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.SimpleGenome;
import net.richarddawkins.watchmaker.morph.common.Morph;
import net.richarddawkins.watchmaker.morph.common.geom.Point;
import net.richarddawkins.watchmaker.morph.common.geom.Rect;

public class SnailGenome extends SimpleGenome implements Cloneable {
  public static boolean sideView = false;

  double wOpening;

  public SnailGenome(Morph morph) {
    this.morph = morph;
  }

  public double getwOpening() {
    return wOpening;
  }

  public void setwOpening(double wOpening) {
    this.wOpening = wOpening;
  }

  public double getdDisplacement() {
    return dDisplacement;
  }

  public void setdDisplacement(double dDisplacement) {
    this.dDisplacement = dDisplacement;
  }

  public double getsShape() {
    return sShape;
  }

  public void setsShape(double sShape) {
    this.sShape = sShape;
  }

  public double gettTranslation() {
    return tTranslation;
  }

  public void settTranslation(double tTranslation) {
    this.tTranslation = tTranslation;
  }

  public double getTranslationGradient() {
    return translationGradient;
  }

  public void setTranslationGradient(double translationGradient) {
    this.translationGradient = translationGradient;
  }

  public double getdGradient() {
    return dGradient;
  }

  public void setdGradient(double dGradient) {
    this.dGradient = dGradient;
  }

  public int getCoarsegraininess() {
    return coarsegraininess;
  }

  public void setCoarsegraininess(int coarsegraininess) {
    this.coarsegraininess = coarsegraininess;
  }

  public int getReach() {
    return reach;
  }

  public void setReach(int reach) {
    this.reach = reach;
  }

  public int getGeneratingCurve() {
    return generatingCurve;
  }

  public void setGeneratingCurve(int generatingCurve) {
    this.generatingCurve = generatingCurve;
  }

  public int getHandedness() {
    return handedness;
  }

  public void setHandedness(int handedness) {
    this.handedness = handedness;
  }

  public int getMutProb() {
    return mutProb;
  }

  public void setMutProb(int mutProb) {
    this.mutProb = mutProb;
  }

  /**
   * DDisplacement is not allowed to be less than zero or greater than 1.
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

  public void flipHandedness() {
    handedness = -handedness;
  }

  double dDisplacement;
  double sShape;
  double tTranslation;
  double translationGradient;
  double dGradient;
  int coarsegraininess;
  int reach;
  int generatingCurve;
  int handedness;
  int mutProb;

  @Override
  public void setBasicType(int i) {
    // TODO Auto-generated method stub

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


  void develop(Point where) {
  }

}
