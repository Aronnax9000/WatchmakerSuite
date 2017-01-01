package net.richarddawkins.watchmaker.morph.simple;

import static net.richarddawkins.watchmaker.morph.util.Random.nextBoolean;

import java.awt.Color;
import java.awt.Point;
import java.util.Vector;

import net.richarddawkins.watchmaker.draw.DrawingPrimitive;
import net.richarddawkins.watchmaker.draw.FrameOval;
import net.richarddawkins.watchmaker.draw.SetColor;
import net.richarddawkins.watchmaker.morph.common.Genome;
import net.richarddawkins.watchmaker.morph.common.Morph;
import net.richarddawkins.watchmaker.morph.common.MorphConfig;
import net.richarddawkins.watchmaker.morph.common.SimpleMorphImpl;
import net.richarddawkins.watchmaker.morph.common.geom.Pic;
public class OvalMorph extends SimpleMorphImpl implements Morph {
    OvalMorphGenome genome = new OvalMorphGenome();

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

	@Override
	public void setMorphConfig(MorphConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MorphConfig getMorphConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Morph getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getOffspringCount(boolean deep) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setParent(Morph parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Morph getFirstBorn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFirstBorn(Morph firstBorn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Morph getLastBorn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastBorn(Morph lastBorn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Morph getElderSib() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setElderSib(Morph elderSib) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Morph getYoungerSib() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setYoungerSib(Morph youngerSib) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Morph getPrec() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPrec(Morph prec) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Morph getNext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNext(Morph next) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPic(Pic pic) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pic getPic() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Genome getGenome() {
		// TODO Auto-generated method stub
		return null;
	}

}