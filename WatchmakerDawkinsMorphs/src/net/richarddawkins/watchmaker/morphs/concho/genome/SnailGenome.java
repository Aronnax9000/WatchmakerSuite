package net.richarddawkins.watchmaker.morphs.concho.genome;

import java.nio.ByteBuffer;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.genome.SimpleGenome;
import net.richarddawkins.watchmaker.morphs.concho.genome.type.HandednessType;

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

	// public final DoubleGene displacement = new Double0To1Gene(this,
	// "Displacement");
	public final DisplacementGene displacement = new DisplacementGene(this);

	public final IntegerGene generatingCurve = new IntegerGene(this, "GeneratingCurve");

	public final DoubleGene gradient = new DoubleGene(this, "Gradient");

	public final HandednessGene handedness = new HandednessGene(this, "Handedness");

	public final IntegerGene mutProb = new IntegerGene(this, "Mutation Probability");

	public final OpeningGene opening = new OpeningGene(this);

	public final IntegerGene reach = new IntegerGene(this, "Reach");

	public final ShapeGene shape = new ShapeGene(this);

	public final TranslationGene translation = new TranslationGene(this);

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
		return new Gene[] { opening, displacement, shape, translation, mutProb, coarsegraininess, reach,
				generatingCurve, translationGradient, gradient, handedness };
	}

	/**
	 * WOpening: real;
    DDisplacement: real;
    SShape: real;
    TTranslation: real;
    Coarsegraininess: integer;
    Reach: integer; 
    GeneratingCurve: integer;
    TranslationGradient: real;
    DGradient: real;
    Handedness: -1..1;
	 */
	public void readFromByteBuffer(ByteBuffer byteBuffer) {
		opening.setValue(byteBuffer.getFloat());
		displacement.setValue(byteBuffer.getFloat());
		shape.setValue(byteBuffer.getFloat());
		translation.setValue(byteBuffer.getFloat());
		coarsegraininess.setValue(byteBuffer.getShort());
		reach.setValue(byteBuffer.getShort());
		generatingCurve.setValue(byteBuffer.getShort());
		translationGradient.setValue(byteBuffer.getFloat());
		gradient.setValue(byteBuffer.getFloat());
		byteBuffer.getShort();
		handedness.setValue(HandednessType.values()[byteBuffer.get()]);
		byteBuffer.get();
		
	}
	
}
