package net.richarddawkins.watchmaker.embryo;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.geom.Pic;

public interface Embryology {
	/**
	 * Given a "genotype" (Genome), produce a "phenotype" (Pic)
	 * @param genome
	 * @param pic
	 */
	public void develop(Genome genome, Pic pic);
}
