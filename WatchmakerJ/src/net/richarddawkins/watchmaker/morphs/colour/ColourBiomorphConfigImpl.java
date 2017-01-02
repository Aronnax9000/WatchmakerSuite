package net.richarddawkins.watchmaker.morphs.colour;

import net.richarddawkins.watchmaker.WatchmakerGUI;
import net.richarddawkins.watchmaker.morphs.Morph;
import net.richarddawkins.watchmaker.morphs.biomorph.BiomorphConfigImpl;

public class ColourBiomorphConfigImpl extends BiomorphConfigImpl {

	public static final int MutTypeNo = 13;
	
	public ColourBiomorphConfigImpl(WatchmakerGUI watchmakerGUI) {
		menuBuilder = new ColourMenuBuilder(this);
		gui = watchmakerGUI;
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

}
