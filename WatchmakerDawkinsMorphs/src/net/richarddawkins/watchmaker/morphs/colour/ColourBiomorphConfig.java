package net.richarddawkins.watchmaker.morphs.colour;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphs.bio.BiomorphConfig;
import net.richarddawkins.watchmaker.morphs.colour.genome.ColourAllowedMutations;
import net.richarddawkins.watchmaker.morphs.colour.genome.ColourMutagen;

public class ColourBiomorphConfig extends BiomorphConfig {

	

	public ColourBiomorphConfig() {
		super();
		setMutagen(new ColourMutagen(new ColourAllowedMutations()));
		geneBoxCount = 28;

	    setDefaultBreedingRows(3);
	    setDefaultBreedingCols(3);
	}

	@Override
	public Morph createMorph(int type) {
			return (Morph) new ColourBiomorph(this, type);
	}



}
