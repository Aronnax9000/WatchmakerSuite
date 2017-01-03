package net.richarddawkins.wm.morphs.colour;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.gui.WatchmakerTabbedPane;
import net.richarddawkins.watchmaker.gui.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.morph.common.Morph;
import net.richarddawkins.watchmaker.morph.common.Mutagen;
import net.richarddawkins.wm.WatchmakerGUI;
import net.richarddawkins.wm.morphs.biomorph.BiomorphConfig;

public class ColourBiomorphConfig extends BiomorphConfig {

	protected ColourMutagen mutagen;
	@Override
	public Mutagen getMutagen() {
		return mutagen;
	}
	@Override
	public void setMutagen(Mutagen mutagen) {
		this.mutagen = (ColourMutagen) mutagen;
	}

	public static final int MutTypeNo = 13;
	
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

	@Override
	public Morph createMorph(int type) {
		return new ColourBiomorph(this, type);
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
