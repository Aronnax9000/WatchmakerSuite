package net.richarddawkins.watchmaker.genome;

public class SimpleGene implements Gene {
	public SimpleGene(String name) {
		this.name = name;
	}
	
	protected Genome genome;

	protected String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Genome getGenome() {
		return genome;
	}

	@Override
	public void setGenome(Genome genome) {
		this.genome = genome;
	}

}
