package net.richarddawkins.watchmaker.gui.menu;

import javax.swing.JMenuBar;

import net.richarddawkins.watchmaker.gui.SwingAppData;

public interface MenuBuilder {
	public void buildMenu(JMenuBar menuBar);

	SwingAppData getSwingAppData();
	void setSwingAppData(SwingAppData swingAppData);
}
