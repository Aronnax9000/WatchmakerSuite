package net.richarddawkins.watchmaker.morphs.arthro.genome;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GenomeFactory;
import net.richarddawkins.watchmaker.genome.mutation.AllowedMutations;
import net.richarddawkins.watchmaker.morphs.arthro.genome.type.AtomKind;
import net.richarddawkins.watchmaker.morphs.mono.genome.BiomorphGenomeFactory;

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

	protected Genome complexAnimal() {
		ArthromorphGenome genome = new ArthromorphGenome();
//		Height Width Angle
//		20.00 20.00 24.00 AnimalTrunk
//		01.00 05.00 05.00     AnimalJoint
//		01.00 01.00 01.00     AnimalClaw
//		01.00 01.00 00.50         SectionTrunk
//		01.00 01.00 01.00             SectionJoint
//		01.00 01.00 01.00             SectionClaw
//		01.00 01.00 01.00                 SegmentTrunk 1
//		01.00 01.00 01.79                     SegmentJoint
//		01.00 01.00 01.00                     SegmentClaw
//		01.00 05.00 02.00                       Joint
//		01.00 05.00 -2.00                       Joint
//		01.00 01.00 01.00                       Claw
//		01.00 01.00 02.00                 SegmentTrunk 2
//		01.00 01.00 02.00                     SegmentJoint
//		01.00 01.00 01.00                     SegmentClaw
//		01.00 05.00 02.00                       Joint
//		01.00 05.00 -2.00                       Joint
//		01.00 01.00 01.00                       Claw
//		01.00 01.00 03.00                 SegmentTrunk 2
//		01.00 01.00 02.00                     SegmentJoint
//		01.00 01.00 01.00                     SegmentClaw
//		01.00 05.00 02.00                       Joint
//		01.00 05.00 -2.00                       Joint
//		01.00 01.00 01.00                       Claw		
		return genome;
	}
	
	protected Genome minimalAnimal() {
		ArthromorphGenome genome = new ArthromorphGenome();
		Atom aa = new Atom(genome, AtomKind.Claw);
		Atom bb = new Atom(genome, AtomKind.Joint);

		bb.setWidth(5);
		bb.setAngle(2);
		bb.setFirstBelowMe(aa);

		aa = new Atom(genome, AtomKind.SegmentClaw);
		aa.setFirstBelowMe(bb);

		bb = new Atom(genome, AtomKind.SegmentJoint);
		bb.setNextLikeMe(aa);
		bb.setAngle(2);

		aa = new Atom(genome, AtomKind.SegmentTrunk);
		aa.setFirstBelowMe(bb);

		bb = new Atom(genome, AtomKind.SectionClaw);
		bb.setFirstBelowMe(aa);

		aa = new Atom(genome, AtomKind.SectionJoint);
		aa.setNextLikeMe(bb);

		bb = new Atom(genome, AtomKind.SectionTrunk);
		bb.setAngle(0.5); // Segment overlap, by convention
		bb.setFirstBelowMe(aa);

		aa = new Atom(genome, AtomKind.AnimalClaw);
		aa.setFirstBelowMe(bb);

		bb = new Atom(genome, AtomKind.AnimalJoint);
		bb.setNextLikeMe(aa);
		bb.setWidth(5); // make it visible
		bb.setAngle(5);

		aa = new Atom(genome, AtomKind.AnimalTrunk);
		aa.setFirstBelowMe(bb);
		aa.setHeight(20);
		aa.setWidth(20);
		aa.setGradientGene(-2); 
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
