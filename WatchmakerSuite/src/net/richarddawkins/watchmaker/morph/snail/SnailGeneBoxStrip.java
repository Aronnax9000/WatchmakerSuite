package net.richarddawkins.watchmaker.morph.snail;

import java.awt.GridBagConstraints;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.gui.DoubleGeneBox;
import net.richarddawkins.watchmaker.genome.gui.SimpleGeneBoxStrip;
import net.richarddawkins.watchmaker.morph.MorphConfig;

public class SnailGeneBoxStrip extends SimpleGeneBoxStrip {
	  
	  
	  private static final long serialVersionUID = 1L;


	  private void updateGeneBox(int geneBoxIndex) {
	  }

	  public void setGenome(Genome genome) {
		  this.genome = (SnailGenome) genome;
		  for(int j = 0; j < this.getComponentCount(); j++)
		  updateGeneBox(j);
	  }
	  
	  public SnailGeneBoxStrip(MorphConfig config, boolean engineeringMode) {
	    super(config, engineeringMode);
	    int numberOfGeneBoxes = config.getGeneBoxCount();
	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.HORIZONTAL;
	    constraints.weightx = 1;
	    constraints.gridy = 0;
	    for(int i = 0; i < numberOfGeneBoxes; i++) {
	      DoubleGeneBox geneBox = new DoubleGeneBox(this, engineeringMode);
	      constraints.gridx = i;
	      this.add(geneBox, constraints);
	    }

	  }

		
		@Override
		public Genome getGenome() {
			return genome;
		}

}
