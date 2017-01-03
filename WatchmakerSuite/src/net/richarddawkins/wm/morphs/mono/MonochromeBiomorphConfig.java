package net.richarddawkins.wm.morphs.mono;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.gui.WatchmakerTabbedPane;
import net.richarddawkins.watchmaker.gui.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.gui.menu.MenuBuilder;
import net.richarddawkins.watchmaker.morph.common.Morph;
import net.richarddawkins.watchmaker.morph.common.Mutagen;
import net.richarddawkins.wm.WatchmakerGUI;
import net.richarddawkins.wm.morphs.biomorph.BiomorphConfig;

public class MonochromeBiomorphConfig extends BiomorphConfig {
	public static final int MutTypeNo = 9;

	protected MonochromeMutagen mutagen;
	
	@Override
	public Mutagen getMutagen() {
		return mutagen;
	}
	@Override
	public void setMutagen(Mutagen mutagen) {
		this.mutagen = (MonochromeMutagen) mutagen;
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
	@Override
	public GeneBoxStrip getGeneBoxStrip() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setGeneBoxStrip(GeneBoxStrip geneBoxStrip) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setCentrePanel(JPanel panel) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public JPanel getCentrePanel() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setFrame(WatchmakerTabbedPane frame) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public WatchmakerTabbedPane getFrame() {
		// TODO Auto-generated method stub
		return null;
	}

}
