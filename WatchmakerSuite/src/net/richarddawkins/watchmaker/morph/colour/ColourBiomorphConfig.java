package net.richarddawkins.watchmaker.morph.colour;

import net.richarddawkins.watchmaker.gui.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.morph.common.BiomorphConfig;
import net.richarddawkins.watchmaker.morph.common.Morph;
import net.richarddawkins.watchmaker.morph.common.MorphType;
import net.richarddawkins.watchmaker.morph.common.Mutagen;

public class ColourBiomorphConfig extends BiomorphConfig {

	public static final int MutTypeNo = 13;
	private ColourMutagen mutagen;
	public Mutagen getMutagen() { return (Mutagen) mutagen; }
	public GeneBoxStrip newGeneBoxStrip() { return new ColourGeneBoxStrip(this); }

	

	public ColourBiomorphConfig() {
		super(MorphType.COLOUR_BIOMORPH);
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
