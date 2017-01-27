package net.richarddawkins.watchmaker.genome.mutation;

import net.richarddawkins.watchmaker.genome.Genome;

public interface Mutagen {
	boolean mutate(Genome genome);
	AllowedMutations getAllowedMutations();
	/** Randomizes the genes in the genome. 
	 * <em>Natura non facit saltum.</em>
	 */
	void deliverSaltation(Genome genome);
		
}