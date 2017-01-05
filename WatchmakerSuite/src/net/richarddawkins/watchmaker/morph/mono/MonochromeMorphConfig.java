package net.richarddawkins.watchmaker.morph.mono;

import net.richarddawkins.watchmaker.gui.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.gui.menu.MenuBuilder;
import net.richarddawkins.watchmaker.morph.common.BiomorphConfig;
import net.richarddawkins.watchmaker.morph.common.Morph;
import net.richarddawkins.watchmaker.morph.common.Mutagen;
import net.richarddawkins.watchmaker.morph.mono.gui.genebox.MonochromeGeneBoxStrip;
import net.richarddawkins.watchmaker.morph.mono.gui.menu.MonochromeMenuBuilder;

public class MonochromeMorphConfig extends BiomorphConfig {



	public static final int MutTypeNo = 9;


	protected MonochromeMutagen mutagen;
	protected MenuBuilder menuBuilder;

	public Mutagen getMutagen() {
		return mutagen;
	}

	public MonochromeMorphConfig() {
		setIconFromFilename("BWSpiderLogoMono_ICNO_23096_32x32");
		geneBoxCount = 16;
		name = "Monochrome";
		toolTip = "Blind Watchmaker (Monochrome)";
		
		mut = new boolean[MutTypeNo];
		for (int i = 0; i < MutTypeNo; i++)
			mut[i] = true;

		setDefaultBreedingRows(3);
		setDefaultBreedingCols(5);
		menuBuilder = new MonochromeMenuBuilder(this);
		mutagen = new MonochromeMutagen(this);
		
	}

	@Override
	public Morph createMorph(int type) {
		return (Morph) new MonochromeMorph(this, type);
	}


	@Override
	public MenuBuilder getMenuBuilder() {
		return menuBuilder;
	}

	@Override
	public void setMutagen(Mutagen mutagen) {
		this.mutagen = (MonochromeMutagen) mutagen;

	}
	
	@Override
	public GeneBoxStrip newGeneBoxStrip() { return new MonochromeGeneBoxStrip(this);}


}
