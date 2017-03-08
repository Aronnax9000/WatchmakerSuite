package net.richarddawkins.watchmaker.morphs.colour.genome.swing;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.genebox.GeneBox;
import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.morphs.colour.genome.ColorGene;
import net.richarddawkins.watchmaker.morphs.colour.genome.LimbFillGene;
import net.richarddawkins.watchmaker.morphs.colour.genome.LimbShapeGene;
import net.richarddawkins.watchmaker.morphs.mono.genebox.swing.SwingMonochromeGeneBoxStrip;

public class SwingColorGeneBoxStrip extends SwingMonochromeGeneBoxStrip {

	public SwingColorGeneBoxStrip(AppData appData) {
        super(appData);
        
    }

    private static final long serialVersionUID = 1L;

	public GeneBox getGeneBoxForGene(Gene gene) {
		if(gene instanceof ColorGene) 
			return new SwingColorGeneBox(appData);
		else if(gene instanceof LimbShapeGene) 
			return new SwingLimbShapeGeneBox(appData);
		else if(gene instanceof LimbFillGene)
			return new SwingLimbFillGeneBox(appData);
		else 
			return super.getGeneBoxForGene(gene, appData);
		
	}
	


}
