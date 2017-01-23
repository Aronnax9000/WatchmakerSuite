package net.richarddawkins.watchmaker.morphs.concho.genome;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.genome.SimpleGenome;
import net.richarddawkins.watchmaker.morphs.concho.genome.type.ClassicSnail;

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
	public static boolean sideView = false;
	public final DoubleGene opening = new DoubleGene(this, "Opening");
	public final DoubleGene displacement = new Double0To1Gene(this, "Displacement");
	public final DoubleGene shape = new DoubleGene(this, "Shape");
	public final DoubleGene translation = new DoubleGene(this, "Translation");
	public final IntegerGene mutProb = new IntegerGene(this, "Mutation Probability");
	public final IntegerGene coarsegraininess = new IntegerGene(this, "Coarsegraininess");
	public final IntegerGene reach = new IntegerGene(this, "Reach");
	public final IntegerGene generatingCurve = new IntegerGene(this, "GeneratingCurve");
	public final HandednessGene handedness = new HandednessGene(this, "Handedness");
	public final DoubleGene translationGradient = new DoubleGene(this, "TranslationGradient");
	public final DoubleGene gradient = new DoubleGene(this, "Gradient");
	public SnailGenome() {

	}

	@Override
	public void setBasicType(int i) {
		ClassicSnail snail = ClassicSnail.values()[i];
		opening.setValue(snail.getOpening());
		displacement.setValue(snail.getDisplacement());
		shape.setValue(snail.getShape());
		translation.setValue(snail.getTranslation());
		coarsegraininess.setValue(snail.getCoarsegraininess());
		reach.setValue(snail.getReach());
		generatingCurve.setValue(snail.getGeneratingCurve());
		translationGradient.setValue(snail.getTranslationGradient());
//		gradient.setValue(snail.getGradient()); Dawkins never set dGradient in his standard types.
		handedness.setValue(snail.getHandedness());
		mutProb.setValue(snail.getMutProb());		
	}

	public Gene[] toGeneArray() {
		return new Gene[] { opening, displacement, shape, translation, mutProb, coarsegraininess, reach, generatingCurve,
				translationGradient, gradient, handedness };
	}

}
