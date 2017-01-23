package net.richarddawkins.watchmaker.morphs.colour.genome;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphs.bio.Biomorph;
import net.richarddawkins.watchmaker.morphs.bio.genome.IntegerGeneOneOrGreater;
import net.richarddawkins.watchmaker.morphs.colour.genome.type.LimbFillType;
import net.richarddawkins.watchmaker.morphs.colour.genome.type.LimbShapeType;
import net.richarddawkins.watchmaker.morphs.mono.genome.MonochromeGenome;

public class ColourGenome extends MonochromeGenome {

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

	int thick;

	public ColourGenome() {
		
	}

	public void addToBackColorGene(int summand) {

		int newValue = backColorGene.getValue() + summand;
		if (newValue > RAINBOW - 1) {
			newValue = RAINBOW - 1;
		}
		if (newValue < 0) {
			newValue = 0;
		}
		backColorGene.setValue(newValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.richarddawkins.watchmaker.morphs.colour.impl.ColourBiomorph#basicTree
	 * ()
	 */
	@Override
	public void basicTree() {
		makeGenes(-Biomorph.TRICKLE, -Biomorph.TRICKLE, -Biomorph.TRICKLE, -Biomorph.TRICKLE, -Biomorph.TRICKLE, 0,
				Biomorph.TRICKLE, Biomorph.TRICKLE, 6);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.richarddawkins.watchmaker.morphs.colour.impl.ColourBiomorph#chess()
	 */
	@Override
	public void chess() {

		makeGenes(-Biomorph.TRICKLE, 3 * Biomorph.TRICKLE, -3 * Biomorph.TRICKLE, -3 * Biomorph.TRICKLE,
				Biomorph.TRICKLE, -2 * Biomorph.TRICKLE, 6 * Biomorph.TRICKLE, -5 * Biomorph.TRICKLE, 7);
	}

	@Override
	public void copy(Genome person) {
		ColourGenome child = (ColourGenome) person;
		super.copy(child);
		child.colorGene1.setValue(colorGene1.getValue());
		child.colorGene2.setValue(colorGene2.getValue());
		child.colorGene3.setValue(colorGene3.getValue());
		child.colorGene4.setValue(colorGene4.getValue());
		child.colorGene5.setValue(colorGene5.getValue());
		child.colorGene6.setValue(colorGene6.getValue());
		child.colorGene7.setValue(colorGene7.getValue());
		child.colorGene8.setValue(colorGene8.getValue());
		child.backColorGene.setValue(backColorGene.getValue());
		child.limbShapeGene.setValue(limbShapeGene.getValue());
		child.limbFillGene.setValue(limbFillGene.getValue());
		child.thicknessGene.setValue(thicknessGene.getValue());
	}


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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.richarddawkins.watchmaker.morphs.colour.impl.ColourBiomorph#insect()
	 */
	@Override
	public void insect() {
		makeGenes(Biomorph.TRICKLE, Biomorph.TRICKLE, -4 * Biomorph.TRICKLE, Biomorph.TRICKLE, -Biomorph.TRICKLE,
				-2 * Biomorph.TRICKLE, 8 * Biomorph.TRICKLE, -4 * Biomorph.TRICKLE, 6);

	}

	public void makeGenes(int a, int b, int c, int d, int e, int f, int g, int h, int i) {
		super.makeGenes(a, b, c, d, e, f, g, h, i);
		segDistGene.setValue(1);
	}


	public Genome reproduce(Morph newMorph) {
		ColourGenome child = new ColourGenome();
		copy(child);
		newMorph.getMorphConfig().getMutagen().mutate(child);
		newMorph.setGenome(child);
		return child;
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
