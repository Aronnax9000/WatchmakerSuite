package net.richarddawkins.watchmaker.morphs.mono;


import java.awt.Rectangle;

import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morphs.Morph;
import net.richarddawkins.watchmaker.morphs.MorphConfig;
import net.richarddawkins.watchmaker.morphs.SimpleMorph;
/**
 * MonochromeBiomorph is a subinterface of Morph which represents Monochrome Biomorphs.
 * <h2>Original source code from Monochrome Biomorphs/Biomorphs</h2>
 * <pre>
 * TYPE
 *   SwellType = (Swell, Same, Shrink); 
 *   CompletenessType = (Single, Double);
 *   SpokesType = (NorthOnly, NSouth, Radial); 
 *   chromosome = ARRAY[1..9] OF Integer; 
 *   
 *   person = RECORD 
 *     gene: chromosome; 
 *     dgene: ARRAY[1..10] OF SwellType;
 *     SegNoGene: Integer; 
 *     SegDistGene: Integer; 
 *     CompletenessGene: CompletenessType;
 *     SpokesGene: SpokesType; 
 *     tricklegene, mutsizegene, mutprobgene: Integer;
 *   end;
 * 
 *   FullPtr = ^Full; 
 *   FullHandle = ^FullPtr; 
 *   
 *   Full = RECORD 
 *     genome: person;
 *     surround: Rect; 
 *     origin, centre: Point; 
 *     parent: FullHandle; 
 *     firstborn: FullHandle; 
 *     lastborn: FullHandle; 
 *     eldersib: FullHandle; 
 *     youngersib: FullHandle; 
 *     Prec, Next: FullHandle; 
 *     Damaged{,Blackened} : Boolean;
 *     snapHandle: Handle; 
 *     snapBytes: Integer; 
 *     snapBounds: Rect; 
 *   end;
 * </pre>
 * @author alan
 *
 */
public class MonochromeBiomorph extends SimpleMorph {
	
	MonochromeBiomorph() {
		setGenome(new MonochromeGenome(this));
		pic = new MonoPic();
	}
	MonochromeBiomorph(MorphConfig config) {
		this();
		setMorphConfig(config);
	}

	MonochromeBiomorph(MorphConfig config, int basicType) {
		this(config);
		genome.setBasicType(basicType);
	}

	public Morph reproduce() {
		MonochromeBiomorph child = new MonochromeBiomorph(config);
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
