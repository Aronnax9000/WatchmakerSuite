package net.richarddawkins.wm.morphs.mono;


import java.awt.Rectangle;

import net.richarddawkins.wm.geom.Point;
import net.richarddawkins.wm.morphs.Genome;
import net.richarddawkins.wm.morphs.Morph;
import net.richarddawkins.wm.morphs.MorphConfig;
import net.richarddawkins.wm.morphs.SimpleMorph;
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
	
  protected MonochromeGenome genome;
  protected MonochromeBiomorphConfig config;
  
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

  @Override
  public void setMorphConfig(MorphConfig config) {
    this.config = (MonochromeBiomorphConfig) config;
    
  }
  @Override
  public MorphConfig getMorphConfig() {
    return config;
  }
  @Override
  public Genome getGenome() {
    return genome;
  }
  @Override
  public void setGenome(Genome genome) {
    this.genome = (MonochromeGenome) genome;
    
  }

}
