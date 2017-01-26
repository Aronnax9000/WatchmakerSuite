package net.richarddawkins.watchmaker.genebox;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;

public interface GeneBoxStrip extends MorphViewPanel {

	public abstract void setGenome(Genome genome);
	
	public abstract GeneBox getGeneBoxForGene(Gene gene);

	public abstract void setEngineeringMode(boolean engineeringMode);

}
