package net.richarddawkins.watchmaker.morphs.bio.genome;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SwellType;

public class Gene12345678 extends IntegerGradientGene {
	public Gene12345678(Genome genome, String name) {
		super(genome, name);
		
	}

	@Override
	public int getGooseSize() {
		BiomorphGenome biomorphGenome = (BiomorphGenome) genome;
		return biomorphGenome.getMutSizeGene().getValue();
	}
	

	@Override
	/** Note that the nineTimesTrickleGene rule is from Colour. TODO check to 
	 * see if it's in Monochrome, too.
	 */
	public void setGradient(SwellType newValue) {
		int nineTimesTrickleGene = ((BiomorphGenome) genome).getTrickleGene().getValue();
		if(newValue == SwellType.Same || (value >= -nineTimesTrickleGene && value <= nineTimesTrickleGene)) {
			super.setGradient(newValue);
		} 
	}
	
}
