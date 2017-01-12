package net.richarddawkins.watchmaker.morph.biomorph.genome;

import net.richarddawkins.watchmaker.genome.IntegerGene;

public class IntegerGradientGene extends IntegerGene {
	public IntegerGradientGene(String name) {
		super(name);
	}
	
	protected SwellType gradient;

	public SwellType getGradient() {
		return gradient;
	}

	public void setGradient(SwellType gradient) {
		this.gradient = gradient;
	}

	public void copy(IntegerGradientGene destinationGene) {
		super.copy((IntegerGene) destinationGene);
		destinationGene.setGradient(gradient);
	} 
}
