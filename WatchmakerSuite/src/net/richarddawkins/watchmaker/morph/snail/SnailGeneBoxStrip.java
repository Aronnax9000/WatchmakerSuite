package net.richarddawkins.watchmaker.morph.snail;

import java.awt.GridBagConstraints;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.gui.genebox.GeneBox;
import net.richarddawkins.watchmaker.gui.genebox.SimpleGeneBoxStrip;
import net.richarddawkins.watchmaker.morph.MorphConfig;

public class SnailGeneBoxStrip extends SimpleGeneBoxStrip {
	  
	  
	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;
	  protected SnailGenome genome;

	  private void updateGeneBox(int geneBoxIndex) {
	  }

	  public void setGenome(Genome genome) {
		  this.genome = (SnailGenome) genome;
		  for(int j = 0; j < this.getComponentCount(); j++)
		  updateGeneBox(j);
	  }
	  
	  public SnailGeneBoxStrip(MorphConfig config) {
	    super(config);
	    int numberOfGeneBoxes = config.getGeneBoxCount();
	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.HORIZONTAL;
	    constraints.weightx = 1;
	    constraints.gridy = 0;
	    for(int i = 0; i < numberOfGeneBoxes; i++) {
	      GeneBox geneBox = new GeneBox(this, i, i < 9);
	      constraints.gridx = i;
	      this.add(geneBox, constraints);
	    }

	  }

		
		@Override
		public Genome getGenome() {
			return genome;
		}

}
