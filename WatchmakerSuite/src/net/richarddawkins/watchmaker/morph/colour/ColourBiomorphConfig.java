package net.richarddawkins.watchmaker.morph.colour;

import net.richarddawkins.watchmaker.gui.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.morph.common.BiomorphConfigImpl;
import net.richarddawkins.watchmaker.morph.common.Morph;
import net.richarddawkins.watchmaker.morph.common.Mutagen;

public class ColourBiomorphConfigImpl extends BiomorphConfigImpl {

	public static final int MutTypeNo = 13;
	private ColourMutagenImpl mutagen;
	public Mutagen getMutagen() { return (Mutagen) mutagen; }
	
	public ColourBiomorphConfigImpl() {
		menuBuilder = new ColourMenuBuilder(this);
		name = "Colour";
		toolTip = "Blind Watchmaker (Colour)";
		setIconFromFilename("BWSpiderLogoPurple_icl8_23096_32x32");
		mut = new boolean[MutTypeNo];
		for(int i = 0; i < MutTypeNo; i++)
			mut[i] = true;
    setDefaultBreedingRows(3);
    setDefaultBreedingCols(3);
	}

	@Override
	public Morph createMorph(int type) {
		return new ColourBiomorphImpl(this, type);
	}

	@Override
	public GeneBoxStrip getGeneBoxStrip() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGeneBoxStrip(GeneBoxStrip geneBoxStrip) {
		// TODO Auto-generated method stub
		
	}

}