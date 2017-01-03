package net.richarddawkins.wm.morphs.snail;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.gui.WatchmakerTabbedPane;
import net.richarddawkins.watchmaker.gui.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.gui.menu.MenuBuilder;
import net.richarddawkins.watchmaker.morph.common.Morph;
import net.richarddawkins.watchmaker.morph.common.Mutagen;
import net.richarddawkins.watchmaker.morph.common.SimpleMorphConfig;
import net.richarddawkins.wm.BreedingPanelOld;
import net.richarddawkins.wm.WatchmakerGUI;

public class SnailConfig extends SimpleMorphConfig {

	protected boolean sideView = false;
	
	protected SnailMutagen mutagen;
	@Override
	public Mutagen getMutagen() {
		return mutagen;
	}
	@Override
	public void setMutagen(Mutagen mutagen) {
		this.mutagen = (SnailMutagen) mutagen;
	}
	
	public boolean isSideView() {
		return sideView;
	}

	public void setSideView(boolean sideView) {
		this.sideView = sideView;
	}

	@Override
	public Morph createMorph(int type) {
		return new Snail(this, type);
	}
	private SnailMenuBuilder menuBuilder = new SnailMenuBuilder(this);
	@Override
	public MenuBuilder getMenuBuilder() {
		return menuBuilder;
	}

	public SnailConfig(WatchmakerGUI watchmakerGUI) {
		gui = watchmakerGUI;
		name = "Snails";
		toolTip = "Blind Snailmaker";
		setIconFromFilename("SnailLogoBlackBackground_icl4_17669_32x32");
    setDefaultBreedingRows(3);
    setDefaultBreedingCols(5);
	}

	@Override
	public boolean[] getMut() {
		// TODO Auto-generated method stub
		return null;
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
