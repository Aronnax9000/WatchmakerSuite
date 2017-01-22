package net.richarddawkins.watchmaker.morphs.colour.genome;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.geom.Pic;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morphs.bio.genome.BiomorphEmbryology;
import net.richarddawkins.watchmaker.morphs.bio.genome.Gene12345678;
import net.richarddawkins.watchmaker.morphs.bio.genome.Gene9;
import net.richarddawkins.watchmaker.morphs.bio.genome.IntegerGeneZeroOrGreater;
import net.richarddawkins.watchmaker.morphs.bio.genome.IntegerGradientGene;
import net.richarddawkins.watchmaker.morphs.bio.genome.SegNoGene;
import net.richarddawkins.watchmaker.morphs.bio.genome.SpokesGene;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SpokesType;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SwellType;
import net.richarddawkins.watchmaker.morphs.colour.geom.ColourPic;
import net.richarddawkins.watchmaker.util.Globals;
import net.richarddawkins.watchmaker.util.ModeType;

public class ColourEmbryology extends BiomorphEmbryology {
	protected void tree(ColourGenome genome, ColourPic pic, int x, int y, int lgth, int dir, int[] dx, int[] dy, int order, boolean oddOne) {
		
		int trickleGene = genome.getTrickleGene().getValue();
		int thicknessGene = genome.getThicknessGene().getValue();
		int gene9 = genome.getGene9().getValue();
		int xnew;
		int ynew;
		if (dir < 0) {
			dir += 8;
		}
		if (dir >= 8) {
			dir -= 8;
		}
		xnew = x + lgth * dx[dir] / trickleGene;
		ynew = y + lgth * dy[dir] / trickleGene;
		pic.margin.left = Math.min(pic.margin.left, x);
		pic.margin.right = Math.max(pic.margin.right, x + thicknessGene);
		pic.margin.bottom = Math.max(pic.margin.bottom, y + thicknessGene);
		pic.margin.top = Math.min(pic.margin.top, y);
		pic.margin.left = Math.min(pic.margin.left, xnew);
		pic.margin.right = Math.max(pic.margin.right, xnew);
		pic.margin.bottom = Math.max(pic.margin.bottom, ynew);
		pic.margin.top = Math.min(pic.margin.top, ynew);
		int subscript = (gene9 - lgth) % 8; // + 1; Trimmed off + 1
														// to make it
		// zero based.
		pic.picLine(x, y, xnew, ynew, 1, genome.getColorGenes()[subscript].getValue());
		if (lgth > 1) {
			if (oddOne) {
				tree(genome, pic, xnew, ynew, lgth - 1, dir + 1, dx, dy, order, oddOne);
				if (lgth < order) {
					tree(genome, pic, xnew, ynew, lgth - 1, dir - 1, dx, dy, order, oddOne);
				}
			} else {
				tree(genome, pic, xnew, ynew, lgth - 1, dir - 1, dx, dy, order, oddOne);
				if (lgth < order) {
					tree(genome, pic, xnew, ynew, lgth - 1, dir + 1, dx, dy, order, oddOne);
				}
			}
		}
	}

	@Override
	public void develop(Genome colourGenome, Pic colourPic) {
		super.develop(colourGenome, colourPic);
    	ColourGenome genome = (ColourGenome) colourGenome;
    	ColourPic pic = (ColourPic) colourPic;
		Gene12345678 gene1 = genome.getGene1();
    	Gene12345678 gene2 = genome.getGene2();
    	Gene12345678 gene3 = genome.getGene3();
    	Gene12345678 gene4 = genome.getGene4();
    	Gene12345678 gene5 = genome.getGene5();
    	Gene12345678 gene6 = genome.getGene6();
    	Gene12345678 gene7 = genome.getGene7();
    	Gene12345678 gene8 = genome.getGene8();
    	Gene9 gene9 = genome.getGene9();
    	SegNoGene segNoGene = genome.getSegNoGene();
    	IntegerGradientGene segDistGene = genome.getSegDistGene();
    	SpokesGene spokesGene = genome.getSpokesGene();
    	IntegerGeneZeroOrGreater trickleGene = genome.getTrickleGene();
		

		Point here = new Point(0,0);
	    
	    LimbShapeGene limbShapeGene = genome.getLimbShapeGene();
	    LimbFillGene limbFillGene = genome.getLimbFillGene();
	    IntegerGene thicknessGene = genome.getThicknessGene();
	    ColorGene backColorGene = genome.getBackColorGene();
	    pic.setLimbShape(limbShapeGene.getValue());
	    pic.setLimbFill(limbFillGene.getValue());
	    pic.setThickness(thicknessGene.getValue());
	    pic.setBackgroundColor(backColorGene.getValue());
	    
	    pic.zeroPic();
		// int x;
		// int y;
		int upExtent;
		int downExtent;
		int wid;
		int ht;
		int[] dx = new int[8];
		int[] dy = new int[8];
		int[] running = new int[9];
		Point oldHere;
		Point centre;
		// boolean oddOne;
		int extraDistance;
		int incDistance;
		int dummyColour;
		pic.margin.zeroRect();
		centre = (Point) here.clone();
		int order = plugIn(new int[] { gene1.getValue(), gene2.getValue(), gene3.getValue(), gene4.getValue(), gene5.getValue(),
				gene6.getValue(), gene7.getValue(), gene8.getValue(), gene9.getValue() }, dx, dy);
		pic.zeroPic();
		if (segNoGene.getValue() < 1) {
			segNoGene.setValue(1);
		}
		switch (segDistGene.getGradient()) {
		case Swell:
			extraDistance = trickleGene.getValue();
			break;
		case Shrink:
			extraDistance = -trickleGene.getValue();
			break;
		case Same:
		default:
			extraDistance = 0;
		}
		running = new int[] { gene1.getValue(), gene2.getValue(), gene3.getValue(), gene4.getValue(), gene5.getValue(),
				gene6.getValue(), gene7.getValue(), gene8.getValue(), gene9.getValue() };
		incDistance = 0;
		SwellType[] dGene = new SwellType[] { gene1.getGradient(), gene2.getGradient(), gene3.getGradient(),
				gene4.getGradient(), gene5.getGradient(), gene6.getGradient(), gene7.getGradient(), gene8.getGradient(),
				gene9.getGradient() };
		for (int seg = 0; seg < segNoGene.getValue(); seg++) {
			boolean oddOne = (seg & 1) == 1;
			if (seg > 0) {
				oldHere = (Point) here.clone();
				here.v += (segDistGene.getValue() + incDistance) / trickleGene.getValue();
				incDistance += extraDistance;
				dummyColour = 100;
				pic.picLine(oldHere.h, oldHere.v, here.h, here.v, 1, dummyColour);
				for (int j = 0; j < 8; j++) {
					if (dGene[j] == SwellType.Swell) {
						running[j] += trickleGene.getValue();
					}
					if (dGene[j] == SwellType.Shrink) {
						running[j] -= trickleGene.getValue();
					}
				}
				if (running[8] < 1) {
					running[8] = 1;
				}
				plugIn(running, dx, dy);
			}
			tree(genome, pic, here.h, here.v, order, 2, dx, dy, order, oddOne);
		}
		if (centre.h - pic.margin.left > pic.margin.right - centre.h) {
			pic.margin.right = centre.h + (centre.h - pic.margin.left);
		} else {
			pic.margin.left = centre.h - (pic.margin.right - centre.h);
		}
		upExtent = centre.v - pic.margin.top;
		downExtent = pic.margin.bottom - centre.v;

		if (spokesGene.getValue() == SpokesType.NSouth || spokesGene.getValue() == SpokesType.Radial
				|| Globals.theMode == ModeType.Engineering) {
			// {Obscurely necessary to cope with erasing last rect in
			// Manipulation}
			if (upExtent > downExtent) {
				pic.margin.bottom = centre.v + upExtent;
			} else {
				pic.margin.top = centre.v - downExtent;
			}
		}
		if (spokesGene.getValue() == SpokesType.Radial) {
			wid = pic.margin.right - pic.margin.left;
			ht = pic.margin.bottom - pic.margin.top;
			if (wid > ht) {
				pic.margin.top = centre.v - wid / 2 - 1;
				pic.margin.bottom = centre.v + wid / 2 + 1;
			} else {
				pic.margin.left = centre.h - ht / 2 - 1;
				pic.margin.right = centre.h + ht / 2 + 1;
			}
		}

	}

}
