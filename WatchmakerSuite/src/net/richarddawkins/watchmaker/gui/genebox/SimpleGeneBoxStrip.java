package net.richarddawkins.watchmaker.gui.genebox;

import java.awt.GridLayout;

import net.richarddawkins.watchmaker.morph.MorphConfig;



public abstract class SimpleGeneBoxStrip extends GeneBoxStrip {
  /**
   * 
   */
  
  private static final long serialVersionUID = 1L;
  protected MorphConfig config;
  
  public void setMorphConfig(MorphConfig config) {
	  this.config = config;
  }
  public SimpleGeneBoxStrip(MorphConfig config, boolean engineeringMode) {
    this.setLayout(new GridLayout(1, config.getGeneBoxCount()));
    this.config = config;
    this.engineeringMode = engineeringMode;
  }

  
}
