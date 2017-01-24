package net.richarddawkins.watchmaker.morphs.arthro.embryo;

import net.richarddawkins.watchmaker.embryo.SimpleEmbryology;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphs.arthro.genome.ArthromorphGenome;
import net.richarddawkins.watchmaker.morphs.arthro.phenotype.ArthromorphPic;

public class ArthromorphEmbryology extends SimpleEmbryology {
//
//	/**
//	 * The original implmentation could change the value of the variable
//	 * parameter called ySeg. This implementation can't do that, so it returns
//	 * the new value of ySeg.
//	 * <h2>Original documentation</h2>
//	 * <p>
//	 * Starting at the atom 'which', multiply its numbers into the array of
//	 * params.<br>
//	 * At the bottom, draw the part starting at x,y<br>
//	 * params accumulates the final Joint width, Claw angle, etc.<br>
//	 * params: 1 Seg height, 2 Seg width, 3 (not used), 4 Joint thickness, 5
//	 * Joint length, 6 Joint angle, 7 Claw thickness, 8 Claw length, 9 Claw
//	 * angle between pincers<br>
//	 * x,y are current local point, xCenter is the centerline of the animal
//	 * (left and right Joints need this)
//	 * </p>
//	 * 
//	 * @param params
//	 * @param x
//	 * @param y
//	 * @param xCenter
//	 * @throws ArthromorphGradientExceeds1000Exception
//	 *             if the cumulative gradient exceeds 1000.
//	 */
//	public void draw(ArthromorphPic pic, double[] params, int x, int y, int xCenter, int ySeg,
//			boolean centring,
//			boolean sideways,
//			boolean wantColor)
//			throws ArthromorphGradientExceeds1000Exception {
//		double[] myPars = params.clone();
//		// j, oldX, oldY, leftOldX, leftX, offset, thick: integer;
//		// ang, jointscale, theFactor: real;
//		// rankstring: str255;
//
//		double gradientFactor;
//		double jointscale = 0.5;
//		// local copy so next segment builds on section above, not this segment
//		// The original Pascal implementation, grabbed gradient from AnimalTrunk
//		// as it went by,
//		// storing the value in a static (global) variable.
//		// the present implementation stores gradient separately, in the genome.
//		ArthromorphGenome arthromorphGenome = (ArthromorphGenome) genome;
//		gradientFactor = arthromorphGenome.getGradient();
//
//		if (kind == AtomKind.AnimalTrunk) {
//			if (gradientFactor > 1000)
//				throw new ArthromorphGradientExceeds1000Exception("Arthromorph gradient is " + gradientFactor
//						+ " which exceeds 1000. The original code would beep here and continue.");
//		}
//
//		int offset = paramOffset[kind.ordinal()]; // where in params to begin,
//													// see InitBoneYard
//		params[offset] *= height; // fold in this atom's params
//		params[offset + 1] *= width;
//		params[offset + 2] *= angle;// Must be a real number, even if not used
//									// in this Atom
//
//		double overlap = 0;
//		int oldX;
//		int oldY = 0;
//
//		if (kind == AtomKind.SectionTrunk) {
//			overlap = angle; // by convention}
//		} else if (kind == AtomKind.SegmentTrunk) {
//			if (gradientFactor > 1000)
//				throw new ArthromorphGradientExceeds1000Exception("Arthromorph gradient is " + gradientFactor
//						+ " which exceeds 1000. The original code would beep here and continue.");
//			params[1] = params[1] + gradientFactor * angle;
//			params[0] += gradientFactor * angle;
//			drawSeg(g2, x, ySeg, params[1], params[0], sideways, wantColor);
//			// Draw the oval in the right place. 1 = Width , 0 = Height
//			oldY = ySeg; // Save for next segment
//			x += (int) Math.round(params[1] / 2.0); // joint starts at the side
//													// of the segment
//			y = ySeg + (int) Math.round(params[0] / 2.0);
//			// joint starts half way down the segment
//		} else if (kind == AtomKind.Joint) {
//			// both next joint (NextLikeMe) and claw (FirstBelowMe) want x,y at
//			// end of this joint
//			oldX = x;
//			oldY = y;
//			double ang = params[5];
//			x += (int) Math.round(jointscale * params[4] * Math.cos(ang)); // line
//																			// end
//																			// point
//																			// width*sine(angle)
//			y += (int) Math.round(jointscale * params[4] * Math.sin(ang)); // line
//																			// end
//																			// point
//			int thick = 1 + (int) Math.floor(params[3]); // 1 is minimum
//															// thickness
//			drawLine(pic, oldX, oldY, x, y, thick, sideways); // right side leg
//			int leftX = xCenter - (x - xCenter); // do the left side leg
//			int leftOldX = xCenter - (oldX - xCenter);
//			drawLine(pic, leftOldX, oldY, leftX, y, thick, sideways);
//			if (g2 != null)
//				g2.setColor(Color.BLACK);
//		}
//
//		if (kind == AtomKind.Claw) {
//			drawClaw(pic, params, x, y, xCenter, sideways); // all work is done in here
//		} else {
//			// {TED: why else? Presumably because claw is the end of the line?}
//			if (firstBelowMe != null)
//				firstBelowMe.draw(g2, params, x, y, xCenter, ySeg, centring, sideways, wantColor); // build on
//																	// my
//																	// cumulative
//																	// numbers
//
//			if (kind == AtomKind.SegmentTrunk) {
//				x = xCenter;
//				ySeg = (int) Math.round(oldY + overlap * params[1]); // Seg
//				// Jump down by height of this segment to the next segment
//			}
//			if (nextLikeMe != null) {
//				// Note that each Joint builds on the length of the SegJoint,
//				// not the joint just before it.
//				// This is consistent with the way Sections and Segments work.
//				if (kind == AtomKind.AnimalJoint || kind == AtomKind.SectionJoint || kind == AtomKind.SegmentJoint)
//					nextLikeMe.draw(pic, params, x, y, xCenter, ySeg, centring, sideways, wantColor); // build
//																		// on me
//				else if (kind != AtomKind.AnimalTrunk)
//					nextLikeMe.draw(pic, myPars, x, y, xCenter, ySeg, centring, sideways, wantColor); // build
//																		// on my
//																		// parent's
//																		// numbers
//			}
//		}
//	}
//	/**
//	 * <h2>Original documentation</h2> Draw a claw, note that we don't [refer to
//	 * a specific atom] at all. Param info is already folded in
//	 * 
//	 * @param params
//	 *            array of 9 doubles
//	 * @param x
//	 * @param y
//	 * @param xCenter
//	 */
//	void drawClaw(ArthromorphPic pic, double[] params, int x, int y, int xCenter, boolean sideways) {
//
//		int oldX = x;
//		int oldY = y;
//		double ang = params[8] / 2.0; // half claw opening, in radians
//		x = (int) Math.round(x + params[7] * Math.sin(ang)); // line end point
//																// width*sine(angle)
//		y = (int) Math.round(y + params[7] * Math.cos(ang)); // line end point
//		// Used floor instead of Pascal trunc. Maybe not so good. Should be fine
//		// if no negative numbers
//		// ABC
//		int thick = 1 + (int) Math.floor(params[6]); // 1 is minimum thickness
//		drawLine(pic, oldX, oldY, x, y, thick, sideways, ColourPic.RED); // right side, top of claw
//
//		int leftX = xCenter - (x - xCenter); // do the left side, top of claw
//		int leftOldX = xCenter - (oldX - xCenter);
//		drawLine(pic, leftOldX, oldY, leftX, y, thick, sideways, ColourPic.RED);
//
//		// Bottom of the claw}
//		y = (int) Math.round(y - 2.0 * params[7] * Math.cos(ang));
//		drawLine(pic, oldX, oldY, x, y, thick, sideways, ColourPic.RED); // right side}
//		drawLine(pic, leftOldX, oldY, leftX, y, thick, sideways, ColourPic.RED); // left side
//	}
//	void drawLine(ArthromorphPic pic, int x, int y, int endX, int endY, int thick, boolean sideways, int color) {
//		
//		if (sideways) {
//			int temp = x;
//			x = y;
//			y = temp;
//			temp = endX;
//			endX = endY;
//			endY = temp;
//		}
//		pic.picLine(x - thick / 2, y - thick / 2, endX - thick / 2, endY - thick / 2, thick, color);
//		
//	}
//	public void drawOval(ArthromorphPic pic, int x, int y, int width, int height, boolean sideways, boolean wantColor) {
//
//		if (sideways) {
//			int temp = x;
//			x = y;
//			y = temp;
//			temp = width;
//			width = height;
//			height = temp;
//		}
//		Rect rect = new Rect();
//		rect.left = x;
//		rect.top = y;
//		rect.right = rect.left + width;
//		rect.bottom = rect.top + height;
//		Color color;
//			if (wantColor) {
//				color = ColourPic.rgbColorPalette[ColourPic.GREEN];
//			} else {
//				color = ColourPic.rgbColorPalette[ColourPic.LIGHT_GRAY];
//			}
//			pic.addLin(new ArthroLin());
//			g2.fillOval(rect.left, rect.top, rect.getWidth(), rect.getHeight());
//			g2.setColor(Color.BLACK);
//			g2.setStroke(new BasicStroke(2));
//			g2.drawOval(rect.left, rect.top, rect.getWidth(), rect.getHeight());
//			g2.setStroke(new BasicStroke(1));
//		}
//	}
//
//	void drawSeg(ArthromorphPic pic, int x, int y, double width, double height, boolean sideways, boolean wantColor) {
//		int halfW = (int) Math.round(width / 2);
//		// We must adjust the position before drawing the oval
//		drawOval(pic, x - halfW, y, (int) Math.round(width), (int) Math.round(height), sideways, wantColor);
//
//		// convert from center of oval to left corner
//	}
//
//
//	/**
//	 * An example of how to call Draw for an animal
//	 * 
//	 * @param g2
//	 *            a Graphics2D drawing context. May be null (for dry runs,
//	 *            calculating poles)
//	 * @param x
//	 *            the x coordinate where the animal is to be drawn.
//	 * @param y
//	 *            the y coordinate where the animal is to be drawn.
//	 * @throws ArthromorphGradientExceeds1000Exception
//	 *             if the gradient exceeds 1000.
//	 */
//	public void drawAnimal(Atom animalTrunk, Graphics2D g2, int x, int y, boolean centring, boolean sideways, boolean wantColor) throws ArthromorphGradientExceeds1000Exception { // procedure
//		double[] params = new double[9];
//		for (int ii = 0; ii < params.length; ii++)
//			// ii, j, ySeg: integer;
//			params[ii] = 1.0; // clear it all out
//		int ySeg = y;
//		animalTrunk.draw(g2, params, x, y, x, ySeg, centring, sideways, true);
//	}
//
//	public void drawInBox(Atom animalTrunk, Graphics2D g2, boolean centring, boolean sideways, boolean wantColor)
//			throws ArthromorphGradientExceeds1000Exception { // procedure
//																// DrawInBox
//																// (BoxNo:
//																// integer);
//		Point where = new Point(0,0);
//	    int centre;
//		int start;
//		int midriff;
//		int verticalOffset = 0;
//		if (sideways) {
//			centre = where.v;
//			start = where.h;
//            westPole = 0;
//			eastPole = 0;
//			if (centring) {
//				// Original implementation bracketed this call with hidePen /
//				// showPen. This
//				// implementation simply calls drawAnimal with a null graphics
//				// context.
//				drawAnimal(animalTrunk, null, centre, start, centring, sideways, wantColor); // return with NorthPole and
//													// SouthPole updated
//				midriff = westPole + (eastPole - westPole) / 2;
//				verticalOffset = start - midriff;
//			}
//		} else {
//			start = where.v;
//			centre = where.h;
//			northPole = 0;
//			southPole = 0;
//			if (centring) {
//				// Preliminary dummy draw to measure North & South extent of
//				// animal
//				drawAnimal(animalTrunk, null, centre, start, centring, sideways, wantColor); // return with NorthPole and
//													// SouthPole updated
//				midriff = northPole + (southPole - northPole) / 2;
//				verticalOffset = start - midriff;
//			}
//		}
//
//		// if AnimalPicture[BoxNo] <> nil then
//		// KillPicture(AnimalPicture[BoxNo]); {redraw Picture in correct
//		// position}
//		// AnimalPicture[BoxNo] = OpenPicture(Box[BoxNo]);
//		// showpen;
//		// ClipRect(Box[BoxNo]);
//		drawAnimal(animalTrunk, g2, centre, start + verticalOffset, centring, sideways, wantColor);
//		// hidepen;
//		// ClipRect(Prect);
//		// ClosePicture;
//	}
//
//	public void expandPoles(int north, int south, int east, int west) {
//		if (north < northPole)
//			northPole = north;
//		if (south > southPole)
//			southPole = south;
//		if (west < westPole)
//			westPole = west;
//		if (east > eastPole)
//			eastPole = east;
//	}
//	protected int eastPole = 0;
//	protected int northPole = 0;
//
//	protected int southPole = 0;
//
//	protected int westPole = 0;
//
//	public int getEastPole() {
//		return eastPole;
//	}
//	public void setEastPole(int eastPole) {
//		this.eastPole = eastPole;
//	}
//	
//	public void setNorthPole(int northPole) {
//		this.northPole = northPole;
//	}
//
//	public void setSouthPole(int southPole) {
//		this.southPole = southPole;
//	}
//
//	public void setWestPole(int westPole) {
//		this.westPole = westPole;
//	}
//	
//	public int getNorthPole() {
//		return northPole;
//	}
//
//	public int getSouthPole() {
//		return southPole;
//	}
//
//	public int getWestPole() {
//		return westPole;
//	}
	
	@Override
	public void develop(Morph morph) {
		ArthromorphGenome genome = (ArthromorphGenome) morph.getGenome();
		ArthromorphPic pic = (ArthromorphPic) morph.getPic();
		
	}

}
