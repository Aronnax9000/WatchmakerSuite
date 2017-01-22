package net.richarddawkins.watchmaker.morphs.arthro.genome;

import java.awt.Graphics2D;
import java.util.Vector;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.SimpleGenome;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphs.arthro.ArthromorphConfig;
import net.richarddawkins.watchmaker.morphs.arthro.genome.type.AtomKind;

public class ArthromorphGenome extends SimpleGenome {
	Atom animalTrunk;

	int atomCount;

	double[] cumParams = new double[9]; // Was 1-based array in Pascal

	protected int eastPole = 0;

	int gradient;

	protected int northPole = 0;

	protected int southPole = 0;

	protected int westPole = 0;

	public ArthromorphGenome() {

	}

	public void copy(Genome person) {
		((ArthromorphGenome) person).setAnimalTrunk(animalTrunk.copy());
	}

	public void develop() {
		// TODO Auto-generated method stub

	}

	/**
	 * An example of how to call Draw for an animal
	 * 
	 * @param g2
	 *            a Graphics2D drawing context. May be null (for dry runs,
	 *            calculating poles)
	 * @param x
	 *            the x coordinate where the animal is to be drawn.
	 * @param y
	 *            the y coordinate where the animal is to be drawn.
	 * @throws ArthromorphGradientExceeds1000Exception
	 *             if the gradient exceeds 1000.
	 */
	public void drawAnimal(Graphics2D g2, int x, int y) throws ArthromorphGradientExceeds1000Exception { // procedure
																											// DrawAnimal
																											// (BoxNo,
																											// x,
																											// y:
																											// integer);
		double[] params = new double[9];
		for (int ii = 0; ii < params.length; ii++)
			// ii, j, ySeg: integer;
			params[ii] = 1.0; // clear it all out
		int ySeg = y;
		animalTrunk.draw(g2, params, x, y, x, ySeg);
	}

	public void drawInBox(Graphics2D g2)
			throws ArthromorphGradientExceeds1000Exception { // procedure
																// DrawInBox
																// (BoxNo:
																// integer);
		Point where = new Point(0,0);
	    int centre;
		int start;
		ArthromorphConfig config = (ArthromorphConfig) morph.getMorphConfig();
		int midriff;
		int verticalOffset = 0;
		if (config.isSideways()) {
			centre = where.v;
			start = where.h;
            westPole = 0;
			eastPole = 0;
			if (config.isCentring()) {
				// Original implementation bracketed this call with hidePen /
				// showPen. This
				// implementation simply calls drawAnimal with a null graphics
				// context.
				drawAnimal(null, centre, start); // return with NorthPole and
													// SouthPole updated
				midriff = westPole + (eastPole - westPole) / 2;
				verticalOffset = start - midriff;
			}
		} else {
			start = where.v;
			centre = where.h;
			northPole = 0;
			southPole = 0;
			if (config.isCentring()) {
				// Preliminary dummy draw to measure North & South extent of
				// animal
				drawAnimal(null, centre, start); // return with NorthPole and
													// SouthPole updated
				midriff = northPole + (southPole - northPole) / 2;
				verticalOffset = start - midriff;
			}
		}

		// if AnimalPicture[BoxNo] <> nil then
		// KillPicture(AnimalPicture[BoxNo]); {redraw Picture in correct
		// position}
		// AnimalPicture[BoxNo] = OpenPicture(Box[BoxNo]);
		// showpen;
		// ClipRect(Box[BoxNo]);
		drawAnimal(g2, centre, start + verticalOffset);
		// hidepen;
		// ClipRect(Prect);
		// ClosePicture;
	}

	public void expandPoles(int north, int south, int east, int west) {
		if (north < northPole)
			northPole = north;
		if (south > southPole)
			southPole = south;
		if (west < westPole)
			westPole = west;
		if (east > eastPole)
			eastPole = east;
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

	public int getEastPole() {
		return eastPole;
	}

	public int getGradient() {
		return gradient;
	}

	public int getNorthPole() {
		return northPole;
	}

	public int getSouthPole() {
		return southPole;
	}

	public int getWestPole() {
		return westPole;
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

	@Override
	public Genome reproduce(Morph newMorph) {
		ArthromorphGenome child = new ArthromorphGenome();
		child.setMorph(newMorph);
		// A bit of a cheat, because reproduce needs access to the Config
		// object.
		copy(child);
		int counter = 0;
		boolean done = false;
		do {
			counter++;
			done = newMorph.getMorphConfig().getMutagen().mutate(child); // If
																			// it
																			// fails,
																			// just
																			// try
																			// again
																			// until
																			// we
																			// succeed
																			// at
																			// changing
																			// something}
		} while (!done && counter <= 1000);

		if (counter > 1000)
			try {
				throw new Exception("Timed out, perhaps attempting impossible duplication or deletion");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	public void setEastPole(int eastPole) {
		this.eastPole = eastPole;
	}

	public void setGradient(int gradient) {
		this.gradient = gradient;
	}

	public void setNorthPole(int northPole) {
		this.northPole = northPole;
	}

	public void setSouthPole(int southPole) {
		this.southPole = southPole;
	}

	public void setWestPole(int westPole) {
		this.westPole = westPole;
	}

	@Override
	public Gene[] toGeneArray() {
		Vector<Atom> atoms = new Vector<Atom>();
		animalTrunk.addChildrenToVectorDepthFirst(atoms);
		return (Atom[]) atoms.toArray();
	}


}
