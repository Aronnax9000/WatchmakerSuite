package net.richarddawkins.watchmaker.menu;

public interface WatchmakerMenu {

	void add(WatchmakerMenuItem menuItem);

	void add(WatchmakerAction action);

	void addSeparator();

}
