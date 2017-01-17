package net.richarddawkins.watchmaker.morphs.colour;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphType;
import net.richarddawkins.watchmaker.morph.Mutagen;
import net.richarddawkins.watchmaker.morphs.bio.BiomorphConfig;
import net.richarddawkins.watchmaker.morphs.colour.genome.ColourMutagen;

public class ColourBiomorphConfig extends BiomorphConfig {

	public static final int MutTypeNo = 13;
	
	private ColourMutagen mutagen;
	public Mutagen getMutagen() { return (Mutagen) mutagen; }


	public ColourBiomorphConfig() {
		super(MorphType.COLOUR_BIOMORPH);
		geneBoxCount = 28;
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
