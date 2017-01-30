package net.richarddawkins.watchmaker.morphs.concho.genome;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.genome.SimpleGenome;

/**
 * <pre>
 * person = record WOpening, DDisplacement, SShape, TTranslation: real;
 * Coarsegraininess, Reach, GeneratingCurve: integer; TranslationGradient,
 * DGradient: real; Handedness: -1..1; end;
 * </pre>
 * 
 * 
 * 
 * @author sven
 *
 */
public class SnailGenome extends SimpleGenome {


	public final IntegerGene coarsegraininess = new IntegerGene(this, "Coarsegraininess");

	public final DoubleGene displacement = new Double0To1Gene(this, "Displacement");

	public final IntegerGene generatingCurve = new IntegerGene(this, "GeneratingCurve");

	public final DoubleGene gradient = new DoubleGene(this, "Gradient");

	public final HandednessGene handedness = new HandednessGene(this, "Handedness");

	public final IntegerGene mutProb = new IntegerGene(this, "Mutation Probability");

	public final DoubleGene opening = new DoubleGene(this, "Opening");

	public final IntegerGene reach = new IntegerGene(this, "Reach");

	public final DoubleGene shape = new DoubleGene(this, "Shape");

	public final DoubleGene translation = new DoubleGene(this, "Translation");

	public final DoubleGene translationGradient = new DoubleGene(this, "TranslationGradient");

	public SnailGenome() {

	}
	public IntegerGene getCoarsegraininess() {
		return coarsegraininess;
	}
	public DoubleGene getDisplacement() {
		return displacement;
	}
	public IntegerGene getGeneratingCurve() {
		return generatingCurve;
	}
	public DoubleGene getGradient() {
		return gradient;
	}
	public HandednessGene getHandedness() {
		return handedness;
	}
	public IntegerGene getMutProb() {
		return mutProb;
	}
	public DoubleGene getOpening() {
		return opening;
	}
	public IntegerGene getReach() {
		return reach;
	}
	public DoubleGene getShape() {
		return shape;
	}
	public DoubleGene getTranslation() {
		return translation;
	}
	public DoubleGene getTranslationGradient() {
		return translationGradient;
	}



	public Gene[] toGeneArray() {
		return new Gene[] { opening, displacement, shape, translation, mutProb, coarsegraininess, reach, generatingCurve,
				translationGradient, gradient, handedness };
	}

}
