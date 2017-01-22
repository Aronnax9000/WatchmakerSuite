package net.richarddawkins.watchmaker.swing.menu;

import javax.swing.JCheckBoxMenuItem;

import net.richarddawkins.watchmaker.menu.WatchmakerCheckBoxMenuItem;

public class SwingWatchmakerCheckBoxMenuItem extends JCheckBoxMenuItem implements WatchmakerCheckBoxMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SwingWatchmakerCheckBoxMenuItem(SwingWatchmakerAction swingWatchmakerAction) {
		super(swingWatchmakerAction);
		
	}
	
	public SwingWatchmakerCheckBoxMenuItem(String name) {
		super(name);
	}

}
