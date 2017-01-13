package net.richarddawkins.watchmaker.morph.colour;

import net.richarddawkins.watchmaker.genome.gui.GeneBoxStrip;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphType;
import net.richarddawkins.watchmaker.morph.Mutagen;
import net.richarddawkins.watchmaker.morph.biomorph.BiomorphConfig;
import net.richarddawkins.watchmaker.morph.colour.genome.ColourMutagen;
import net.richarddawkins.watchmaker.morph.colour.genome.gui.ColourGeneBoxStrip;
import net.richarddawkins.watchmaker.morph.colour.gui.ColourMenuBuilder;

public class ColourBiomorphConfig extends BiomorphConfig {

	public static final int MutTypeNo = 13;
	
	private ColourMutagen mutagen;
	public Mutagen getMutagen() { return (Mutagen) mutagen; }
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) { 
		return new ColourGeneBoxStrip(this, engineeringMode); }

	public ColourBiomorphConfig() {
		super(MorphType.COLOUR_BIOMORPH);
		geneBoxCount = 28;
		menuBuilder = new ColourMenuBuilder(this);
		mut = new boolean[MutTypeNo];
		for(int i = 0; i < MutTypeNo; i++)
			mut[i] = true;
	    setDefaultBreedingRows(3);
	    setDefaultBreedingCols(3);
	}

	@Override
	public Morph createMorph(int type) {
			return (Morph) new ColourBiomorph(this, type);
	}

	@Override
	public void setMutagen(Mutagen mutagen) {
		this.mutagen = (ColourMutagen) mutagen;
	}

}
