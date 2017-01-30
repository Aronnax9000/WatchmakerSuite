package net.richarddawkins.watchmaker.morphs.arthro.genome;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GenomeFactory;
import net.richarddawkins.watchmaker.genome.mutation.AllowedMutations;
import net.richarddawkins.watchmaker.morphs.arthro.genome.type.AtomKind;
import net.richarddawkins.watchmaker.morphs.bio.genome.BiomorphGenomeFactory;

public class ArthromorphGenomeFactory extends GenomeFactory {

	public ArthromorphGenomeFactory(AllowedMutations muts) {
		super(muts);

	}

	public Genome getBasicType(int basicType) {
		if (basicType == BiomorphGenomeFactory.RANDOM)
			return deliverSaltation();
		else
			return minimalAnimal();
	}

	protected Genome minimalAnimal() {
		ArthromorphGenome genome = new ArthromorphGenome();
		Atom aa = new Atom(AtomKind.Claw, genome);
		Atom bb = new Atom(AtomKind.Joint, genome);

		bb.setWidth(5);
		bb.setAngle(2);
		bb.setFirstBelowMe(aa);

		aa = new Atom(AtomKind.SegmentClaw, genome);
		aa.setFirstBelowMe(bb);

		bb = new Atom(AtomKind.SegmentJoint, genome);
		bb.setNextLikeMe(aa);
		bb.setAngle(2);

		aa = new Atom(AtomKind.SegmentTrunk, genome);
		aa.setFirstBelowMe(bb);

		bb = new Atom(AtomKind.SectionClaw, genome);
		bb.setFirstBelowMe(aa);

		aa = new Atom(AtomKind.SectionJoint, genome);
		aa.setNextLikeMe(bb);

		bb = new Atom(AtomKind.SectionTrunk, genome);
		bb.setAngle(0.5); // Segment overlap, by convention
		bb.setFirstBelowMe(aa);

		aa = new Atom(AtomKind.AnimalClaw, genome);
		aa.setFirstBelowMe(bb);

		bb = new Atom(AtomKind.AnimalJoint, genome);
		bb.setNextLikeMe(aa);
		bb.setWidth(5); // make it visible
		bb.setAngle(5);

		aa = new Atom(AtomKind.AnimalTrunk, genome);
		aa.setFirstBelowMe(bb);
		aa.setHeight(20);
		aa.setWidth(20);
		genome.setGradient(-2); // Gradient, by convention
		genome.setAtomCount(aa.countAtoms());
		genome.setAnimalTrunk(aa);
		return genome;
	}

	@Override
	public Genome deliverSaltation() {
		// TODO Auto-generated method stub
		return null;
	}

}
