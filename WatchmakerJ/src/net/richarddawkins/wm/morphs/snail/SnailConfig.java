package net.richarddawkins.wm.morphs.snail;

import net.richarddawkins.wm.MenuBuilder;
import net.richarddawkins.wm.WatchmakerGUI;
import net.richarddawkins.wm.morphs.Morph;
import net.richarddawkins.wm.morphs.SimpleMorphConfig;

public class SnailConfig  extends SimpleMorphConfig {

	protected boolean sideView = false;
	
	
	
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
}
