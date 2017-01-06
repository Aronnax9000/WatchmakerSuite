package net.richarddawkins.watchmaker.morph.simple;

import java.awt.Point;
import java.util.Vector;

import net.richarddawkins.watchmaker.draw.DrawingPrimitive;
import net.richarddawkins.watchmaker.draw.LineTo;
import net.richarddawkins.watchmaker.draw.MoveTo;
import net.richarddawkins.watchmaker.morph.Morph;

public class StalkedOvalMorph extends OvalMorph {
	@Override
	public void generatePrimitives(Vector<DrawingPrimitive> primitives, Point centre) {
		super.generatePrimitives(primitives, centre);
		int stalkX = genome.origin.x + genome.dimension.width / 2;
		int stalkY = (int) (genome.origin.y + genome.dimension.height);
		
		primitives.add(new MoveTo(stalkX, stalkY));
		primitives.add(new LineTo(stalkX, (int) (stalkY + genome.dimension.height * 1.5)));
		primitives.add(new LineTo(stalkX + genome.dimension.width * 3, stalkY + genome.dimension.height));
	}
	
	public Morph reproduce() {
		StalkedOvalMorph newMorph = new StalkedOvalMorph();
		newMorph.genome = (OvalMorphGenome) genome.clone();
		return newMorph;
	}

}
