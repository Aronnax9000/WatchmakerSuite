package net.richarddawkins.watchmaker.morphs;

public interface Mutagen {

	void setMorphConfig(MorphConfig morphConfig);
	MorphConfig getMorphConfig();

	boolean mutate(Genome genome);

}