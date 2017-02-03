package net.richarddawkins.watchmaker.morphs.concho.embryo;

import java.util.logging.Logger;

import net.richarddawkins.watchmaker.embryo.SimpleEmbryology;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphs.bio.geom.Lin;
import net.richarddawkins.watchmaker.morphs.concho.genome.DoubleGene;
import net.richarddawkins.watchmaker.morphs.concho.genome.SnailGenome;
import net.richarddawkins.watchmaker.morphs.concho.genome.type.HandednessType;
import net.richarddawkins.watchmaker.morphs.concho.geom.RealPoint;
import net.richarddawkins.watchmaker.morphs.concho.geom.SnailPic;
import net.richarddawkins.watchmaker.morphs.mono.geom.MonoLin;

public class SnailEmbryology extends SimpleEmbryology {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morphs.concho.embryo.SnailEmbryology");

	SnailEmbryologyPreferences prefs = new SnailEmbryologyPreferences();

	public SnailEmbryology() {

	}

	public void develop(Morph morph) {
		super.develop(morph);

		SnailGenome genome = (SnailGenome) morph.getGenome();
		SnailPic pic = (SnailPic) morph.getPhenotype();

		if (prefs.isSideView()) {
			drawShell(genome, pic);
		} else {
			drawTop(genome, pic);
		}
	}

	/**
	 * {@code
	 * procedure DrawTop(var margin: Rect; var MyPic: Pic; var theShell: person; where: Point);
	 * var
	 *   mn: RealPoint;
	 *   v1: RealPoint;
	 *   v2: RealPoint;
	 *   u1: RealPoint;
	 *   u2: RealPoint;
	 *   p1: RealPoint;
	 *   p2: RealPoint;
	 *   denom, theSize, W, D: real;
	 *   RAD: real;
	 *   PI, I, BD, WI: real;
	 *   M, start, inc: integer;
	 *   p: real;
	 * 
	 * begin
	 *   with theShell do
	 *   begin
	 *     DGradient := 1;
	 *     W := WOpening;
	 *     D := DDisplacement;
	 *     Inc := Coarsegraininess;
	 *     start := reach * 360;
	 *     rad := 100;
	 * end;
	 *   theSize := 0.74;
	 *   denom := 136 * theSize;
	 *   mn.x := (-(100 / denom) * where.h); mn.y := (-(100 / denom) * where.v);
	 * {the smaller or more negative the number,the lower down the page} {mxy :=
	 * 90;}
	 * 
	 * V1.x := 0; v1.y := 0; U1.x := 0; U1.y := 0; PI := 3.14159; m := start;
	 * repeat p := (start - (start - m) * (1 - theShell.DGradient)) / start; D
	 * := theShell.DDisplacement * p; i := m / 360; BD := 2 * PI * I; WI := RAD
	 * * EXP(-I * LN(W)); V2.x := theShell.handedness * (-WI * COS(BD)); V2.y :=
	 * WI * SIN(BD); U2.x := V2.x * D; U2.y := V2.y * D; p1 := v1; p2 := v2;
	 * EnlargeMarginAndDoPicLine(margin, myPic, p1, p2, mn); p1 := p2; p2 := u2;
	 * if m <= 0 then EnlargeMarginAndDoPicLine(margin, myPic, p1, p2, mn); p1
	 * := p2; p2 := u1; EnlargeMarginAndDoPicLine(margin, myPic, p1, p2, mn); v1
	 * := v2; u1 := u2; m := m - Inc; until m < 0; end; }
	 * 
	 * @param genome
	 *            the SnailGenome
	 * @param pic
	 *            the Snail's phenotype
	 */
	private void drawTop(SnailGenome genome, SnailPic pic) {
		DoubleGene dGradient = genome.getGradient();
		dGradient.setValue(1);

		logger.info("SnailEmbryology.drawTop(" + genome.toString() + ")");
		double w = genome.getOpening().getValue();

		DoubleGene dDisplacement = genome.getDisplacement();
		
		int inc = genome.getCoarsegraininess().getValue();
		int start = genome.getReach().getValue() * 360;
		double rad = 100;



		double theSize = 0.74;
		double denom = 136 * theSize;
		// mn.x = (-(100 / denom) * where.h);
		// mn.y = (-(100 / denom) * where.v);
		// {the smaller or more negative the number,the lower down the page}
		// {mxy := 90;}

		RealPoint v1 = new RealPoint(0, 0);
		RealPoint u1 = new RealPoint(0, 0);

		// PI := 3.14159;
		int m = start;
		int handedness = genome.getHandedness().getValue() == HandednessType.Left ? -1 : 1;
		do {
			logger.fine("Top of drawTop loop");
			double p = (start - (start - m) * (1 - dGradient.getValue())) / start;
			double d = dDisplacement.getValue() * p;
			double i = m / 360;
			double bd = 2 * Math.PI * i;
			double wi = rad * Math.exp(-i * Math.log(w));
			
			RealPoint v2 = new RealPoint(handedness * (-wi * Math.cos(bd)), wi * Math.sin(bd));
			RealPoint u2 = v2.scale(d);
			
			RealPoint p1 = v1.copy();
			RealPoint p2 = v2.copy();
			enlargeMarginAndDoPicLine(pic, p1, p2);
			p1 = p2.copy();
			p2 = u2.copy();
			if (m <= 0) {
				enlargeMarginAndDoPicLine(pic, p1, p2);
			}
			p1 = p2.copy();
			p2 = u1.copy();
			enlargeMarginAndDoPicLine(pic, p1, p2);
			v1 = v2.copy();
			u1 = u2.copy();
			m -=  inc;
		} while (!(m < 0));
	}

	private void enlargeMarginAndDoPicLine(SnailPic pic, RealPoint p1, RealPoint p2) {
		Point startPt = p1.toPoint();
		Point endPt = p2.toPoint();
		Lin lin = new MonoLin(startPt, endPt, 1);
		logger.fine("Snail adding lin:" + lin);
		pic.addLin(lin);
		
	}

	private void drawShell(SnailGenome genome, SnailPic pic) {
		// TODO Auto-generated method stub

	}

}
