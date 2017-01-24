package net.richarddawkins.watchmaker.morphs.arthro.genome;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.SimpleGenome;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphs.arthro.genome.type.AtomKind;

public class ArthromorphGenome extends SimpleGenome {
	protected Atom animalTrunk;

	protected int atomCount;

	protected double[] cumParams = new double[9]; // Was 1-based array in Pascal


	protected int gradient;


	public ArthromorphGenome() {

	}

	public void copy(Genome target) {
		ArthromorphGenome genome = (ArthromorphGenome) target;
		((ArthromorphGenome) target).setAnimalTrunk(animalTrunk.copy());
	}



	public Atom getAnimalTrunk() {
		return animalTrunk;
	}

	public int getAtomCount() {
		return atomCount;
	}

	public double[] getCumParams() {
		return cumParams;
	}


	public void minimalAnimal() {
		Atom aa = new Atom(AtomKind.Claw, this);
		Atom bb = new Atom(AtomKind.Joint, this);

		bb.setWidth(5);
		bb.setAngle(2);
		bb.setFirstBelowMe(aa);

		aa = new Atom(AtomKind.SegmentClaw, this);
		aa.setFirstBelowMe(bb);

		bb = new Atom(AtomKind.SegmentJoint, this);
		bb.setNextLikeMe(aa);
		bb.setAngle(2);

		aa = new Atom(AtomKind.SegmentTrunk, this);
		aa.setFirstBelowMe(bb);

		bb = new Atom(AtomKind.SectionClaw, this);
		bb.setFirstBelowMe(aa);

		aa = new Atom(AtomKind.SectionJoint, this);
		aa.setNextLikeMe(bb);

		bb = new Atom(AtomKind.SectionTrunk, this);
		bb.setAngle(0.5); // Segment overlap, by convention
		bb.setFirstBelowMe(aa);

		aa = new Atom(AtomKind.AnimalClaw, this);
		aa.setFirstBelowMe(bb);

		bb = new Atom(AtomKind.AnimalJoint, this);
		bb.setNextLikeMe(aa);
		bb.setWidth(5); // make it visible
		bb.setAngle(5);

		aa = new Atom(AtomKind.AnimalTrunk, this);
		aa.setFirstBelowMe(bb);
		aa.setHeight(20);
		aa.setWidth(20);
		gradient = -2; // Gradient, by convention
		atomCount = aa.countAtoms();
		animalTrunk = aa;
	}

	
	public Genome reproduce(Morph newMorph) {
		ArthromorphGenome child = new ArthromorphGenome();
		// A bit of a cheat, because reproduce needs access to the Config
		// object.
		copy(child);
		
		return child;
	}

	public void setAnimalTrunk(Atom animalTrunk) {
		this.animalTrunk = animalTrunk;
	}

	public void setAtomCount(int atomCount) {
		this.atomCount = atomCount;
	}

	@Override
	public void setBasicType(int i) {
		minimalAnimal();
	}

	public void setCumParams(double[] cumParams) {
		this.cumParams = cumParams;
	}


	public int getGradient() {
		return gradient;
	}


	public void setGradient(int gradient) {
		this.gradient = gradient;
	}



	@Override
	public Gene[] toGeneArray() {
		return new Gene[] { animalTrunk };
	}


}
