package net.richarddawkins.wm.morphs;

public interface Mutagen {

	void setMorphConfig(MorphConfig morphConfig);
	MorphConfig getMorphConfig();

	boolean mutate(Genome genome);

}