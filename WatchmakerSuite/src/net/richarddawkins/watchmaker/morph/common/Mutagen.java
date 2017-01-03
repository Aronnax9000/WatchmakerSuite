package net.richarddawkins.watchmaker.morph.common;

import net.richarddawkins.watchmaker.genome.Genome;

public interface Mutagen {

	boolean mutate(Genome genome);

}