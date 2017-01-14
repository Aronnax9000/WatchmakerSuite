package net.richarddawkins.watchmaker.morph;

import static net.richarddawkins.watchmaker.morph.util.Random.randInt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Pic;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Point;

public abstract class SimpleMorph implements Morph {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morph.SimpleMorph");


	Morph elderSib;

	Morph firstBorn;

	protected Genome genome;

	Image image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	Morph lastBorn;
	public int mutSizeGene;
	Morph next;
	Morph parent;
	protected Pic pic;
	Morph prec;

	Morph youngerSib;

	public void delayvelop(Graphics2D g2, Dimension d, boolean midBox) {
		int margcentre, offset;
		Point offCentre = new Point();
		// Zeromargin := TRUE;
		Point p = new Point();
		p.h = d.width / 2;
		p.v = d.height / 2;

		getGenome().develop(null, d, true); // null equivalent to classic
											// DelayedDrawing := TRUE;
		// DelayedDrawing := FALSE;
		margcentre = pic.margin.top + (pic.margin.bottom - pic.margin.top) / 2;
		offset = margcentre - p.v;
		pic.margin.top -= offset;
		pic.margin.bottom -= offset;
		offCentre.h = p.h;
		offCentre.v = p.v - offset;
		pic.drawPic(g2, d, offCentre, this);
		if (this.getMorphConfig().isShowBoundingBoxes()) {
			g2.setColor(Color.RED);
			Rectangle rectangle = pic.margin.toRectangle();
			g2.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		}
	}

	protected int direction() {
		return randInt(2) == 2 ? mutSizeGene : -mutSizeGene;
	}

	@Override
	public void draw(Graphics2D g2, Dimension d, boolean midBox) {
		g2.setColor(Color.BLACK);
		// g2.drawString("Offspring " + this.getOffspringCount(false), 10, 20);
		// g2.drawString(d.getWidth() + "x" + d.getHeight(), 10, 40);
		delayvelop(g2, d, midBox);
	}

	public Morph getElderSib() {
		return elderSib;
	}

	public Morph getFirstBorn() {
		return firstBorn;
	}

	@Override
	public Genome getGenome() {
		return genome;
	}

	public Morph getLastBorn() {
		return lastBorn;
	}

	public Morph getNext() {
		return next;
	}

	public int getOffspringCount(boolean deep) {
		int count = 0;
		Morph child = firstBorn;
		while (child != null) {
			count++;
			if (deep)
				count += child.getOffspringCount(true);
			child = child.getYoungerSib();
		}

		return count;
	}

	public Morph getParent() {
		return parent;
	}

	public Pic getPic() {
		return pic;
	}

	public Morph getPrec() {
		return prec;
	}

	public Morph getYoungerSib() {
		return youngerSib;
	}

	public void setElderSib(Morph elderSib) {
		this.elderSib = elderSib;
	}

	public void setFirstBorn(Morph firstBorn) {
		this.firstBorn = firstBorn;
	}

	@Override
	public void setGenome(Genome genome) {
		this.genome = genome;
	}

	public void setLastBorn(Morph lastBorn) {
		this.lastBorn = lastBorn;
	}

	public void setNext(Morph next) {
		this.next = next;
	}

	public void setParent(Morph parent) {
		this.parent = parent;
		if (parent.getFirstBorn() == null)
			parent.setFirstBorn(this);
		else {
			parent.getLastBorn().setYoungerSib(this);
			this.elderSib = lastBorn;
		}
		this.parent.setLastBorn(this);
	}

	public void setPic(Pic pic) {
		this.pic = pic;
	}

	public void setPrec(Morph prec) {
		this.prec = prec;
	}

	public void setYoungerSib(Morph youngerSib) {
		this.youngerSib = youngerSib;
	}
}
