package net.richarddawkins.watchmaker.morphs;

public interface Mutagen {

	void setPerson(Genome genome);

	Genome getGenome();

	boolean mutate();

}