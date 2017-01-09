package net.richarddawkins.watchmaker.morph.colour;

import java.awt.Point;
import java.util.Vector;

import net.richarddawkins.watchmaker.draw.DrawingPrimitive;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.SimpleMorph;
import net.richarddawkins.watchmaker.morph.colour.genome.ColourGenome;
/**
 * <h2>Original sources</h2>
 * <pre>
 * chromosome = array[1..9] of INTEGER;
 * person = record
 *  gene: chromosome;
 *  colorGene: array[1..8] of Longint;{index in clut}
 *  backColorGene: LongInt;{index in clut}
 *  dGene: array[1..10] of SwellType;
 *  segNoGene: INTEGER;
 *  segDistGene: INTEGER;
 *  completenessGene: CompletenessType;
 *  spokesGene: SpokesType;
 *  trickleGene, mutSizeGene, mutProbGene: INTEGER;
 *  limbShapeGene: LimbType;
 *  limbFillGene: LimbFillType;
 *  thicknessGene: INTEGER;
 *  pic: picHandle;
 * end;
 * </pre>
 * @author alan
 *
 */
public class ColourBiomorph extends SimpleMorph  {
	public static final int RAINBOW = 1 << 8;
	protected ColourBiomorphConfig config;
	protected ColourGenome genome;

	@Override
	public void setMorphConfig(MorphConfig config) {
		this.config = (ColourBiomorphConfig) config;
	}
	
	@Override
	public MorphConfig getMorphConfig() { return config; }
	
	public Genome getGenome() {
		return genome;
	}

	public void setGenome(Genome genome) {
		this.genome = (ColourGenome)genome;
	}

	ColourBiomorph() {
		setGenome(new ColourGenome(this));
		pic = new ColourPic();	
	}
	
	public ColourBiomorph(MorphConfig config) {
		this();
		this.setMorphConfig(config);
	}
	
	public ColourBiomorph(MorphConfig config, int basicType) {
		this(config);
		genome.setBasicType(basicType);
	}
	/* (non-Javadoc)
	 * @see net.richarddawkins.watchmaker.watchmaker.morphs.colour.impl.ColourBiomorph#reproduce()
	 */
	@Override
	public Morph reproduce() {
		ColourBiomorph child = new ColourBiomorph(config);
		child.setGenome(genome.reproduce(child));
		child.setParent(this);
		return child;
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
