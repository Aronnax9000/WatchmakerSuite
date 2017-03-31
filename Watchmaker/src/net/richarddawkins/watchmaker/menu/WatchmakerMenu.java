package net.richarddawkins.watchmaker.menu;

import net.richarddawkins.watchmaker.component.WatchComponent;

public interface WatchmakerMenu extends WatchmakerMenuItem {


	void add(WatchmakerAction action);

	void addSeparator();

    void removeMenu(String string);

    WatchComponent getMenu(String text);

}
