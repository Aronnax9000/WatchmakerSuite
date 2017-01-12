package net.richarddawkins.watchmaker.genome;

public interface Gene {
	Genome getGenome();
	void setGenome(Genome genome);
	String getName();
	void setName(String name);
}
