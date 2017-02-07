package net.richarddawkins.watchmaker.morphs.concho.genome;

import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genome.GeneManipulationEvent;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GooseDirection;
import net.richarddawkins.watchmaker.morphs.concho.genome.mutation.SnailMutagen;

public class OpeningGene extends DoubleGene {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morphs.concho.genome.OpeningGene");

	public OpeningGene(Genome genome) {
		super(genome, "Opening");
	}

	@Override
	public void geneManipulated(GeneManipulationEvent gbme) {
		GooseDirection direction = gbme.getGooseDirection();
		double newValue = 0;
		if (direction.equals(GooseDirection.leftArrow)) {
			newValue = SnailMutagen.margarine(value, -1);
		} else if (direction.equals(GooseDirection.rightArrow)) {
			newValue = SnailMutagen.margarine(value, 1);
		}
		logger.info("New value for opening: " + newValue);
		setValue(newValue);
	}

}
