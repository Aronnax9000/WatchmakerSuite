package net.richarddawkins.watchmaker.morph;

import net.richarddawkins.watchmaker.genome.Genome;

public interface Mutagen {
	void setMorphConfig(MorphConfig morphConfig);
	MorphConfig getMorphConfig();

	boolean mutate(Genome genome);

}