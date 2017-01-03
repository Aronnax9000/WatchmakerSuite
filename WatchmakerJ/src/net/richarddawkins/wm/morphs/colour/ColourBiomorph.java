package net.richarddawkins.watchmaker.morphs.colour;

import net.richarddawkins.watchmaker.morphs.Morph;
import net.richarddawkins.watchmaker.morphs.MorphConfig;
import net.richarddawkins.watchmaker.morphs.SimpleMorph;
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
public class ColourBiomorph extends SimpleMorph   {

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
	 * @see net.richarddawkins.watchmaker.morphs.colour.impl.ColourBiomorph#reproduce()
	 */
	@Override
	public Morph reproduce() {
		ColourBiomorph child = new ColourBiomorph(config);
		child.setGenome(genome.reproduce(child));
		child.setParent(this);
		return child;
	}


	
}
