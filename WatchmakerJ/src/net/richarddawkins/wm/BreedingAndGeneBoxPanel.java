package net.richarddawkins.watchmaker;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.morphs.GeneBoxStrip;
import net.richarddawkins.watchmaker.morphs.MorphConfig;
import net.richarddawkins.watchmaker.morphs.SimpleGeneBoxStrip;
/**
 * Two Classic Watchmaker programs, Monochrome Blind Watchmaker and
 * Blind Snailmaker, use GeneBoxes to display numerical gene boxes
 * at the top of the Breeding and Engineering windows.
 * 
 * <h2>Original sources</h2>
 * 
 * @author alan
 *
 */
public class BreedingAndGeneBoxPanel extends JPanel {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  GeneBoxStrip strip;
  BreedingPanel panel;
  protected MorphConfig config;
  
  public BreedingAndGeneBoxPanel(SimpleGeneBoxStrip strip, BreedingPanel panel) {
  }
  
  protected GeneBoxStrip geneBoxPanel;
  protected BreedingPanel breedingPanel;

  public BreedingPanel getBreedingPanel() {
    if (breedingPanel == null)
      breedingPanel = new BreedingPanel(
          config);
    return breedingPanel;
  }


  public BreedingAndGeneBoxPanel(MorphConfig config) {
    this.config = config;
    this.setLayout(new BorderLayout());
    
    
    this.add(getBreedingPanel(), BorderLayout.CENTER);
    if(config.getGeneBoxCount() != 0) {
        this.add(getGeneBoxPanel(), BorderLayout.NORTH);
    }
  }


  protected GeneBoxStrip getGeneBoxPanel() {
    if (geneBoxPanel == null)
      geneBoxPanel = new SimpleGeneBoxStrip(config);
    return geneBoxPanel;
  }
  
  
}
