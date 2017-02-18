package net.richarddawkins.watchmaker.morphs.concho.genome.mutation;

import static net.richarddawkins.watchmaker.genome.mutation.Random.randInt;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.mutation.AllowedMutations;
import net.richarddawkins.watchmaker.genome.mutation.SimpleMutagen;
import net.richarddawkins.watchmaker.morphs.concho.genome.SnailGenome;

public class SnailMutagen extends SimpleMutagen {

	public SnailMutagen(AllowedMutations allowedMutations) {
		super(allowedMutations);
	}

	public static double direction() {
		if (randInt(2) == 2) {
			return 1;
		} else {
			return -1;
		}
	}

	/**
	 * Calculate the scale of a 'reasonable' alteration to a double, concomitant with its size.
	 * Original Pascal source comment from Richard Dawkins: 
	 * 'we want to change by large amounts when low, small amounts when large'
	 * @param w the number whose scale is being measured.
	 * @param direction the direction of the change.
	 * @return the factor by which to multiply w in order to make a reasonable incremental change.
	 */
	public static double margarine(double w, double direction) {
		double wMutSize = 0.1f;
		double logged = Math.log(w);
		double logchanged = logged + wMutSize * direction;
		if (logchanged > 20.0f) {
			logchanged = 20.0f;
		}
		double m = Math.exp(logchanged);
		if (m < 1.0f) {
			m = 1.0f;
		}
		return m;
	}

	public static double direction9() {
		int r = randInt(5);
		switch (r) {
		case 5:
			return 2;
		case 4:
			return 1;
		case 3:
			return 0;
		case 2:
			return -1;
		case 1:
			return -2;
		default:
			return 0;
		}
	}

	public static final double DMUTSIZE = 0.01d;
	public static final double SMUTSIZE = 0.1d;
	public static final double TMUTSIZE = 0.1d;

	public boolean mutate(Genome genome) {
		SnailGenome target = (SnailGenome) genome;
		SnailAllowedMutations muts = (SnailAllowedMutations) allowedMutations;
		int mutProb = target.getMutProb().getValue();
		boolean success = false;
		
		if (randInt(100) < mutProb) {
			target.opening.setValue(margarine(target.opening.getValue(), direction()));
			success = true;
		}

		if (randInt(100) < mutProb) {
			target.displacement.addToGene(direction9() * DMUTSIZE);
			success = true;
		}

		if (randInt(100) < mutProb && muts.isSideView()) {
			// Don't let Translation gene drift when you can't see its
			// consequences
			target.translation.addToGene(direction9() * TMUTSIZE);
			success = true;
		}

		if (randInt(100) < 1) {
			target.handedness.flipHandedness();
			success = true;
		}
		return success;
	}

}
