package net.richarddawkins.watchmaker.morphs.bio.genome;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SwellType;

public class SegDistGene extends Gene12345678 {

	public SegDistGene(Genome genome, String name) {
		super(genome, name);
	}

	@Override
    public int getGooseSize() {
		BiomorphGenome biomorphGenome = (BiomorphGenome) genome;
		return biomorphGenome.trickleGene.getValue();
	}
	@Override
	/** The rule is from Colour TODO check to see if it's in Monochrome, too.
	 */
	public void setGradient(SwellType newValue) {
		if(newValue == SwellType.Same || (Math.abs(((BiomorphGenome) genome).getSegNoGene().getValue() * value) <= 100)) {
			super.setGradient(newValue);
		} 
	}
}
