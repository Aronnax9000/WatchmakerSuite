package net.richarddawkins.watchmaker.morph.colour;

import java.awt.GridBagConstraints;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.gui.genebox.GeneBox;
import net.richarddawkins.watchmaker.gui.genebox.SimpleGeneBoxStrip;
import net.richarddawkins.watchmaker.morph.MorphConfig;

public class ColourGeneBoxStrip extends SimpleGeneBoxStrip {
	  
	  
	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;
	  private ColourGenome genome;

	  private void updateGeneBox(int geneBoxIndex) {
	  }

	  public void setGenome(Genome genome) {
		  this.genome = (ColourGenome) genome;
		  for(int j = 0; j < this.getComponentCount(); j++)
		  updateGeneBox(j);
	  }
	  
	  public ColourGeneBoxStrip(MorphConfig config, boolean engineeringMode) {
	    super(config, engineeringMode);
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
