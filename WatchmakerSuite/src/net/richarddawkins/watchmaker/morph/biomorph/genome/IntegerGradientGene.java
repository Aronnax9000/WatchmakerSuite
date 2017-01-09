package net.richarddawkins.watchmaker.morph.biomorph.genome;

import net.richarddawkins.watchmaker.genome.IntegerGene;

public class IntegerGradientGene extends IntegerGene {
    protected SwellType gradient;

	public SwellType getGradient() {
		return gradient;
	}

	public void setGradient(SwellType gradient) {
		this.gradient = gradient;
	} 
}
