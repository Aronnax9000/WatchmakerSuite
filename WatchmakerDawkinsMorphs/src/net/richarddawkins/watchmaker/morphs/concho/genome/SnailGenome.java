package net.richarddawkins.watchmaker.morphs.concho.genome;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.genome.SimpleGenome;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morph.Morph;

/**
 * person = record WOpening, DDisplacement, SShape, TTranslation: real;
 * Coarsegraininess, Reach, GeneratingCurve: integer; TranslationGradient,
 * DGradient: real; Handedness: -1..1; end;
 * 
 * @author sven
 *
 */
public class SnailGenome extends SimpleGenome implements Cloneable {
	public static boolean sideView = false;

	public final IntegerGene coarsegraininess = new IntegerGene(this, "Coarsegraininess");

	public final DoubleGene displacement = new Double0To1Gene(this, "Displacement");
	public final IntegerGene generatingCurve = new IntegerGene(this, "GeneratingCurve");
	public final DoubleGene gradient = new DoubleGene(this, "Gradient");
	public final HandednessGene handedness = new HandednessGene(this, "Handedness");
	public final DoubleGene opening = new DoubleGene(this, "Opening");
	public final IntegerGene reach = new IntegerGene(this, "Reach");
	public final DoubleGene shape = new DoubleGene(this, "Shape");
	public final DoubleGene translation = new DoubleGene(this, "Translation");
	public final DoubleGene translationGradient = new DoubleGene(this, "TranslationGradient");
	public SnailGenome() {

	}

	@Override
	public void develop() {
		// SnailDeveloperImpl developer = new SnailDeveloperImpl();
//		Rect box = new Rect(0, 0, 0, 0);
//		Point where = new Point(0, 0);
//		// BufferedImage bufferedImage = new
		// BufferedImage(BufferedImage.TYPE_4BYTE_ABGR, 1,1);
		// developer.develop(bufferedImage.createGraphics(), this, where, box);

	}

	void develop(Point where) {
	}

	@Override
	public Genome reproduce(Morph newMorph) {
		SnailGenome child = new SnailGenome();
		child.setMorph(newMorph);
		child.opening.setValue(opening.getValue());
		child.displacement.setValue(displacement.getValue());
		child.shape.setValue(shape.getValue());
		child.translation.setValue(translation.getValue());
		child.coarsegraininess.setValue(coarsegraininess.getValue());
		child.reach.setValue(reach.getValue());
		child.generatingCurve.setValue(generatingCurve.getValue());
		child.translationGradient.setValue(translationGradient.getValue());
		child.gradient.setValue(gradient.getValue());
		child.handedness.setValue(handedness.getValue());
		newMorph.getMorphConfig().getMutagen().mutate(child);
		return child;

	}

	@Override
	public void setBasicType(int i) {
		// TODO Auto-generated method stub

	}

	public Gene[] toGeneArray() {
		return new Gene[] { opening, displacement, shape, translation, coarsegraininess, reach, generatingCurve,
				translationGradient, gradient, handedness };
	}

}
