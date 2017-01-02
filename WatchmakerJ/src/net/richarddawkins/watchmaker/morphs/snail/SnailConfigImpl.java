package net.richarddawkins.watchmaker.morphs.snail;

import net.richarddawkins.watchmaker.MenuBuilder;
import net.richarddawkins.watchmaker.WatchmakerGUI;
import net.richarddawkins.watchmaker.morphs.Morph;
import net.richarddawkins.watchmaker.morphs.SimpleMorphConfig;

public class SnailConfigImpl  extends SimpleMorphConfig implements SnailConfig {

	protected boolean sideView = false;
	
	
	/* (non-Javadoc)
	 * @see net.richarddawkins.watchmaker.morphs.snail.impl.SnailConfig#isSideView()
	 */
	@Override
	public boolean isSideView() {
		return sideView;
	}

	/* (non-Javadoc)
	 * @see net.richarddawkins.watchmaker.morphs.snail.impl.SnailConfig#setSideView(boolean)
	 */
	@Override
	public void setSideView(boolean sideView) {
		this.sideView = sideView;
	}

	@Override
	public Morph createMorph(int type) {
		return new SnailImpl(this, type);
	}
	private SnailMenuBuilder menuBuilder = new SnailMenuBuilder(this);
	@Override
	public MenuBuilder getMenuBuilder() {
		return menuBuilder;
	}

	public SnailConfigImpl(WatchmakerGUI watchmakerGUI) {
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
