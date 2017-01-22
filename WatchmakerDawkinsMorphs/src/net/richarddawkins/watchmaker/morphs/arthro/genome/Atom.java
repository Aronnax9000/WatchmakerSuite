package net.richarddawkins.watchmaker.morphs.arthro.genome;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

import net.richarddawkins.watchmaker.genome.GeneManipulationEvent;
import net.richarddawkins.watchmaker.genome.SimpleGene;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morphs.arthro.ArthromorphConfig;
import net.richarddawkins.watchmaker.morphs.arthro.genome.type.AtomKind;

public class Atom extends SimpleGene implements Cloneable {

	static int[] paramOffset = new int[AtomKind.values().length];

	static {
		// where in a CumParams the Width of an AnimalTrunk gets multiplied in
		paramOffset[AtomKind.AnimalTrunk.ordinal()] = 0;
		paramOffset[AtomKind.AnimalJoint.ordinal()] = 3;
		paramOffset[AtomKind.AnimalClaw.ordinal()] = 6;
		paramOffset[AtomKind.SectionTrunk.ordinal()] = 0;
		paramOffset[AtomKind.SectionJoint.ordinal()] = 3;
		paramOffset[AtomKind.SectionClaw.ordinal()] = 6;
		paramOffset[AtomKind.SegmentTrunk.ordinal()] = 0;
		paramOffset[AtomKind.SegmentJoint.ordinal()] = 3;
		paramOffset[AtomKind.SegmentClaw.ordinal()] = 6;
		paramOffset[AtomKind.Joint.ordinal()] = 3;
		paramOffset[AtomKind.Claw.ordinal()] = 6;

	}

	public int segmentNumber = 0;

	public Atom(AtomKind kind, ArthromorphGenome genome) {
		super(genome, kind.toString());
		this.kind = kind;
		height = 1.0;
		width = 1.0;
		angle = 1.0;
	}

	public AtomKind getKind() {
		return kind;
	}

	public void setKind(AtomKind kind) {
		this.kind = kind;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public Atom getNextLikeMe() {
		return nextLikeMe;
	}

	public void setNextLikeMe(Atom nextLikeMe) {
		this.nextLikeMe = nextLikeMe;
	}

	public Atom getFirstBelowMe() {
		return firstBelowMe;
	}

	public void setFirstBelowMe(Atom firstBelowMe) {
		this.firstBelowMe = firstBelowMe;
	}

	public Atom(AtomKind kind, double height, double width, double angle, Atom nextLikeMe, Atom firstBelowMe) {
		super(null, kind.toString());
		this.kind = kind;
		this.height = height;
		this.width = width;
		this.angle = angle;
		this.nextLikeMe = nextLikeMe;
		this.firstBelowMe = firstBelowMe;
	}

	public AtomKind kind = AtomKind.Free;
	/**
	 * Original documentation: Also used for Thickness of a Joint
	 */
	public double height;
	/**
	 * Original documentation: Also used for Length of a Joint
	 */
	public double width;
	/**
	 * Original documentation:
	 * <ul>
	 * <li>also used in an AnimalTrunk to store the number of atoms in the
	 * animal.</li>
	 * <li>also used in SectionTrunk to store the Overlap of segments</li>
	 * <li>also used in SegmentTrunk to store the rank number of the
	 * segment</li>
	 * </ul>
	 */
	public double angle;

	/**
	 * Original documentation: Where to look in the BoneYard for the next atom.
	 * 0 means end of chain. Also used in AnimalTrunk to store Gradient gene,
	 * slightly more or less than 100. Treat as Percentage
	 */
	public Atom nextLikeMe;
	/**
	 * Original documentation: where to look in the BoneYard for the next atom.
	 * 0 means end of chain
	 */
	public Atom firstBelowMe;

	/**
	 * Recursively traverse tree of atoms to calculate the total number of atoms
	 * in the tree.
	 * <h2>Original Pascal Source</h2>
	 * <p>
	 * In Classic Arthromorphs, the NextLikeMe field for AnimalTrunk was used to
	 * store the gradient gene. In the Java version, gradient is separately
	 * tracked in the Arthromorph class. For this reason, the "kind !=
	 * AnimalTrunk" check is not needed.
	 * </p>
	 * 
	 * <pre>
	 * 	function CountAtoms (which: integer): integer;
	 * 	{travel over the Animal, counting Atoms}
	 * 		var
	 * 			count: integer;
	 * 	begin
	 * 		count := 1;	{count me}
	 * 		with BoneYard[which]^^ do
	 * 			begin
	 * 				if FirstBelowMe &lt;&gt; 0 then
	 * 					count := count + CountAtoms(FirstBelowMe);
	 * 				if (NextLikeMe &lt;&gt; 0) and (kind &lt;&gt; AnimalTrunk) then
	 * 					count := count + CountAtoms(NextLikeMe);
	 * 			end;
	 * 		CountAtoms := count;	{Me and all below me}
	 * 	end;
	 * </pre>
	 * 
	 * @return the number of atoms in the tree.
	 */
	public int countAtoms() {
		return this.toVector().size();
	}

	public Vector<Atom> toVector() {
		Vector<Atom> atoms = new Vector<Atom>();
		atoms.add(this);
		if (firstBelowMe != null)
			atoms.addAll(firstBelowMe.toVector());
		if (nextLikeMe != null)
			atoms.addAll(nextLikeMe.toVector());
		return atoms;
	}

	/**
	 * 
	 * <h2>Original Documentation</h2>
	 * <p>
	 * Destroy this animal. Mark all of its Atoms as Free again.
	 * </p>
	 * <p>
	 * Recursively step through the animal
	 * </p>
	 */
	public void kill() {
		if (firstBelowMe != null) {
			firstBelowMe.kill();
			firstBelowMe = null;
		}
		if (nextLikeMe != null) {
			nextLikeMe.kill();
			nextLikeMe = null;
		}
	}

	public Object clone() {
		Atom clone = null;
		try {
			clone = (Atom) super.clone();
			if (clone.firstBelowMe != null)
				clone.firstBelowMe = (Atom) clone.firstBelowMe.clone();
			if (clone.nextLikeMe != null)
				clone.nextLikeMe = (Atom) clone.nextLikeMe.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return clone;
	}

	public Atom copy() {
		Atom copy = (Atom) this.clone();
		if (copy.firstBelowMe != null) {
			copy.firstBelowMe = copy.firstBelowMe.copy();
		}
		if (copy.nextLikeMe != null) {
			copy.nextLikeMe = copy.nextLikeMe.copy();
		}
		return copy;
	}

	public Atom copyExceptNext() {
		Atom copy = (Atom) this.clone();
		if (copy.firstBelowMe != null) {
			copy.firstBelowMe = copy.firstBelowMe.copy();
		}
		copy.nextLikeMe = null;
		return copy;
	}

	/**
	 * <h2>Original Documentation</h2>
	 * <p>
	 * Delete a section of the animal somewhere near the atom which.
	 * </p>
	 * <p>
	 * Caller must correct the AtomCount of the whole animal. Return false if
	 * failed
	 * </p>
	 * <p>
	 * Must have a hold on the atom above what we delete. If chosen atom is:
	 * </p>
	 * 
	 * <pre>
	 *AnimalTrunk   delete first Sec
	 *	AnimalJoint   delete first Sec
	 *	AnimalClaw	delete first Sec
	 *		SectionTrunk	delete next Sec
	 *			SectionJoint		delete first Seg
	 *			SectionClaw		delete first Seg
	 *				SegmentTrunk		delete next Seg
	 *					SegmentJoint		delete first Joint
	 *					SegmentClaw		delete first Joint
	 *						Joint				delete next Joint
	 *						Joint				delete next Joint
	 *						Joint				delete Claw
	 *							Claw				fail
	 * </pre>
	 * <p>
	 * Also fail if trying to delete last example of a Kind
	 * 
	 * @return whether the delete succeeded.
	 *         </p>
	 */
	public boolean doDelete() {
		Atom chain = null;
		Atom parent = this;
		boolean doDelete = false; // unless we actually succeed in killing one
		if (parent.kind == AtomKind.AnimalTrunk)
			parent = parent.firstBelowMe; // AnimalJoint
		if (parent.kind == AtomKind.AnimalJoint || parent.kind == AtomKind.SectionJoint
				|| parent.kind == AtomKind.SegmentJoint)

			parent = parent.firstBelowMe; // AnimalClaw is parent
		if (parent != null)
			if (parent.kind == AtomKind.SectionTrunk || parent.kind == AtomKind.SegmentTrunk
					|| parent.kind == AtomKind.Joint)
				// Delete NextLikeMe of parent}
				if (parent.nextLikeMe != null) {
					chain = parent.nextLikeMe.nextLikeMe; // May be null;
					parent.nextLikeMe.nextLikeMe = null; // So Kill won't get
															// the rest of chain
					parent.nextLikeMe.kill(); // won't be killing last one,
												// since parent qualifies as one
					parent.nextLikeMe = chain;
					doDelete = true;
				} else // Try to delete FirstBelow
				if (parent.firstBelowMe != null) { // we know FirstBelow exists
					chain = parent.firstBelowMe.nextLikeMe; // Atom after one we
															// will delete
					parent.firstBelowMe.nextLikeMe = null;
					if (chain != null) { // FirstBelow is not only one
						parent.firstBelowMe.kill();
						parent.firstBelowMe = chain;
						doDelete = true;
					}
				}
		return doDelete;
	}

	/**
	 * The original implmentation could change the value of the variable
	 * parameter called ySeg. This implementation can't do that, so it returns
	 * the new value of ySeg.
	 * <h2>Original documentation</h2>
	 * <p>
	 * Starting at the atom 'which', multiply its numbers into the array of
	 * params.<br>
	 * At the bottom, draw the part starting at x,y<br>
	 * params accumulates the final Joint width, Claw angle, etc.<br>
	 * params: 1 Seg height, 2 Seg width, 3 (not used), 4 Joint thickness, 5
	 * Joint length, 6 Joint angle, 7 Claw thickness, 8 Claw length, 9 Claw
	 * angle between pincers<br>
	 * x,y are current local point, xCenter is the centerline of the animal
	 * (left and right Joints need this)
	 * </p>
	 * 
	 * @param params
	 * @param x
	 * @param y
	 * @param xCenter
	 * @throws ArthromorphGradientExceeds1000Exception
	 *             if the cumulative gradient exceeds 1000.
	 */
	public void draw(Graphics2D g2, double[] params, int x, int y, int xCenter, int ySeg)
			throws ArthromorphGradientExceeds1000Exception {
		double[] myPars = params.clone();
		// j, oldX, oldY, leftOldX, leftX, offset, thick: integer;
		// ang, jointscale, theFactor: real;
		// rankstring: str255;

		double gradientFactor;
		double jointscale = 0.5;
		// local copy so next segment builds on section above, not this segment
		// The original Pascal implementation, grabbed gradient from AnimalTrunk
		// as it went by,
		// storing the value in a static (global) variable.
		// the present implementation stores gradient separately, in the genome.
		ArthromorphGenome arthromorphGenome = (ArthromorphGenome) genome;
		gradientFactor = arthromorphGenome.getGradient();

		if (kind == AtomKind.AnimalTrunk) {
			if (gradientFactor > 1000)
				throw new ArthromorphGradientExceeds1000Exception("Arthromorph gradient is " + gradientFactor
						+ " which exceeds 1000. The original code would beep here and continue.");
		}

		int offset = paramOffset[kind.ordinal()]; // where in params to begin,
													// see InitBoneYard
		params[offset] *= height; // fold in this atom's params
		params[offset + 1] *= width;
		params[offset + 2] *= angle;// Must be a real number, even if not used
									// in this Atom

		double overlap = 0;
		int oldX;
		int oldY = 0;

		if (kind == AtomKind.SectionTrunk) {
			overlap = angle; // by convention}
		} else if (kind == AtomKind.SegmentTrunk) {
			if (gradientFactor > 1000)
				throw new ArthromorphGradientExceeds1000Exception("Arthromorph gradient is " + gradientFactor
						+ " which exceeds 1000. The original code would beep here and continue.");
			params[1] = params[1] + gradientFactor * angle;
			params[0] += gradientFactor * angle;
			drawSeg(g2, x, ySeg, params[1], params[0]);
			// Draw the oval in the right place. 1 = Width , 0 = Height
			oldY = ySeg; // Save for next segment
			x += (int) Math.round(params[1] / 2.0); // joint starts at the side
													// of the segment
			y = ySeg + (int) Math.round(params[0] / 2.0);
			// joint starts half way down the segment
		} else if (kind == AtomKind.Joint) {
			// both next joint (NextLikeMe) and claw (FirstBelowMe) want x,y at
			// end of this joint
			oldX = x;
			oldY = y;
			double ang = params[5];
			x += (int) Math.round(jointscale * params[4] * Math.cos(ang)); // line
																			// end
																			// point
																			// width*sine(angle)
			y += (int) Math.round(jointscale * params[4] * Math.sin(ang)); // line
																			// end
																			// point
			int thick = 1 + (int) Math.floor(params[3]); // 1 is minimum
															// thickness
			drawLine(g2, oldX, oldY, x, y, thick); // right side leg
			int leftX = xCenter - (x - xCenter); // do the left side leg
			int leftOldX = xCenter - (oldX - xCenter);
			drawLine(g2, leftOldX, oldY, leftX, y, thick);
			if (g2 != null)
				g2.setColor(Color.BLACK);
		}

		if (kind == AtomKind.Claw) {
			drawClaw(g2, params, x, y, xCenter); // all work is done in here
		} else {
			// {TED: why else? Presumably because claw is the end of the line?}
			if (firstBelowMe != null)
				firstBelowMe.draw(g2, params, x, y, xCenter, ySeg); // build on
																	// my
																	// cumulative
																	// numbers

			if (kind == AtomKind.SegmentTrunk) {
				x = xCenter;
				ySeg = (int) Math.round(oldY + overlap * params[1]); // Seg
				// Jump down by height of this segment to the next segment
			}
			if (nextLikeMe != null) {
				// Note that each Joint builds on the length of the SegJoint,
				// not the joint just before it.
				// This is consistent with the way Sections and Segments work.
				if (kind == AtomKind.AnimalJoint || kind == AtomKind.SectionJoint || kind == AtomKind.SegmentJoint)
					nextLikeMe.draw(g2, params, x, y, xCenter, ySeg); // build
																		// on me
				else if (kind != AtomKind.AnimalTrunk)
					nextLikeMe.draw(g2, myPars, x, y, xCenter, ySeg); // build
																		// on my
																		// parent's
																		// numbers
			}
		}
	}

	/**
	 * <h2>Original documentation</h2> Draw a claw, note that we don't [refer to
	 * a specific atom] at all. Param info is already folded in
	 * 
	 * @param params
	 *            array of 9 doubles
	 * @param x
	 * @param y
	 * @param xCenter
	 */
	void drawClaw(Graphics2D g2, double[] params, int x, int y, int xCenter) {

		if (g2 != null)
			g2.setColor(Color.RED);
		int oldX = x;
		int oldY = y;
		double ang = params[8] / 2.0; // half claw opening, in radians
		x = (int) Math.round(x + params[7] * Math.sin(ang)); // line end point
																// width*sine(angle)
		y = (int) Math.round(y + params[7] * Math.cos(ang)); // line end point
		// Used floor instead of Pascal trunc. Maybe not so good. Should be fine
		// if no negative numbers
		// ABC
		int thick = 1 + (int) Math.floor(params[6]); // 1 is minimum thickness
		drawLine(g2, oldX, oldY, x, y, thick); // right side, top of claw

		int leftX = xCenter - (x - xCenter); // do the left side, top of claw
		int leftOldX = xCenter - (oldX - xCenter);
		drawLine(g2, leftOldX, oldY, leftX, y, thick);

		// Bottom of the claw}
		y = (int) Math.round(y - 2.0 * params[7] * Math.cos(ang));
		drawLine(g2, oldX, oldY, x, y, thick); // right side}
		drawLine(g2, leftOldX, oldY, leftX, y, thick); // left side
		if (g2 != null)
			g2.setColor(Color.BLACK);
	}

	void drawLine(Graphics2D g2, int x, int y, int endX, int endY, int thick) {
		ArthromorphConfig config = (ArthromorphConfig) genome.getMorph().getMorphConfig();
		if (config.isSideways()) {
			int temp = x;
			x = y;
			y = temp;
			temp = endX;
			endX = endY;
			endY = temp;
		}
		((ArthromorphGenome) genome).expandPoles(endY, endY, endX, endX);
		if (g2 != null) {
			g2.setStroke(new BasicStroke(thick)); // PenSize(thick, thick);
			g2.drawLine(x - thick / 2, y - thick / 2, endX - thick / 2, endY - thick / 2);
			g2.setStroke(new BasicStroke(1)); // PenSize(1, 1);
		}
	}

	public void drawOval(Graphics2D g2, int x, int y, int width, int height) {

		ArthromorphConfig config = (ArthromorphConfig) genome.getMorph().getMorphConfig();
		if (config.isSideways()) {
			int temp = x;
			x = y;
			y = temp;
			temp = width;
			width = height;
			height = temp;
		}
		Rect rect = new Rect();
		rect.left = x;
		rect.top = y;
		rect.right = rect.left + width;
		rect.bottom = rect.top + height;

		((ArthromorphGenome) genome).expandPoles(rect.top, rect.bottom, rect.left, rect.right);
		if (g2 != null) {
			if (config.isWantColor()) {
				g2.setColor(Color.GREEN);
			} else {
				g2.setColor(Color.LIGHT_GRAY);
			}
			g2.fillOval(rect.left, rect.top, rect.getWidth(), rect.getHeight());
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(2));
			g2.drawOval(rect.left, rect.top, rect.getWidth(), rect.getHeight());
			g2.setStroke(new BasicStroke(1));
		}
	}

	void drawSeg(Graphics2D g2, int x, int y, double width, double height) {
		int halfW = (int) Math.round(width / 2);
		// We must adjust the position before drawing the oval
		drawOval(g2, x - halfW, y, (int) Math.round(width), (int) Math.round(height));
		if (g2 != null)
			g2.setColor(Color.BLACK);
		// convert from center of oval to left corner
	}

	@Override
	public void geneManipulated(GeneManipulationEvent gbme) {
		// TODO Auto-generated method stub

	}

protected void addChildrenToVectorDepthFirst(Vector<Atom> atoms) {
	atoms.add(this);
	if(this.firstBelowMe != null) this.firstBelowMe.addChildrenToVectorDepthFirst(atoms);
	if(this.nextLikeMe != null) this.nextLikeMe.addChildrenToVectorDepthFirst(atoms);
}

}
