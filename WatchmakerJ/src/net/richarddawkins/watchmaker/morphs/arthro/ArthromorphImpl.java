package net.richarddawkins.watchmaker.morphs.arthro;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import net.richarddawkins.watchmaker.morphs.Morph;
import net.richarddawkins.watchmaker.morphs.MorphConfig;
import net.richarddawkins.watchmaker.morphs.Genome;
import net.richarddawkins.watchmaker.morphs.SimpleMorphImpl;
public class ArthromorphImpl extends SimpleMorphImpl implements Arthromorph, Cloneable {
	
	ArthromorphImpl() {	}
	
	public ArthromorphImpl(MorphConfig config, int basicType) {
		this.config = config;
		ArthromorphGenome newGenome = new ArthromorphGenome(this);
		this.genome = newGenome;
		genome.setBasicType(basicType);
	}

	public ArthromorphImpl(MorphConfig config) {
		this.config = config;
	}
	@Override
	public void draw(Graphics2D g2, Dimension d, boolean midBox) {
		g2.setColor(Color.BLACK);
//		g2.drawString("Offspring " + this.getOffspringCount(false), 10, 20);
//		g2.drawString(d.getWidth() + "x" + d.getHeight(), 10, 40);
		try {
			((ArthromorphGenome)genome).drawInBox(g2, d, midBox);
		} catch (ArthromorphGradientExceeds1000Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void delayvelop(Graphics2D g2, Dimension d, boolean midBox) {
		
		
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
	

	
}
