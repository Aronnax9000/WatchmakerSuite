package net.richarddawkins.watchmaker.morph.colour;

import net.richarddawkins.watchmaker.gui.old.WatchmakerGUI;
import net.richarddawkins.watchmaker.morph.common.BiomorphConfig;
import net.richarddawkins.watchmaker.morph.common.Morph;
import net.richarddawkins.watchmaker.morph.common.Mutagen;

public class ColourBiomorphConfig extends BiomorphConfig {

	public static final int MutTypeNo = 13;
	private ColourMutagen mutagen;
	public Mutagen getMutagen() { return (Mutagen) mutagen; }
	public ColourBiomorphConfig(WatchmakerGUI watchmakerGUI) {
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

	public ColourBiomorphConfig() {
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
			return (Morph) new ColourBiomorph(this, type);
	}

	

	@Override
	public void setMutagen(Mutagen mutagen) {
		this.mutagen = (ColourMutagen) mutagen;
		
	}

}
