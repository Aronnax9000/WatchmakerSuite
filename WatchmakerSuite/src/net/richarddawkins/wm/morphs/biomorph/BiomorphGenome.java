package net.richarddawkins.wm.morphs.biomorph;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.SimpleGenome;
import net.richarddawkins.watchmaker.morph.common.Morph;
import net.richarddawkins.watchmaker.morph.util.Globals;

public abstract class BiomorphGenome extends SimpleGenome {

  protected boolean oddOne;
  protected int order;

  public void setBasicType(int basicType) {
    switch (basicType) {
    case 1:
      basicTree();
      break;
    case 2:
      chess();
      break;
    case 3:
      insect();
      break;
    default:
      break;
    }
  }

  abstract public void basicTree();
  abstract public void chess();
  abstract public void insect();
  public int[] getGene() {
    return gene;
  }

  public void setGene(int[] gene) {
    this.gene = gene;
  }

  public SwellType[] getdGene() {
    return dGene;
  }

  public void setdGene(SwellType[] dGene) {
    this.dGene = dGene;
  }

  public int getSegNoGene() {
    return segNoGene;
  }

  public void setSegNoGene(int segNoGene) {
    this.segNoGene = segNoGene;
  }

  public int getSegDistGene() {
    return segDistGene;
  }

  public void setSegDistGene(int segDistGene) {
    this.segDistGene = segDistGene;
  }

  public CompletenessType getCompletenessGene() {
    return completenessGene;
  }

  public void setCompletenessGene(CompletenessType completenessGene) {
    this.completenessGene = completenessGene;
  }

  public SpokesType getSpokesGene() {
    return spokesGene;
  }

  public void setSpokesGene(SpokesType spokesGene) {
    this.spokesGene = spokesGene;
  }

  public int getTrickleGene() {
    return trickleGene;
  }

  public void setTrickleGene(int trickleGene) {
    this.trickleGene = trickleGene;
  }

  public int getMutSizeGene() {
    return mutSizeGene;
  }

  public void setMutSizeGene(int mutSizeGene) {
    this.mutSizeGene = mutSizeGene;
  }

  public int getMutProbGene() {
    return mutProbGene;
  }

  public void setMutProbGene(int mutProbGene) {
    this.mutProbGene = mutProbGene;
  }

  public void setMorph(Morph morph) {
    this.morph = morph;
  }

  /**
   * Add a quantity to the mutProbGene. If the the result is less than one, it is set to one. If the
   * result is greater than 100, it is set to 100.
   */
  public void addToMutProbGene(int summand) {
    mutProbGene += summand;
    if (mutProbGene < 1)
      mutProbGene = 1;
    if (mutProbGene > 100)
      mutProbGene = 100;
  }

  protected int gene9Max = 8;

  public int getGene9Max() {
    return gene9Max;
  }

  public void setGene9Max(int max) {
    gene9Max = max;
  }

  /**
   * Gene9 is not permitted to fall below 1, or rise above 8 (the default, for Colour.) Monochrome
   * sets 11 as the limit in its constructor.
   */
  public void addToGene(int index, int summand) {
    gene[index] += summand;
    if (index == 8) {
      if (gene[8] < 1)
        gene[8] = 1;
      else if (gene[8] > gene9Max)
        gene[8] = gene9Max;
    }
  }

  public void setGene(int index, int value) {
    gene[index] = value;
  }

  public int getGene(int index) {
    return gene[index];
  }

  public void decrementSegNoGene() {
    segNoGene--;
  }

  public void decrementGene(int i) {
    gene[i]--;
  }

  public SwellType getDGene(int i) {
    return dGene[i];
  }

  public void setDGene(int i, SwellType swellType) {
    dGene[i] = swellType;
  }

  /** mutSizeGene is not allowed to fall below 1. */
  public void addToMutSizeGene(int summand) {
    mutSizeGene += summand;
    if (mutSizeGene < 1)
      mutSizeGene = 1;
  }

  /**
   * trickleGene is not allowed to fall below 1.
   */
  public void addToTrickleGene(int summand) {
    trickleGene += summand;
    if (trickleGene < 1)
      trickleGene = 1;

  }

  /** Add to segNoGene provided that the product segNoGene * gene[Gene9]^2 < Globals.worryMax */
  public void addToSegNoGene(int summand) {
    segNoGene += summand;
    if (summand > 0) {
      int sizeWorry = segNoGene * 1 << gene[8];
      if (sizeWorry > Globals.worryMax)
        segNoGene--;
      if (segNoGene < 1)
        segNoGene = 1;

    }

  }

  public void addToSegDistGene(int summand) {
    segDistGene += summand;
  }

  protected int[] gene = new int[9];
  protected SwellType[] dGene = new SwellType[10];
  protected int segNoGene;
  protected int segDistGene;
  protected CompletenessType completenessGene;
  protected SpokesType spokesGene;
  protected int trickleGene;
  protected int mutSizeGene;
  protected int mutProbGene;

  public void copy(Genome person) {
    BiomorphGenome child = (BiomorphGenome) person;
    child.setGene(gene.clone());
    child.setdGene(dGene.clone());
    child.setSegNoGene(segNoGene);
    child.setSegDistGene(segDistGene);
    child.setCompletenessGene(completenessGene);
    child.setSpokesGene(spokesGene);
    child.setTrickleGene(trickleGene);
    child.setMutSizeGene(mutSizeGene);
    child.setMutProbGene(mutProbGene);
  }

}
