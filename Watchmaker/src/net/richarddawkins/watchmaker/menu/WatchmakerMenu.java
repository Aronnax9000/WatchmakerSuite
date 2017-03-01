package net.richarddawkins.watchmaker.menu;

public interface WatchmakerMenu {

    void add(WatchmakerMenuItem menuItem);
    void add(WatchmakerMenu menu);

	void add(WatchmakerAction action);

	void addSeparator();

}
