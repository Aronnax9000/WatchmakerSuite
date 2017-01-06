package net.richarddawkins.watchmaker.gui.genebox;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.MorphConfig;

public abstract class GeneBoxStrip extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public abstract void setGenome(Genome genome);
  public abstract Genome getGenome();
  public abstract void setMorphConfig(MorphConfig morphConfig);
  protected boolean engineeringMode;
}
