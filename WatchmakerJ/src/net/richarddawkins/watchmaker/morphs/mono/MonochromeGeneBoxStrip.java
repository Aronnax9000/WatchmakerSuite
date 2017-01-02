package net.richarddawkins.watchmaker.morphs.mono;

import java.awt.GridBagConstraints;

import net.richarddawkins.watchmaker.morphs.GeneBox;
import net.richarddawkins.watchmaker.morphs.MorphConfig;
import net.richarddawkins.watchmaker.morphs.SimpleGeneBoxStrip;

public class MonochromeGeneBoxStrip extends SimpleGeneBoxStrip  {
  
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public MonochromeGeneBoxStrip(MorphConfig config) {
    super(config);
    int numberOfGeneBoxes = config.getGeneBoxCount();
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.weightx = 1;
    constraints.gridy = 0;
    for(int i = 0; i < numberOfGeneBoxes; i++) {
      GeneBox geneBox = new GeneBox(this, numberOfGeneBoxes);
      constraints.gridx = i;
      this.add(geneBox, constraints);
    }

  }
}
