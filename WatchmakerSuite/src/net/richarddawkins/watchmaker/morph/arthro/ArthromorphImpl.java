package net.richarddawkins.watchmaker.morph.arthro;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Vector;

import net.richarddawkins.watchmaker.draw.DrawingPrimitive;
import net.richarddawkins.watchmaker.morph.common.Genome;
import net.richarddawkins.watchmaker.morph.common.Morph;
import net.richarddawkins.watchmaker.morph.common.MorphConfig;
import net.richarddawkins.watchmaker.morph.common.SimpleMorphImpl;
public class ArthromorphImpl extends SimpleMorphImpl implements Arthromorph, Cloneable {
	
	ArthromorphImpl() {	}
	
	public ArthromorphImpl(MorphConfig config, int basicType) {
		this.config = config;
		ArthromorphPerson newGenome = new ArthromorphPersonImpl(this);
		this.genome = newGenome;
		genome.setBasicType(basicType);
	}

	public ArthromorphImpl(MorphConfig config) {
		this.config = config;
	}
	
	public void draw(Graphics2D g2, Dimension d, boolean midBox) {
		g2.setColor(Color.BLACK);
//		g2.drawString("Offspring " + this.getOffspringCount(false), 10, 20);
//		g2.drawString(d.getWidth() + "x" + d.getHeight(), 10, 40);
		try {
			((ArthromorphPerson)genome).drawInBox(g2, d, midBox);
		} catch (ArthromorphGradientExceeds1000Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


	@Override
	public Morph reproduce() {
		ArthromorphImpl child = new ArthromorphImpl(config);
		child.setGenome(genome.reproduce(child));
		child.setParent(this);
		return child;
	}
	
	@Override
	public Genome getGenome() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Morph mutate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generatePrimitives(Vector<DrawingPrimitive> primitives, Point centre) {
		// TODO Auto-generated method stub
		
	}
	

	
}
