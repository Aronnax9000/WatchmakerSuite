package net.richarddawkins.watchmaker.swing.menu;

import javax.swing.Action;
import javax.swing.JMenuItem;

import net.richarddawkins.watchmaker.menu.WatchmakerMenuItem;

public class SwingWatchmakerMenuItem extends JMenuItem implements WatchmakerMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SwingWatchmakerMenuItem(String string) {
		super(string);
	}

	public SwingWatchmakerMenuItem(Action action) {
		super(action);
	}

}
