package net.richarddawkins.watchmaker.morph.mono;

import net.richarddawkins.watchmaker.gui.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.gui.menu.MenuBuilder;
import net.richarddawkins.watchmaker.morph.common.BiomorphConfigImpl;
import net.richarddawkins.watchmaker.morph.common.Morph;
import net.richarddawkins.watchmaker.morph.common.Mutagen;
import net.richarddawkins.watchmaker.morph.mono.gui.genebox.MonochromeGeneBoxStrip;
import net.richarddawkins.watchmaker.morph.mono.gui.menu.MonochromeMenuBuilder;

public class MonochromeMorphConfig extends BiomorphConfigImpl {
	public static final int MutTypeNo = 9;
	private MonochromeGeneBoxStrip geneBoxStrip;
	
	private MonochromeMutagen mutagen;
	public Mutagen getMutagen() { return mutagen; }
	
	
	
	public MonochromeMorphConfig() {
		mutagen = new MonochromeMutagen(this);
		geneBoxCount = 16;
		geneBoxStrip = new MonochromeGeneBoxStrip(this);
		name = "Monochrome";
		toolTip = "Blind Watchmaker (Monochrome)";
		mut = new boolean[MutTypeNo];
		setIconFromFilename("BWSpiderLogoMono_ICNO_23096_32x32");		
		for(int i = 0; i < MutTypeNo; i++)
			mut[i] = true;
		
    setDefaultBreedingRows(3);
    setDefaultBreedingCols(5);
    
	}
	@Override
	public Morph createMorph(int type) {
		return new MonochromeMorph(this, type);
	}
	
	protected MenuBuilder menuBuilder = new MonochromeMenuBuilder(this);
	@Override
	public MenuBuilder getMenuBuilder() {
		return menuBuilder;
	}

	public GeneBoxStrip getGeneBoxStrip() {
		return geneBoxStrip;
	}

	public void setGeneBoxStrip(GeneBoxStrip geneBoxStrip) {
		this.geneBoxStrip = (MonochromeGeneBoxStrip)geneBoxStrip;
	}
	
}
