package net.richarddawkins.watchmaker.swing.menubuilder;

import javax.swing.JMenuBar;

import net.richarddawkins.watchmaker.swing.appdata.SwingAppData;

public interface MenuBuilder {
	public void buildMenu(JMenuBar menuBar);

	SwingAppData getSwingAppData();
	void setSwingAppData(SwingAppData swingAppData);
}
