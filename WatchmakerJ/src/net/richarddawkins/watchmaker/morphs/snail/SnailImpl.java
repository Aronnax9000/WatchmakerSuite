package net.richarddawkins.watchmaker.morphs.snail;

import net.richarddawkins.watchmaker.morphs.Morph;
import net.richarddawkins.watchmaker.morphs.MorphConfig;
import net.richarddawkins.watchmaker.morphs.SimpleMorphImpl;

public class SnailImpl extends SimpleMorphImpl implements Snail {

  public SnailImpl() {
    setGenome(new SnailPersonImpl(this));
    pic = new SnailPic();

  }

  public SnailImpl(MorphConfig config) {
    this();
    this.setMorphConfig(config);
  }

  public SnailImpl(MorphConfig config, int basicType) {
    this(config);
    genome.setBasicType(basicType);
  }

  @Override
  public Morph reproduce() {
    SnailImpl child = new SnailImpl(config);
    child.genome = genome.reproduce(child);
    child.setParent(this);
    return child;
  }

}
