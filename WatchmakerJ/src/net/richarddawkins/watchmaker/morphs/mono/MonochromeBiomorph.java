package net.richarddawkins.watchmaker.morphs.mono;

import net.richarddawkins.watchmaker.morphs.biomorph.Biomorph;
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
public interface MonochromeBiomorph extends Biomorph {

}