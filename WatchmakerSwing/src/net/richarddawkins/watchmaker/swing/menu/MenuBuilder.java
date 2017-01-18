package net.richarddawkins.watchmaker.swing.menu;

import javax.swing.JMenuBar;

import net.richarddawkins.watchmaker.swing.SwingAppData;

public interface MenuBuilder {
	public void buildMenu(JMenuBar menuBar);

	SwingAppData getSwingAppData();
	void setSwingAppData(SwingAppData swingAppData);
}
