package net.richarddawkins.watchmaker.morphs;

import java.awt.GridLayout;



public class SimpleGeneBoxStrip extends GeneBoxStrip {
  /**
   * 
   */
  
  boolean engineeringMode = false;
  boolean hasGradient = false;
  
  private static final long serialVersionUID = 1L;
  protected MorphConfig config;
  
  public SimpleGeneBoxStrip(MorphConfig config) {
    this.setLayout(new GridLayout(1, config.getGeneBoxCount()));
    this.config = config;
  }

  
}
