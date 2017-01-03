package net.richarddawkins.watchmaker.morphs;

import java.awt.Dimension;
import java.awt.Graphics2D;

public interface Morph {
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

	public void draw(Graphics2D g2, Dimension d, boolean midBox);

	public void delayvelop(Graphics2D g2, Dimension d, boolean midBox);
	public Morph reproduce();
	public void setPic(Pic pic);
	public Pic getPic();
	public Genome getGenome();
}