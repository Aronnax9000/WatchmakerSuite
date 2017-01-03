package net.richarddawkins.watchmaker.morphs;

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

  protected Mutagen mutagen;

  @Override
  public Mutagen getMutagen() {
    return mutagen;
  }

  @Override
  public void setMutagen(Mutagen mutagen) {
    this.mutagen = mutagen;
  }
}
