package net.richarddawkins.watchmaker.morph.colour.genome;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.biomorph.Biomorph;
import net.richarddawkins.watchmaker.morph.biomorph.genome.IntegerGeneOneOrGreater;
import net.richarddawkins.watchmaker.morph.biomorph.genome.type.SpokesType;
import net.richarddawkins.watchmaker.morph.biomorph.genome.type.SwellType;
import net.richarddawkins.watchmaker.morph.colour.genome.type.LimbFillType;
import net.richarddawkins.watchmaker.morph.colour.genome.type.LimbShapeType;
import net.richarddawkins.watchmaker.morph.colour.geom.ColourPic;
import net.richarddawkins.watchmaker.morph.common.geom.Pic;
import net.richarddawkins.watchmaker.morph.common.geom.Point;
import net.richarddawkins.watchmaker.morph.mono.genome.MonochromeGenome;
import net.richarddawkins.watchmaker.morph.util.Globals;
import net.richarddawkins.watchmaker.morph.util.ModeType;

public class ColourGenome extends MonochromeGenome {

	public static final int RAINBOW = 256;

	protected final ColorGene backColorGene = new ColorGene(this, "Background Color", RAINBOW / 3);
	protected final ColorGene colorGene1 = new ColorGene(this, "Color Gene 1");
	protected final ColorGene colorGene2 = new ColorGene(this, "Color Gene 2");
	protected final ColorGene colorGene3 = new ColorGene(this, "Color Gene 3");
	protected final ColorGene colorGene4 = new ColorGene(this, "Color Gene 4");
	protected final ColorGene colorGene5 = new ColorGene(this, "Color Gene 5");
	protected final ColorGene colorGene6 = new ColorGene(this, "Color Gene 6");
	protected final ColorGene colorGene7 = new ColorGene(this, "Color Gene 7");
	protected final ColorGene colorGene8 = new ColorGene(this, "Color Gene 8");
	protected final LimbShapeGene limbShapeGene = new LimbShapeGene(this, "Limb Shape", LimbShapeType.Stick);
	protected final LimbFillGene limbFillGene = new LimbFillGene(this, "Limb Fill", LimbFillType.Filled);
	protected final IntegerGeneOneOrGreater thicknessGene = new IntegerGeneOneOrGreater(this, "Thickness", 1);

	int thick;

	public ColourGenome(Morph morph) {
		super(morph);
	}

	public void addToBackColorGene(int summand) {

		int newValue = backColorGene.getValue() + summand;
		if (newValue > RAINBOW - 1) {
			newValue = RAINBOW - 1;
		}
		if (newValue < 0) {
			newValue = 0;
		}
		backColorGene.setValue(newValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.richarddawkins.watchmaker.morphs.colour.impl.ColourBiomorph#basicTree
	 * ()
	 */
	@Override
	public void basicTree() {
		makeGenes(-Biomorph.TRICKLE, -Biomorph.TRICKLE, -Biomorph.TRICKLE, -Biomorph.TRICKLE, -Biomorph.TRICKLE, 0,
				Biomorph.TRICKLE, Biomorph.TRICKLE, 6);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.richarddawkins.watchmaker.morphs.colour.impl.ColourBiomorph#chess()
	 */
	@Override
	public void chess() {

		makeGenes(-Biomorph.TRICKLE, 3 * Biomorph.TRICKLE, -3 * Biomorph.TRICKLE, -3 * Biomorph.TRICKLE,
				Biomorph.TRICKLE, -2 * Biomorph.TRICKLE, 6 * Biomorph.TRICKLE, -5 * Biomorph.TRICKLE, 7);
	}

	@Override
	public void copy(Genome person) {
		ColourGenome child = (ColourGenome) person;
		super.copy(child);
		child.colorGene1.setValue(colorGene1.getValue());
		child.colorGene2.setValue(colorGene2.getValue());
		child.colorGene3.setValue(colorGene3.getValue());
		child.colorGene4.setValue(colorGene4.getValue());
		child.colorGene5.setValue(colorGene5.getValue());
		child.colorGene6.setValue(colorGene6.getValue());
		child.colorGene7.setValue(colorGene7.getValue());
		child.colorGene8.setValue(colorGene8.getValue());
		child.backColorGene.setValue(backColorGene.getValue());
		child.limbShapeGene.setValue(limbShapeGene.getValue());
		child.limbFillGene.setValue(limbFillGene.getValue());
		child.thicknessGene.setValue(thicknessGene.getValue());
	}

	public void develop(Graphics2D g2, Dimension d, boolean zeroMargin) {
		Point here = new Point(d.width / 2, d.height / 2);

		Pic pic = this.morph.getPic();
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
		if (zeroMargin) {
			pic.margin.left = here.h;
			pic.margin.right = here.h;
			pic.margin.top = here.v;
			pic.margin.bottom = here.v;
		}
		centre = (Point) here.clone();
		plugIn(new int[] { gene1.getValue(), gene2.getValue(), gene3.getValue(), gene4.getValue(), gene5.getValue(),
				gene6.getValue(), gene7.getValue(), gene8.getValue(), gene9.getValue() }, dx, dy);
		pic.zeroPic(here);
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
			oddOne = (seg & 1) == 1;
			if (seg > 0) {
				oldHere = (Point) here.clone();
				here.v += (segDistGene.getValue() + incDistance) / trickleGene.getValue();
				incDistance += extraDistance;
				dummyColour = 100;
				pic.picLine(oldHere.h, oldHere.v, here.h, here.v, 1, ColourPic.chooseColor(dummyColour));
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
			tree(here.h, here.v, order, 2, dx, dy);
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
		pic.morph = this.morph;
		if (g2 != null) {
			pic.drawPic(g2, d, centre, morph);
			if (morph.getMorphConfig().isShowBoundingBoxes()) {
				g2.setColor(Color.RED);
				Rectangle rectangle = pic.margin.toRectangle();
				g2.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
			}
		}
	}

	public IntegerGene getBackColorGene() {
		return backColorGene;
	}

	public ColorGene[] getColorGenes() {
		return new ColorGene[] { colorGene1, colorGene2, colorGene3, colorGene4, colorGene5, colorGene6, colorGene7,
				colorGene8 };
	}

	public LimbFillGene getLimbFillGene() {
		return limbFillGene;
	}

	public LimbShapeGene getLimbShapeGene() {
		return limbShapeGene;
	}

	public IntegerGene getThicknessGene() {
		return thicknessGene;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.richarddawkins.watchmaker.morphs.colour.impl.ColourBiomorph#insect()
	 */
	@Override
	public void insect() {
		makeGenes(Biomorph.TRICKLE, Biomorph.TRICKLE, -4 * Biomorph.TRICKLE, Biomorph.TRICKLE, -Biomorph.TRICKLE,
				-2 * Biomorph.TRICKLE, 8 * Biomorph.TRICKLE, -4 * Biomorph.TRICKLE, 6);

	}

	public void makeGenes(int a, int b, int c, int d, int e, int f, int g, int h, int i) {
		super.makeGenes(a, b, c, d, e, f, g, h, i);
		segDistGene.setValue(1);
	}

	protected void plugIn(int[] gene, int[] dx, int[] dy) {
		order = gene[8];
		dx[3] = gene[0];
		dx[4] = gene[1];
		dx[5] = gene[2];
		dy[2] = gene[3];
		dy[3] = gene[4];
		dy[4] = gene[5];
		dy[5] = gene[6];
		dy[6] = gene[7];
		dx[1] = -dx[3];
		dy[1] = dy[3];
		dx[0] = -dx[4];
		dy[0] = dy[4];
		dx[7] = -dx[5];
		dy[7] = dy[5];
		dx[2] = 0;
		dx[6] = 0;
	}

	public Genome reproduce(Morph newMorph) {
		ColourGenome child = new ColourGenome(newMorph);
		copy(child);
		newMorph.getMorphConfig().getMutagen().mutate(child);
		return child;
	}

	@Override
	public Gene[] toGeneArray() {
		Gene[] theGenes = new Gene[28];

		Gene[] theMonochromeGenes = super.toGeneArray();
		for (int i = 0; i < 16; i++) {
			theGenes[i] = theMonochromeGenes[i];
		}
		theGenes[16] = colorGene1;
		theGenes[17] = colorGene2;
		theGenes[18] = colorGene3;
		theGenes[19] = colorGene4;
		theGenes[20] = colorGene5;
		theGenes[21] = colorGene6;
		theGenes[22] = colorGene7;
		theGenes[23] = colorGene8;
		theGenes[24] = backColorGene;
		theGenes[25] = limbShapeGene;
		theGenes[26] = limbFillGene;
		theGenes[27] = thicknessGene;
		return theGenes;
	}

	protected void tree(int x, int y, int lgth, int dir, int[] dx, int[] dy) {
		ColourPic pic = (ColourPic) morph.getPic();
		int xnew;
		int ynew;
		if (dir < 0) {
			dir += 8;
		}
		if (dir >= 8) {
			dir -= 8;
		}
		xnew = x + lgth * dx[dir] / trickleGene.getValue();
		ynew = y + lgth * dy[dir] / trickleGene.getValue();
		pic.margin.left = Math.min(pic.margin.left, x);
		pic.margin.right = Math.max(pic.margin.right, x + thicknessGene.getValue());
		pic.margin.bottom = Math.max(pic.margin.bottom, y + thicknessGene.getValue());
		pic.margin.top = Math.min(pic.margin.top, y);
		pic.margin.left = Math.min(pic.margin.left, xnew);
		pic.margin.right = Math.max(pic.margin.right, xnew);
		pic.margin.bottom = Math.max(pic.margin.bottom, ynew);
		pic.margin.top = Math.min(pic.margin.top, ynew);
		int subscript = (gene9.getValue() - lgth) % 8; // + 1; Trimmed off + 1
														// to make it
		// zero based.
		pic.picLine(x, y, xnew, ynew, 1, ColourPic.chooseColor(this.getColorGenes()[subscript].getValue()));
		if (lgth > 1) {
			if (oddOne) {
				tree(xnew, ynew, lgth - 1, dir + 1, dx, dy);
				if (lgth < order) {
					tree(xnew, ynew, lgth - 1, dir - 1, dx, dy);
				}
			} else {
				tree(xnew, ynew, lgth - 1, dir - 1, dx, dy);
				if (lgth < order) {
					tree(xnew, ynew, lgth - 1, dir + 1, dx, dy);
				}
			}
		}
	}

}
