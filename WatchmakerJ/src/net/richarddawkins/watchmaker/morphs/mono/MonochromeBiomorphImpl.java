package net.richarddawkins.watchmaker.morphs.mono;


import java.awt.Rectangle;

import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morphs.Morph;
import net.richarddawkins.watchmaker.morphs.MorphConfig;
import net.richarddawkins.watchmaker.morphs.SimpleMorphImpl;
public class MonochromeBiomorphImpl extends SimpleMorphImpl implements MonochromeBiomorph {
	
	MonochromeBiomorphImpl() {
		setGenome(new MonochromeGenome(this));
		pic = new MonoPic();
	}
	MonochromeBiomorphImpl(MorphConfig config) {
		this();
		setMorphConfig(config);
	}

	MonochromeBiomorphImpl(MorphConfig config, int basicType) {
		this(config);
		genome.setBasicType(basicType);
	}

	public Morph reproduce() {
		MonochromeBiomorphImpl child = new MonochromeBiomorphImpl(config);
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

}
