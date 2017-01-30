package net.richarddawkins.watchmaker.genome;

import net.richarddawkins.watchmaker.genome.mutation.AllowedMutations;

public abstract class GenomeFactory {
	public static final int RANDOM = 0;
	public static final int BASIC_TREE = 1;
	public static final int CHESS = 2;
	public static final int INSECT = 3;
	protected AllowedMutations muts;

	public GenomeFactory(AllowedMutations muts) {
		this.muts = muts;
	}
	
	public Genome getBasicType(int basicType) {
		return deliverSaltation();
	}
	
	/** Randomizes the genes in the genome. 
	 * <em>Natura non facit saltum.</em>
	 * @return a new randomized Genome.
	 */

	public abstract Genome deliverSaltation();
}
