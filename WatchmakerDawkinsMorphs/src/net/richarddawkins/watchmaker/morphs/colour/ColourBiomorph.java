package net.richarddawkins.watchmaker.morphs.colour;

import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.SimpleMorph;
import net.richarddawkins.watchmaker.morphs.colour.geom.ColourPic;

/**
 * <h2>Original sources</h2>
 * 
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
 * 
 * @author alan
 *
 */
public class ColourBiomorph extends SimpleMorph {
	public static final int RAINBOW = 1 << 8;

	public ColourBiomorph(MorphConfig config) {
		super(config);
		this.pic = new ColourPic();
	}

	public ColourBiomorph(MorphConfig config, int basicType) {
		super(config, basicType);
	}

}
