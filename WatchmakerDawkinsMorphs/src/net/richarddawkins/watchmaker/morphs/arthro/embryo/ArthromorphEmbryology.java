package net.richarddawkins.watchmaker.morphs.arthro.embryo;

import java.util.Arrays;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.embryo.SimpleEmbryology;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.WatchmakerColor;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphs.arthro.genome.ArthromorphGenome;
import net.richarddawkins.watchmaker.morphs.arthro.genome.Atom;
import net.richarddawkins.watchmaker.morphs.arthro.genome.type.AtomKind;
import net.richarddawkins.watchmaker.morphs.arthro.phenotype.ArthroLimbType;
import net.richarddawkins.watchmaker.morphs.arthro.phenotype.ArthroLin;
import net.richarddawkins.watchmaker.morphs.arthro.phenotype.ArthromorphPic;
import net.richarddawkins.watchmaker.util.Globals;

public class ArthromorphEmbryology extends SimpleEmbryology {
	private static Logger logger = Logger
			.getLogger("net.richarddawkins.watchmaker.morphs.arthro.embryo.ArthromorphEmbryology");

	private static int[] kindsData;

	public static synchronized int[] getKindsData() {
		if (kindsData == null) {
			int[] paramOffset = new int[AtomKind.values().length];
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
			kindsData = paramOffset;
		}
		return kindsData;
	}

	@Override
	public void develop(Morph morph) {
		super.develop(morph);
		ArthromorphGenome genome = (ArthromorphGenome) morph.getGenome();
		ArthromorphPic pic = (ArthromorphPic) morph.getPhenotype();

		double[] params = new double[9];
		for (int ii = 0; ii < params.length; ii++) {
			params[ii] = 1.0; // Clear it all out
		}

		draw(pic, (Atom) genome.getGene(0), params, 0, 0, 0, 0d, 0d);

	}

	/**
	 * 
	 * @param pic
	 * @param atom
	 * @param params
	 * @param x
	 * @param y
	 * @param ySeg
	 * @param gradientFactor
	 * @param overlap
	 * @return the new value of ySeg, used by recursive calls to draw.
	 */
	public int draw(ArthromorphPic pic, Atom atom, double[] params, int x, int y, int ySeg, double gradientFactor,
			double overlap) {

		double jointscale = 0.5;
		// local copy so next segment builds on section above, not this segment
		double[] myPars = Arrays.copyOf(params, params.length);

		if (atom.kind == AtomKind.AnimalTrunk) {
			gradientFactor = atom.gradientGene;
			if (gradientFactor > 1000) {
				Globals.sysbeep(1);
			}
		}
		int[] paramOffset = ArthromorphEmbryology.getKindsData();

		// where in params to begin, see InitBoneYard;
		int offset = paramOffset[atom.kind.ordinal()];
		// fold in this atom's params
		params[offset] = params[offset] * atom.height;
		params[offset + 1] = params[offset + 1] * atom.width;
		params[offset + 2] = params[offset + 2] * atom.angle;
		// Must be a real number, even if not used in this Atom
		if (atom.kind == AtomKind.SectionTrunk) {
			overlap = atom.angle; // by convention
		}
		int oldX = 0;
		int oldY = 0;
		if (atom.kind == AtomKind.SegmentTrunk) {
			if (gradientFactor > 1000) {
				Globals.sysbeep(1);
			}
			params[1] = params[1] + gradientFactor * atom.angle;
			params[0] = params[0] + gradientFactor * atom.angle;
			int halfW = (int) Math.round(params[1] / 2);
			logger.info("HalfW:" + halfW);
			Point startPt = new Point(x - halfW, ySeg);
			Point endPt = new Point((int) params[1], (int) params[0]);
			// Draw the oval in the right place. params[1] = width , params[0] =
			// height
			pic.addLin(new ArthroLin(startPt, endPt, WatchmakerColor.BlackColor, 1, ArthroLimbType.Oval));

			oldY = ySeg; // Save for next segment
			// joint starts at the side of the segment
			x = (int) (x + Math.round(params[1] / 2.0));
			// joint starts half way down the segment
			y = (int) (ySeg + Math.round(params[0] / 2.0));
		} else if (atom.kind == AtomKind.Joint) {
			// both next joint (NextLikeMe) and claw (FirstBelowMe) want x,y at
			// end of this joint}
			oldX = x;
			oldY = y;
			double ang = params[5];
			double xDisp = jointscale * params[4] * Math.cos(ang);
			double yDisp = jointscale * params[4] * Math.sin(ang);
			x = x + (int) Math.round(xDisp);

			// line end point width*sine(angle)
			y = y + (int) Math.round(yDisp);

			int thick = (int) (1 + Math.floor(params[3])); // 1 is minimum
															// thickness

			// right side leg
			Point startPt = new Point(oldX, oldY);
			Point endPt = new Point(x, y);
			pic.addLin(new ArthroLin(startPt, endPt, WatchmakerColor.BlackColor, thick, ArthroLimbType.LineSegment));

			// do the left side leg
			int leftX = -x;
			int leftOldX = -oldX;
			startPt = new Point(leftOldX, oldY);
			endPt = new Point(leftX, y);
			pic.addLin(new ArthroLin(startPt, endPt, WatchmakerColor.BlackColor, thick, ArthroLimbType.LineSegment));
		} else if (atom.kind == AtomKind.Claw) {
			int clawOldX = x;
			int clawOldY = y;
			// half claw opening, in radians
			double ang = params[8] / 2.0;
			// line end point width*sine(angle)
			int clawX = (int) Math.round(x + params[7] * Math.sin(ang));
			// line end point
			int clawY = (int) Math.round(y + params[7] * Math.cos(ang));
			int thick = (int) (1 + Math.floor(params[6])); // 1 is minimum
															// thickness

			// right side, top of claw
			pic.addLin(new ArthroLin(new Point(clawOldX, clawOldY), new Point(clawX, clawY), WatchmakerColor.RedColor,
					thick, ArthroLimbType.LineSegment));

			// do the left side, top of claw
			int clawLeftX = -clawX;
			int clawLeftOldX = -clawOldX;
			pic.addLin(new ArthroLin(new Point(clawLeftOldX, clawOldY), new Point(clawLeftX, clawY),
					WatchmakerColor.RedColor, thick, ArthroLimbType.LineSegment));
			// Bottom of the claw
			clawY = (int) Math.round(clawY - 2.0 * params[7] * Math.cos(ang));
			// right side
			pic.addLin(new ArthroLin(new Point(clawOldX, clawOldY), new Point(clawX, clawY), WatchmakerColor.RedColor,
					thick, ArthroLimbType.LineSegment));
			// left side
			pic.addLin(new ArthroLin(new Point(clawLeftOldX, clawOldY), new Point(clawLeftX, clawY),
					WatchmakerColor.RedColor, thick, ArthroLimbType.LineSegment));

		}
		if (atom.firstBelowMe != null) {
			ySeg = draw(pic, atom.firstBelowMe, params, x, y, ySeg, gradientFactor, overlap);
		}
		// build on my cumulative numbers
		if (atom.kind == AtomKind.SegmentTrunk) {
			x = 0;
			// Jump down by height of this segment to the next segment
			ySeg = (int) Math.round(oldY + overlap * params[0]); // Seg
		}

		// Note that each Joint builds on the length of the SegJoint, not the
		// joint just before it.
		// This is consistant with the way Sections and Segments work.

		if (atom.nextLikeMe != null) {
			if (atom.kind == AtomKind.AnimalJoint || atom.kind == AtomKind.SectionJoint
					|| atom.kind == AtomKind.SegmentJoint) {
				ySeg = draw(pic, atom.nextLikeMe, params, x, y, ySeg, gradientFactor, overlap);
			} else if (atom.kind != AtomKind.AnimalTrunk) {
				// build on my parent's numbers
				ySeg = draw(pic, atom.nextLikeMe, myPars, x, y, ySeg, gradientFactor, overlap);

			}
		}

		return ySeg;
	}

}
