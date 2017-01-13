package net.richarddawkins.watchmaker.morph.biomorph.genome;

import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.morph.biomorph.genome.type.SwellType;

public class IntegerGradientGene extends IntegerGene implements GradientGene {
	public IntegerGradientGene(String name) {
		super(name);
	}
	
	protected SwellType gradient;

	public SwellType getGradient() {
		return gradient;
	}

	public void setGradient(SwellType gradient) {
		SwellType old = this.gradient;
		this.gradient = gradient;
		pcs.firePropertyChange("gradient", old, value);
	}

	public void copy(IntegerGradientGene destinationGene) {
		super.copy((IntegerGene) destinationGene);
		destinationGene.setGradient(gradient);
	} 
}
