package net.richarddawkins.watchmaker.morph.biomorph.genome;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.morph.biomorph.genome.type.SwellType;

public class IntegerGradientGene extends IntegerGene implements GradientGene {
	public IntegerGradientGene(Genome genome, String name) {
		super(genome, name);
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
	@Override
	public void copy(Gene destinationGene) {
		super.copy((IntegerGene) destinationGene);
		((GradientGene)destinationGene).setGradient(gradient);
	} 
}
