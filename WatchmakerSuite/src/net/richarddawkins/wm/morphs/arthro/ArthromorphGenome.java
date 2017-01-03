package net.richarddawkins.wm.morphs.arthro;

import java.awt.Dimension;
import java.awt.Graphics2D;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.common.Morph;
import net.richarddawkins.watchmaker.morph.common.geom.Rect;

public class ArthromorphGenome implements Genome {
	
	protected Arthromorph morph;
	
	@Override
	public Morph getMorph() {
		return morph;
	}

	@Override
	public void setMorph(Morph morph) {
		this.morph = (Arthromorph) morph;
	}

	Atom animalTrunk;

	int atomCount;

	double[] cumParams = new double[9]; // Was 1-based array in Pascal

	protected int eastPole = 0;

	int gradient;

	protected int northPole = 0;

	protected int southPole = 0;

	protected int westPole = 0;

	public ArthromorphGenome(Arthromorph morph) {
		this.morph = morph;
	}


	void copy(Genome person) {
		((ArthromorphGenome)person).setAnimalTrunk(animalTrunk.copy());
	}

	
	@Override
	public void develop(Graphics2D g2, Dimension d, boolean zeroMargin) {
		// TODO Auto-generated method stub

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
		ArthromorphGenome child 
		= new ArthromorphGenome((Arthromorph)newMorph);
		// A bit of a cheat, because reproduce needs access to the Config object.
		copy(child);
		int counter = 0;
		boolean done = false;
		do {
			counter++;
			done = newMorph.getMorphConfig().getMutagen().mutate(child); // If it fails, just try again until we succeed at changing something}
		} while (! done && counter <= 1000);
		
		if(counter > 1000)
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

	/**
	 * An example of how to call Draw for an animal
	 * @param g2 a Graphics2D drawing context. May be null (for dry runs, calculating poles)
	 * @throws ArthromorphGradientExceeds1000Exception 
	 */
	public void drawAnimal(Graphics2D g2, int x, int y) throws ArthromorphGradientExceeds1000Exception { // procedure DrawAnimal (BoxNo, x, y: integer);
		double[] params = new double[9];
		for(int ii = 0; ii < params.length; ii++)
		// ii, j, ySeg: integer;
			params[ii] = 1.0; // clear it all out
		int ySeg = y;
		animalTrunk.draw(g2, params, x, y, x, ySeg);
	}
	
	public void drawInBox(Graphics2D g2, Dimension panelSize, boolean midBox) 
			throws ArthromorphGradientExceeds1000Exception { // procedure DrawInBox (BoxNo: integer);
		Rect where = new Rect();
		where.left = 0;
		where.top = 0;
		where.right = panelSize.width;
		where.bottom = panelSize.height;
		int centre;
		int start;
		int boxwidth = panelSize.width;
		int boxheight = panelSize.height;
		ArthromorphConfig config = (ArthromorphConfig) morph.getMorphConfig();
		int midriff;
		int verticalOffset = 0;
		if(config.isSideways()) {
			centre = where.top + boxheight / 2;
			start = where.left + boxwidth / 2;
			westPole = panelSize.width;
			eastPole = 0;
			if(config.isCentring() || midBox) {
				// Original implementation bracketed this call with hidePen / showPen. This
				// implementation simply calls drawAnimal with a null graphics context.
				drawAnimal(null, centre, start); // return with NorthPole and SouthPole updated
				midriff = westPole + (eastPole - westPole) / 2;
	 			verticalOffset = start - midriff;
			}
		} else {
			start = where.top + boxheight / 2;
			centre = where.left + boxwidth / 2;
			northPole = panelSize.height;
			southPole = 0;
			if(config.isCentring() || midBox) {
				// Preliminary dummy draw to measure North & South extent of animal
				drawAnimal(null, centre, start); // return with NorthPole and SouthPole updated
				midriff = northPole + (southPole - northPole) / 2;
				verticalOffset = start - midriff;
			}
		}
		
		// if AnimalPicture[BoxNo] <> nil then
		// KillPicture(AnimalPicture[BoxNo]); {redraw Picture in correct position}
		// AnimalPicture[BoxNo] = OpenPicture(Box[BoxNo]);
		// showpen;
		// ClipRect(Box[BoxNo]);
		 drawAnimal(g2, centre, start + verticalOffset);
		// hidepen;
		// ClipRect(Prect);
		// ClosePicture;
	}
}