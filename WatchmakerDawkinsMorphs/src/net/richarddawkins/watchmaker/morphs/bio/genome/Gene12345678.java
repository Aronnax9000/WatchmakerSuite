package net.richarddawkins.watchmaker.morphs.bio.genome;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GooseDirection;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SwellType;

public class Gene12345678 extends IntegerGradientGene {
	public Gene12345678(Genome genome, String name) {
		super(genome, name);
		
	}
	@Override
	public void goose(GooseDirection direction) {
		super.goose(direction);
		if (direction.equals(GooseDirection.upArrow)) {
			setGradient(SwellType.Swell);
		} else if (direction.equals(GooseDirection.equalsSign)) {
			setGradient(SwellType.Same);
		} else if (direction.equals(GooseDirection.downArrow)) {
			setGradient(SwellType.Shrink);
		}
	}
	@Override
	public int getGooseSize() {
		BiomorphGenome biomorphGenome = (BiomorphGenome) genome;
		return biomorphGenome.getMutSizeGene().getValue();
	}
	

	
}
