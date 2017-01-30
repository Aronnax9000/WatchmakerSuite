package net.richarddawkins.watchmaker.genome;

import java.beans.PropertyChangeListener;

public interface Genome extends PropertyChangeListener {
	void copy(Genome targetGenome);


	Gene[] toGeneArray();
	Gene getGene(int geneBoxIndex);
	void addGenomeChangeListener(GenomeChangeListener listener);
	void removeGenomeChangeListener(GenomeChangeListener listener);

}
