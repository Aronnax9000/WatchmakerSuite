package net.richarddawkins.watchmaker.morphs.concho.embryo;

import java.util.logging.Logger;

import net.richarddawkins.watchmaker.embryo.SimpleEmbryology;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphs.concho.genome.DoubleGene;
import net.richarddawkins.watchmaker.morphs.concho.genome.SnailGenome;
import net.richarddawkins.watchmaker.morphs.concho.genome.type.HandednessType;
import net.richarddawkins.watchmaker.morphs.concho.geom.RealPoint;
import net.richarddawkins.watchmaker.morphs.concho.geom.SnailPic;
import net.richarddawkins.watchmaker.morphs.mono.geom.Lin;
import net.richarddawkins.watchmaker.morphs.mono.geom.MonoLin;

public class SnailEmbryology extends SimpleEmbryology {
	private static Logger logger = Logger
			.getLogger("net.richarddawkins.watchmaker.morphs.concho.embryo.SnailEmbryology");


	public SnailEmbryology() {
		prefs = new SnailEmbryologyPreferences();
		
	}

	public void develop(Morph morph) {
		super.develop(morph);
		
		SnailGenome genome = (SnailGenome) morph.getGenome();
		SnailPic pic = (SnailPic) morph.getPhenotype();
		SnailEmbryologyPreferences snailPrefs = (SnailEmbryologyPreferences) prefs;
		pic.setSideView(snailPrefs.isSideView());
		if (snailPrefs.isSideView()) {
			drawShell(genome, pic, false);
		} else {
			drawTop(genome, pic);
		}
	}

	/**
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
			// p := (start - (start - m) * (1 - theShell.DGradient)) / start;
			double p = (start - (start - m) * (1 - dGradient.getValue())) / start;
			// D := theShell.DDisplacement * p;
			double d = dDisplacement.getValue() * p;
			// i := m / 360;
			double i = (double) m / 360;
			// BD := 2 * PI * I;
			double bd = 2 * Math.PI * i;
			// WI := RAD * EXP(-I * LN(W));
			double wi = rad * Math.exp(-i * Math.log(w));

			// V2.x := theShell.handedness * (-WI * COS(BD));
			// V2.y := WI * SIN(BD);
			RealPoint v2 = new RealPoint(handedness * (-wi * Math.cos(bd)), wi * Math.sin(bd));
			logger.fine("WI:" + wi + " BD:" + bd + " -> y:" + wi*Math.sin(bd));

			// U2.x := V2.x * D;
			// U2.y := V2.y * D;
			RealPoint u2 = v2.scale(d);

			// p1 := v1;
			RealPoint p1 = v1.copy();

			// p2 := v2;
			RealPoint p2 = v2.copy();

			// EnlargeMarginAndDoPicLine(margin, myPic, p1, p2, mn);
			enlargeMarginAndDoPicLine(pic, p1, p2);

			// p1 := p2;
			p1 = p2.copy();

			// p2 := u2;
			p2 = u2.copy();

			// if m <= 0 then
			if (m <= 0) {
				// EnlargeMarginAndDoPicLine(margin, myPic, p1, p2, mn);
				enlargeMarginAndDoPicLine(pic, p1, p2);
			}
			// p1 := p2;
			p1 = p2.copy();
			// p2 := u1;
			p2 = u1.copy();
			// EnlargeMarginAndDoPicLine(margin, myPic, p1, p2, mn);
			enlargeMarginAndDoPicLine(pic, p1, p2);
			// v1 := v2;
			v1 = v2.copy();
			// u1 := u2;
			u1 = u2.copy();
			// m := m - Inc;
			m -= inc;
		} while (!(m < 0));
	}

	private void enlargeMarginAndDoPicLine(SnailPic pic, RealPoint p1, RealPoint p2) {
		Point startPt = p1.toPoint();
		Point endPt = p2.toPoint();
		Lin lin = new MonoLin(startPt, endPt, 1);
		logger.fine("Snail adding lin:" + lin);
		pic.addLin(lin);

	}

	private void drawShell(SnailGenome genome, SnailPic pic, boolean dontDraw) {

		double theSize = 0.8; 
		double rad = 100;
		double twoPi = Math.PI * 2;
      
		double w = genome.getOpening().getValue();
		double d = genome.getDisplacement().getValue();
		double s = genome.getShape().getValue();
	    int inc = genome.getCoarsegraininess().getValue();
	    if(inc < 1) {
	    	inc = 1;
	    }
	    int reach = genome.getReach().getValue();
	    if(reach < 1) {
	      reach = 1;
	    }
	    int start = reach * 360;  
	    double rad1 = 1.088 * (rad + rad * d) / 2;
	    double rad2 = 1.088 * (rad - rad * d) / 2;

	    int m = start; 
		int handedness = genome.getHandedness().getValue() == HandednessType.Left ? -1 : 1;


		Rect theRect;
		double translation = genome.getTranslation().getValue();
		double translationGradient = genome.getTranslationGradient().getValue();
		do {
		    double p = (start - (start - m) * (1 - translationGradient)) / start;
		    double t = translation * p;
		    double i = (double) m / 360;
		    double fw = Math.exp(-i * Math.log(w));
		    double grunge = fw * Math.cos(twoPi * i);

		    double xc = handedness * (rad1 * grunge);
		    double yc = -rad1 * t * (1 - fw);
		    double xr = handedness * (rad2 * grunge);
		    double yr = -rad2 * fw * s;
		    // the minus signs are to invert the whole snail
		    double left = xc - xr;
		    double top = yc + yr;
	
		    double right = xc + xr; 
		    double bottom = yc - yr;
		    Point startPt = new RealPoint(left, top).toPoint();
		    Point endPt = new RealPoint(right, bottom).toPoint();
		    if(! dontDraw) {
		    	Lin lin = new MonoLin(startPt, endPt, 1);
				logger.fine("Snail adding lin:" + lin);
				pic.addLin(lin);
		    }
		    m -= inc;
		} while (!(m < 0));
	
	}

}
