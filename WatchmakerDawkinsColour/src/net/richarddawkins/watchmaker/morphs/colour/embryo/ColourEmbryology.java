package net.richarddawkins.watchmaker.morphs.colour.embryo;

import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphs.colour.genome.ColorGene;
import net.richarddawkins.watchmaker.morphs.colour.genome.ColourGenome;
import net.richarddawkins.watchmaker.morphs.colour.genome.LimbFillGene;
import net.richarddawkins.watchmaker.morphs.colour.genome.LimbShapeGene;
import net.richarddawkins.watchmaker.morphs.colour.geom.ColourLin;
import net.richarddawkins.watchmaker.morphs.colour.geom.ColourPic;
import net.richarddawkins.watchmaker.morphs.mono.embryo.BiomorphEmbryology;
import net.richarddawkins.watchmaker.morphs.mono.genome.Gene12345678;
import net.richarddawkins.watchmaker.morphs.mono.genome.Gene9;
import net.richarddawkins.watchmaker.morphs.mono.genome.IntegerGeneOneOrGreater;
import net.richarddawkins.watchmaker.morphs.mono.genome.IntegerGradientGene;
import net.richarddawkins.watchmaker.morphs.mono.genome.SegNoGene;
import net.richarddawkins.watchmaker.morphs.mono.genome.type.SwellType;

public class ColourEmbryology extends BiomorphEmbryology {
    private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morphs.bio.embryo.BiomorphEmbryology");

	protected void tree(ColourGenome genome, ColourPic pic, 
			
			int x, int y, int lgth, int dir, int[] dx, int[] dy, 
			int order, boolean oddOne) {
		logger.info("Tree:" +  lgth  + "/" + order);
		int trickleGene = genome.getTrickleGene().getValue();
		int thickness = genome.getThicknessGene().getValue();
		int gene9 = genome.getGene9().getValue();

		if (dir < 0) {
			dir += 8;
		}
		if (dir >= 8) {
			dir -= 8;
		}
		int xnew = x + lgth * dx[dir] / trickleGene;
		int ynew = y + lgth * dy[dir] / trickleGene;
		
		int subscript = (gene9 - lgth) % 8; // + 1; Trimmed off + 1
														// to make it
		// zero based.
		ColorGene colorGene = null;
		try {
			ColorGene[] colorGenes = genome.getColorGenes();
			colorGene = colorGenes[subscript];
		}
		catch (StackOverflowError soe) {
			logger.warning("Subscript: " + subscript);
		}
		int color = colorGene.getValue();
		ColourLin lin = new ColourLin(new Point(x,y), new Point(xnew,ynew),
				thickness, color);
		logger.info("ColorLin: " + color);
		pic.addLin(lin);
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
	public void develop(Morph morph) {
				super.develop(morph);
    	ColourGenome genome = (ColourGenome) morph.getGenome();
    	ColourPic pic = (ColourPic) morph.getPhenotype();
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
    	IntegerGeneOneOrGreater trickleGene = genome.getTrickleGene();
		

		Point here = new Point(0,0);
	    
	    LimbShapeGene limbShapeGene = genome.getLimbShapeGene();
	    LimbFillGene limbFillGene = genome.getLimbFillGene();
	    IntegerGene thicknessGene = genome.getThicknessGene();
	    ColorGene backColorGene = genome.getBackColorGene();
	    pic.setLimbShape(limbShapeGene.getValue());
	    pic.setLimbFill(limbFillGene.getValue());
	    pic.setThickness(thicknessGene.getValue());
	    int backgroundColor = backColorGene.getValue();
	    pic.setBackgroundColor(backgroundColor);
	    
	    pic.zero();

		int[] dx = new int[8];
		int[] dy = new int[8];
		int[] running = new int[9];
		Point oldHere;
		Point centre;
		// boolean oddOne;
		int extraDistance;
		int incDistance;
		int dummyColour;
		pic.zero();
		centre = (Point) here.clone();
		int order = plugIn(new int[] { gene1.getValue(), gene2.getValue(), gene3.getValue(), gene4.getValue(), gene5.getValue(),
				gene6.getValue(), gene7.getValue(), gene8.getValue(), gene9.getValue() }, dx, dy);
		logger.info("Order:" + order);

		pic.zero();
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
				pic.addLin(new ColourLin(new Point(oldHere.h, oldHere.v), new Point(here.h, here.v), 
						thicknessGene.getValue(), dummyColour));
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
	}

}
