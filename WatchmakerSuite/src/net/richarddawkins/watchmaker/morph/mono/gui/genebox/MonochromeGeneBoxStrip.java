package net.richarddawkins.watchmaker.morph.mono.gui.genebox;



import java.awt.GridBagConstraints;

import net.richarddawkins.watchmaker.gui.genebox.GeneBox;
import net.richarddawkins.watchmaker.gui.genebox.SimpleGeneBoxStrip;
import net.richarddawkins.watchmaker.morph.common.Genome;
import net.richarddawkins.watchmaker.morph.common.MorphConfig;
import net.richarddawkins.watchmaker.morph.mono.MonochromeGenome;

public class MonochromeGeneBoxStrip extends SimpleGeneBoxStrip  {
  
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
private MonochromeGenome genome;

  private void updateGeneBox(int geneBoxIndex) {
	  GeneBox geneBox = (GeneBox) this.getComponent(geneBoxIndex);
	  switch(geneBoxIndex) {
	  case 0: // 1 in Pascal - ABC
	  case 1: // 2 in Pascal - ABC
	  case 2: // 3 in Pascal - ABC
	  case 3: // 4 in Pascal - ABC
	  case 4: // 5 in Pascal - ABC
	  case 5: // 6 in Pascal - ABC
	  case 6: // 7 in Pascal - ABC
	  case 7: // 8 in Pascal - ABC
	  case 8: // 9 in Pascal - ABC
		geneBox.setValueLabelValue(genome.getGene(geneBoxIndex));
		
		break;
	  case 9: // 10 in Pascal - ABC
		geneBox.setValueLabelValue(genome.getSegNoGene());
		break;
	  case 10: // 11 in Pascal - ABC
		geneBox.setValueLabelValue(genome.getSegDistGene());
		
		break;
	  case 11: // 12 in Pascal - ABC
		  geneBox.setCompleteness(genome.getCompletenessGene());
		  break;
	  case 12: // 13 in Pascal - ABC
		  geneBox.setSpokes(genome.getSpokesGene());
		  break;
		case 13: // 14 in Pascal - ABC
			geneBox.setValueLabelValue(genome.getTrickleGene());
			break;
		case 14: // 15 in Pascal - ABC
			geneBox.setValueLabelValue(genome.getMutSizeGene());
			break;
		case 15: // 16 in Pascal - ABC
			geneBox.setValueLabelValue(genome.getMutProbGene());
			break;
	  }

	  }


  public void setGenome(Genome genome) {
	  this.genome = (MonochromeGenome) genome;
	  for(int j = 0; j < this.getComponentCount(); j++)
	  updateGeneBox(j);
  }
  
  public MonochromeGeneBoxStrip(MorphConfig config) {
    super(config);
    int numberOfGeneBoxes = config.getGeneBoxCount();
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.weightx = 1;
    constraints.gridy = 0;
    for(int i = 0; i < numberOfGeneBoxes; i++) {
      GeneBox geneBox = new GeneBox(this, numberOfGeneBoxes, i);
      if(i < 9) geneBox.setHasSwell(true);
      constraints.gridx = i;
      this.add(geneBox, constraints);
    }

  }


@Override
public Genome getGenome() {
	// TODO Auto-generated method stub
	return null;
}
}
