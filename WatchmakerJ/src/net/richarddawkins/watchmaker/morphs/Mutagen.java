package net.richarddawkins.watchmaker.morphs;

public interface Mutagen {

	void setPerson(Person genome);

	Person getGenome();

	boolean mutate();

}