package net.richarddawkins.watchmaker.embryo;

import java.beans.PropertyChangeListener;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.phenotype.Phenotype;

public interface Embryology extends PropertyChangeListener {
	/**
	 * Given a "genotype" (Genome), produce a "phenotype" (Pic)
	 * @param genome
	 * @param pic
	 */
	public void develop(Genome genome, Phenotype phenotype);
}
