package net.richarddawkins.watchmaker.morph.biomorph.genome;

public class SegDistGene extends Gene12345678 {

	public SegDistGene(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
    public int getGooseSize() {
		BiomorphGenome biomorphGenome = (BiomorphGenome) genome;
		return biomorphGenome.trickleGene.getValue();
	}

}
