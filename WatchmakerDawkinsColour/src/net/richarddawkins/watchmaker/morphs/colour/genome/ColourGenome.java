package net.richarddawkins.watchmaker.morphs.colour.genome;

import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.morphs.bio.genome.IntegerGeneOneOrGreater;
import net.richarddawkins.watchmaker.morphs.colour.genome.type.LimbFillType;
import net.richarddawkins.watchmaker.morphs.colour.genome.type.LimbShapeType;
import net.richarddawkins.watchmaker.morphs.mono.genome.MonochromeGenome;
/**
 * <h2>The Genome in Dawkins' Colour Biomorphs</h2>
 * <pre>
 * chromosome = array[1..9] of INTEGER;
 * Compass = (NorthSouth, EastWest);
 * CompletenessType = (Single, Double);
 * SpokesType = (NorthOnly, NSouth, Radial);
 * LimbType = (Stick, Oval, Rectangle);
 * LimbFillType = (Open, Filled);
 * person = record
 *         gene: chromosome;
 *         colorgene: array[1..8] of Longint;{index in clut}
 *         BackColorGene: LongInt;{index in clut}
 *         dgene: array[1..10] of SwellType;
 *         SegNoGene: INTEGER;
 *         SegDistGene: INTEGER;
 *         CompletenessGene: CompletenessType;
 *         SpokesGene: SpokesType;
 *         tricklegene, mutsizegene, mutprobgene: INTEGER;
 *         LimbShapeGene: LimbType;
 *         LimbFillGene: LimbFillType;
 *         ThicknessGene: INTEGER;
 *         bioPicture: picHandle;
 * end; 
 * </pre>
 * <h2>Gene validation rules in Dawkins' Colour Biomorphs</h2>
 * <h3>Color Genes</h3>
 * Integer values range from 0 to rainbow(), which calculates
 * the number of available colors based on pixel depth according
 * to the function 2^p - 1 where p is the pixel depth in bits. Check
 * occurs in reproduce().
 * <h3>Genes 1-8 (IntegerGradientGene)</h3>
 * In DeliverSaltation(), if the gradient is Shrink or Swell,
 * the value must fall in [-9 * TrickleGene, 9 * TrickleGene].
 * Both gradient and value are randomised until this condition is
 * satisfied.

 * <h3>Gene 9</h3>
 * Integer from 1 to 8: check occurs in reproduce().
 * <br>
 * In DeliverSaltation(), gene9 is randomised to a value from [1,6],
 * and a check is made to make sure that the value is greater than 1,
 * which is unnecessary given that the RandInt() function can only
 * return positive values.
 * <h3>MutProbGene</h3>
 * A positive percentage: acceptable values are 1 to 100.
 * Checks occur in reproduce().
 * <h3>MutSizeGene</h3>
 * Positive integer: test occurs in reproduce().
 * <br>
 * In DeliverSaltation(), After TrickleGene is randomised,
 * MutSizeGene is set to half whatever TrickleGene is,
 * provided the result is nonzero.
 * <pre>
 * TrickleGene := 1 + randint(100) div 10;
 * if TrickleGene &gt; 1 then
 *   MutSizeGene := Tricklegene div 2
 * </pre>
 * <h3>ThicknessGene</h3>
 * Positive integer: test occurs in reproduce().
 * <h3>TrickleGene</h3>
 * Positive integer: test occurs in reproduce();
 * <h3>SegDistGene gradient</h3>
 * In DeliverSaltation(), gradient is randomised. The
 * resulting gradient may be Shrink or Swell, unless
 * the integer value, multiplied by SegNoGene, would then fall outside [-100,100].
 * <h3>SegNoGene</h3>
 * Positive integer: test occurs in reproduce().
 * 
 * <h2>Gene validation rules which violate the Central Dogma</h2>
 * In develop(), check and force SegNoGene to be 1 or greater.
 * @author Alan Canon
 *
 */
public class ColourGenome extends MonochromeGenome {
    
    @SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morphs.colour.genome.ColourGenome");

    public static final int RAINBOW = 256;

    protected final ColorGene backColorGene = new ColorGene(this, "Background Color", RAINBOW / 3);
    protected final ColorGene colorGene1 = new ColorGene(this, "Color Gene 1");
    protected final ColorGene colorGene2 = new ColorGene(this, "Color Gene 2");
    protected final ColorGene colorGene3 = new ColorGene(this, "Color Gene 3");
    protected final ColorGene colorGene4 = new ColorGene(this, "Color Gene 4");
    protected final ColorGene colorGene5 = new ColorGene(this, "Color Gene 5");
    protected final ColorGene colorGene6 = new ColorGene(this, "Color Gene 6");
    protected final ColorGene colorGene7 = new ColorGene(this, "Color Gene 7");
    protected final ColorGene colorGene8 = new ColorGene(this, "Color Gene 8");
    protected final LimbShapeGene limbShapeGene = new LimbShapeGene(this, "Limb Shape", LimbShapeType.Stick);
    protected final LimbFillGene limbFillGene = new LimbFillGene(this, "Limb Fill", LimbFillType.Filled);
    protected final IntegerGeneOneOrGreater thicknessGene = new IntegerGeneOneOrGreater(this, "Thickness", 1);

    public ColourGenome() { 
    	this.getMutProbGene().setValue(10);
    }
//    @Override
//    public void copy(Genome person) {
//        ColourGenome child = (ColourGenome) person;
//        super.copy(child);
//        child.colorGene1.setValue(colorGene1.getValue());
//        child.colorGene2.setValue(colorGene2.getValue());
//        child.colorGene3.setValue(colorGene3.getValue());
//        child.colorGene4.setValue(colorGene4.getValue());
//        child.colorGene5.setValue(colorGene5.getValue());
//        child.colorGene6.setValue(colorGene6.getValue());
//        child.colorGene7.setValue(colorGene7.getValue());
//        child.colorGene8.setValue(colorGene8.getValue());
//        child.backColorGene.setValue(backColorGene.getValue());
//        child.limbShapeGene.setValue(limbShapeGene.getValue());
//        child.limbFillGene.setValue(limbFillGene.getValue());
//        child.thicknessGene.setValue(thicknessGene.getValue());
//    }


    public ColorGene getBackColorGene() {
        return backColorGene;
    }

    public ColorGene[] getColorGenes() {
        return new ColorGene[] { colorGene1, colorGene2, colorGene3, colorGene4, colorGene5, colorGene6, colorGene7,
                colorGene8 };
    }

    public LimbFillGene getLimbFillGene() {
        return limbFillGene;
    }

    public LimbShapeGene getLimbShapeGene() {
        return limbShapeGene;
    }

    public IntegerGene getThicknessGene() {
        return thicknessGene;
    }

    @Override
    public Gene[] toGeneArray() {
        Gene[] theGenes = new Gene[28];

        Gene[] theMonochromeGenes = super.toGeneArray();
        for (int i = 0; i < 16; i++) {
            theGenes[i] = theMonochromeGenes[i];
        }
        theGenes[16] = colorGene1;
        theGenes[17] = colorGene2;
        theGenes[18] = colorGene3;
        theGenes[19] = colorGene4;
        theGenes[20] = colorGene5;
        theGenes[21] = colorGene6;
        theGenes[22] = colorGene7;
        theGenes[23] = colorGene8;
        theGenes[24] = backColorGene;
        theGenes[25] = limbShapeGene;
        theGenes[26] = limbFillGene;
        theGenes[27] = thicknessGene;
        return theGenes;
    }
}
