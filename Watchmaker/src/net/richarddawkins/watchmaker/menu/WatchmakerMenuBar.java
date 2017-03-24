package net.richarddawkins.watchmaker.menu;

import net.richarddawkins.watchmaker.component.WatchComponent;

public interface WatchmakerMenuBar extends WatchComponent {

	void add(WatchmakerMenu menu);

	void repaint();



	/**
	 * Remove the named component from the menu bar.
	 * @param string the name of the component to remove.
	 */
    void remove(String string);

    WatchmakerMenu getMenu(String string);
    

    
}
