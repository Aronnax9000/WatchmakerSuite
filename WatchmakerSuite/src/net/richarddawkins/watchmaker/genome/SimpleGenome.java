package net.richarddawkins.watchmaker.genome;

import net.richarddawkins.watchmaker.morph.common.Morph;

public abstract class SimpleGenome implements Genome {

  protected Morph morph;

  @Override
  public Morph getMorph() {
    return morph;
  }

  @Override
  public void setMorph(Morph morph) {
    this.morph = morph;
  }

}
