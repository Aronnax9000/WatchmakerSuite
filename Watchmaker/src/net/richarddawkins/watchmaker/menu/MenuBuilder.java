package net.richarddawkins.watchmaker.menu;

import net.richarddawkins.watchmaker.app.AppData;

public interface MenuBuilder {
	public void setAppData(AppData appData);
	public void buildMenu(WatchmakerMenuBar menuBar);

}
