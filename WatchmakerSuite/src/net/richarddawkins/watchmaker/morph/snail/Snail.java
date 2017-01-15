package net.richarddawkins.watchmaker.morph.snail;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.SimpleMorph;

public class Snail extends SimpleMorph {

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
    pic = new SnailPic(this);

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
    child.getPedigree().parent = this;
    return child;
  }



}
