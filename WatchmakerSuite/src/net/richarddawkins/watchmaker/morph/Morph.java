package net.richarddawkins.watchmaker.morph;

import java.awt.Graphics2D;
import java.awt.Image;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Pic;

public interface Morph {
	
	public Image getImage();
	
	public void setMorphConfig(MorphConfig config);
	public MorphConfig getMorphConfig();

	public void setGenome(Genome genome);
	public Genome getGenome();

	public void setPic(Pic pic);
	public Pic getPic();

	public Morph reproduce();
	
	public void draw(Graphics2D g2);


    public MorphPedigree getPedigree();

}