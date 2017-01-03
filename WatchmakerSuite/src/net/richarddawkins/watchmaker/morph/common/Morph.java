package net.richarddawkins.watchmaker.morph.common;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Vector;

import net.richarddawkins.watchmaker.draw.DrawingPrimitive;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.common.geom.Pic;

public interface Morph {
	public void generatePrimitives(Vector<DrawingPrimitive> primitives, Point centre);

	public Morph reproduce();
	
	public void setMorphConfig(MorphConfig config);
	public MorphConfig getMorphConfig();
	public Morph getParent();

	public int getOffspringCount(boolean deep);
	
	public void setParent(Morph parent);
	public Morph getFirstBorn();
	public void setFirstBorn(Morph firstBorn);
	public Morph getLastBorn();

	public void setLastBorn(Morph lastBorn);

	public Morph getElderSib();
	public void setElderSib(Morph elderSib);

	public Morph getYoungerSib();

	public void setYoungerSib(Morph youngerSib);
	public Morph getPrec();

	public void setPrec(Morph prec);
	public Morph getNext();

	public void setNext(Morph next);


	public void setPic(Pic pic);
	public Pic getPic();
	public Genome getGenome();
	public void setGenome(Genome genome);

	public void draw(Graphics2D g2, Dimension size, boolean midBox);
}