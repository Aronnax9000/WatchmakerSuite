package net.richarddawkins.watchmaker.menu;

public interface MenuBuilder {

    
    /** 
     * Add items to a menu bar.
     * Please note that implementations may clear or otherwise alter 
     * the menu bar prior to adding items. 
     * @param menuBar the menu bar to add menus to.
     * */
	public void buildMenu(WatchmakerMenuBar menuBar);
	/** 
	 * Remove items created by buildMenu 
	 * @param menuBar the menu bar to clean.
	 * */
	public void cleanMenu(WatchmakerMenuBar menuBar);
	/**
	 * Update the menu bar in response to change in application state.
	 * @param menuBar the menu bar to update.
	 */
	public void updateMenu(WatchmakerMenuBar menuBar);
}