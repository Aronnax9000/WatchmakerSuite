package net.richarddawkins.watchmaker.morph.mono;

import net.richarddawkins.watchmaker.gui.breed.BreedingPanel;
import net.richarddawkins.watchmaker.gui.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.gui.menu.MenuBuilder;
import net.richarddawkins.watchmaker.gui.old.WatchmakerGUI;
import net.richarddawkins.watchmaker.morph.common.BiomorphConfig;
import net.richarddawkins.watchmaker.morph.common.Morph;
import net.richarddawkins.watchmaker.morph.common.Mutagen;
import net.richarddawkins.watchmaker.morph.mono.gui.genebox.MonochromeGeneBoxStrip;
import net.richarddawkins.watchmaker.morph.mono.gui.menu.MonochromeMenuBuilder;

public class MonochromeMorphConfig extends BiomorphConfig {
	
	public MonochromeMorphConfig(WatchmakerGUI watchmakerGUI) {
		menuBuilder = new MonochromeMenuBuilder(this);
		geneBoxCount = 16;
	    gui = watchmakerGUI;
		name = "Monochrome";
		toolTip = "Blind Watchmaker (Monochrome)";
		mut = new boolean[MutTypeNo];
		setIconFromFilename("BWSpiderLogoMono_ICNO_23096_32x32");		
		for(int i = 0; i < MutTypeNo; i++)
			mut[i] = true;
    setDefaultBreedingRows(3);
    setDefaultBreedingCols(5);
	}
	
	public static final int MutTypeNo = 9;
	private MonochromeGeneBoxStrip geneBoxStrip;
	
	private MonochromeMutagen mutagen;
	public Mutagen getMutagen() { return mutagen; }
	
	
	public MonochromeMorphConfig() {
		setIconFromFilename("BWSpiderLogoMono_ICNO_23096_32x32");		
		geneBoxCount = 16;
		menuBuilder = new MonochromeMenuBuilder(this);
		geneBoxStrip = new MonochromeGeneBoxStrip(this);
		name = "Monochrome";
		toolTip = "Blind Watchmaker (Monochrome)";
		mutagen = new MonochromeMutagen(this);
		mut = new boolean[MutTypeNo];
		for(int i = 0; i < MutTypeNo; i++)
			mut[i] = true;
		
	    setDefaultBreedingRows(3);
	    setDefaultBreedingCols(5);
		centrePanel = new BreedingPanel(this);
    
	}
	@Override
	public Morph createMorph(int type) {
		return (Morph) new MonochromeMorph(this, type);
	}
	
	protected MenuBuilder menuBuilder;
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




	@Override
	public void setMutagen(Mutagen mutagen) {
		this.mutagen = (MonochromeMutagen) mutagen;
		
	}
	
}
