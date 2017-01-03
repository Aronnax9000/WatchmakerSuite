package net.richarddawkins.watchmaker.morphs.snail;

import net.richarddawkins.watchmaker.morphs.Morph;
import net.richarddawkins.watchmaker.morphs.MorphConfig;
import net.richarddawkins.watchmaker.morphs.SimpleMorph;

public class Snail extends SimpleMorph {

  public Snail() {
    setGenome(new SnailGenome(this));
    pic = new SnailPic();

  }

  public Snail(MorphConfig config) {
    this();
    this.setMorphConfig(config);
  }

  public Snail(MorphConfig config, int basicType) {
    this(config);
    genome.setBasicType(basicType);
  }

  @Override
  public Morph reproduce() {
    Snail child = new Snail(config);
    child.genome = genome.reproduce(child);
    child.setParent(this);
    return child;
  }

}
