package net.richarddawkins.watchmaker.genome;

public class SimpleGene implements Gene {

	protected Genome genome;
	
	@Override
	public Genome getGenome() {
		return genome;
	}

	@Override
	public void setGenome(Genome genome) {
		this.genome = genome;
	}

}
