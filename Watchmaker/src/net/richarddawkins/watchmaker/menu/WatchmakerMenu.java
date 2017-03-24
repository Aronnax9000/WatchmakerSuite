package net.richarddawkins.watchmaker.menu;

public interface WatchmakerMenu extends WatchmakerMenuItem {


	void add(WatchmakerAction action);

	void addSeparator();

}
