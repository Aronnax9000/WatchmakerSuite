package net.richarddawkins.watchmaker.genome.mutation;

import net.richarddawkins.watchmaker.genome.Genome;

public interface Mutagen {
	boolean mutate(Genome genome);
	AllowedMutations getAllowedMutations();

}