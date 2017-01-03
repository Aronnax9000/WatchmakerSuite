package net.richarddawkins.wm.morphs;

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
