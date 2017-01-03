package net.richarddawkins.wm.morphs.snail;

import net.richarddawkins.wm.morphs.Genome;
import net.richarddawkins.wm.morphs.Morph;
import net.richarddawkins.wm.morphs.MorphConfig;
import net.richarddawkins.wm.morphs.SimpleMorph;

public class Snail extends SimpleMorph {

  protected SnailGenome genome;
  
  protected SnailConfig config;
  
  @Override
  public MorphConfig getMorphConfig() { return config; }
  @Override
  public void setMorphConfig(MorphConfig config) {
    this.config = (SnailConfig) config;
  }
  @Override
  public Genome getGenome() {
    return genome;
  }

  @Override
  public void setGenome(Genome genome) {
    this.genome = (SnailGenome) genome;
  }

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
    child.genome = (SnailGenome) genome.reproduce(child);
    child.setParent(this);
    return child;
  }

}
