package net.richarddawkins.wm.morphs.mono;

import net.richarddawkins.wm.MenuBuilder;
import net.richarddawkins.wm.WatchmakerGUI;
import net.richarddawkins.wm.morphs.Morph;
import net.richarddawkins.wm.morphs.biomorph.BiomorphConfig;

public class MonochromeBiomorphConfig extends BiomorphConfig {
	public static final int MutTypeNo = 9;

	
	public MonochromeBiomorphConfig(WatchmakerGUI watchmakerGUI) {
		geneBoxCount = 16;
	  gui = watchmakerGUI;
		menuBuilder = new MonochromeMenuBuilder(this);
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
		return new MonochromeBiomorph(this, type);
	}
	
	protected MenuBuilder menuBuilder = new MonochromeMenuBuilder(this);
	@Override
	public MenuBuilder getMenuBuilder() {
		return menuBuilder;
	}
}
