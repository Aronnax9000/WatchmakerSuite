package net.richarddawkins.watchmaker.morphs.mono;

import net.richarddawkins.watchmaker.BreedingAndGeneBoxPanel;
import net.richarddawkins.watchmaker.MenuBuilder;
import net.richarddawkins.watchmaker.WatchmakerGUI;
import net.richarddawkins.watchmaker.morphs.Morph;
import net.richarddawkins.watchmaker.morphs.biomorph.BiomorphConfigImpl;

public class MonochromeBiomorphConfig extends BiomorphConfigImpl {
	public static final int MutTypeNo = 9;
	
  @Override
  public BreedingAndGeneBoxPanel getBreedingAndGeneBoxPanel() {
    if (breedingAndGeneBoxPanel == null)
      breedingAndGeneBoxPanel = new MonochromeBreedingAndGeneBoxPanel(this);
    return breedingAndGeneBoxPanel;
  }
	
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
