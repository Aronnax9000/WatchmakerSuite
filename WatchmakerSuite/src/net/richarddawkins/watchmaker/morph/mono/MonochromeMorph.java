package net.richarddawkins.watchmaker.morph.mono;


import java.awt.Rectangle;
import java.util.Vector;

import net.richarddawkins.watchmaker.draw.DrawingPrimitive;
import net.richarddawkins.watchmaker.morph.common.Morph;
import net.richarddawkins.watchmaker.morph.common.MorphConfig;
import net.richarddawkins.watchmaker.morph.common.SimpleMorphImpl;
import net.richarddawkins.watchmaker.morph.common.geom.Point;
public class MonochromeMorph extends SimpleMorphImpl {
	
	MonochromeMorph() {
		setGenome(new MonochromeGenome(this));
		pic = new MonoPic();
	}
	MonochromeMorph(MorphConfig config) {
		this();
		setMorphConfig(config);
	}

	MonochromeMorph(MorphConfig config, int basicType) {
		this(config);
		genome.setBasicType(basicType);
	}

	public Morph reproduce() {
		MonochromeMorph child = new MonochromeMorph(config);
		child.setGenome(genome.reproduce(child));
		child.setParent(this);
		return child;
	}
	
	Rectangle surround;
	Point origin;
	Point centre;

	boolean damaged; // ,Blackened
	Object snapHandle;
	int snapBytes;
	Rectangle snapBounds;

	@Override
	public void generatePrimitives(Vector<DrawingPrimitive> primitives, java.awt.Point centre) {
		((MonochromeGenome) genome).generatePic();
		pic.generatePrimitives(primitives, genome);
		
	}

}
