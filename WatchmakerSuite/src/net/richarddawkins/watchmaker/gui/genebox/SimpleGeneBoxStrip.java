package net.richarddawkins.watchmaker.gui.genebox;

import java.awt.GridLayout;

import net.richarddawkins.watchmaker.morph.MorphConfig;



public abstract class SimpleGeneBoxStrip extends GeneBoxStrip {
  /**
   * 
   */
  
  boolean engineeringMode = false;
  boolean hasGradient = false;
  
  private static final long serialVersionUID = 1L;
  protected MorphConfig config;
  
  public void setMorphConfig(MorphConfig config) {
	  this.config = config;
  }
  public SimpleGeneBoxStrip(MorphConfig config) {
    this.setLayout(new GridLayout(1, config.getGeneBoxCount()));
    this.config = config;
  }

  
}
