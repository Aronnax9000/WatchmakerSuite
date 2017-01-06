package net.richarddawkins.watchmaker.gui.menu;

import javax.swing.JMenuBar;

import net.richarddawkins.watchmaker.morph.common.MorphConfig;

public interface MenuBuilder {
	public void buildMenu(JMenuBar menuBar);
	void setMorphConfig(MorphConfig config);
	MorphConfig getMorphConfig();
}
