package net.richarddawkins.watchmaker.morph.simple;

import static net.richarddawkins.watchmaker.morph.util.Random.nextBoolean;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;
import java.util.Vector;

import net.richarddawkins.watchmaker.draw.DrawingPrimitive;
import net.richarddawkins.watchmaker.draw.FrameOval;
import net.richarddawkins.watchmaker.draw.SetColor;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.SimpleMorph;
public class OvalMorph extends SimpleMorph implements Morph {
    public void setGenome(Genome genome) {
		this.genome = (OvalMorphGenome) genome;
	}

	OvalMorphGenome genome = new OvalMorphGenome();
    public static Random random = new Random();
	@Override
	public void generatePrimitives(Vector<DrawingPrimitive> primitives, Point centre) {
		primitives.removeAllElements();
		primitives.add(new SetColor(genome.color));
		primitives.add(new FrameOval(genome.origin.x, genome.origin.y, 
				genome.dimension.width, genome.dimension.height));
	}
	
	public Morph reproduce() {
		OvalMorph newMorph = new OvalMorph();
		newMorph.genome = (OvalMorphGenome) genome.clone();
		return newMorph.mutate();
	}
	
	
	public Morph mutate() {
		double delta;
		if(nextBoolean()) {
			if(nextBoolean()) {
				delta = 0.8;
			} else {
				delta = 1.2;
			} 
			if(nextBoolean()) {
				genome.dimension.width *= delta;
			} else {
				genome.dimension.height *= delta;
			}
		} else {
			if(genome.color == Color.RED) {
				genome.color = Color.BLUE;
			} else if(genome.color == Color.BLUE) {
				genome.color = Color.GREEN;
			} else {
				genome.color = Color.RED;
			}
		}
		return this;
	}
	protected OvalMorphConfig config;
	@Override
	public void setMorphConfig(MorphConfig config) {
		this.config = (OvalMorphConfig) config;
		
	}

	@Override
	public MorphConfig getMorphConfig() {
		
		return config;
	}

	@Override
	public Genome getGenome() {
		
		return genome;
	}


}
