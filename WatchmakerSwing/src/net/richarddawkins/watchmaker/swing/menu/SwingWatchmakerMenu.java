package net.richarddawkins.watchmaker.swing.menu;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import net.richarddawkins.watchmaker.menu.WatchmakerAction;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuItem;

public class SwingWatchmakerMenu extends JMenu implements WatchmakerMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SwingWatchmakerMenu(String name) {
		super(name);
	}

	@Override
	public void add(WatchmakerMenuItem menuItem) {
		super.add((JMenuItem) menuItem);
		
	}

	@Override
	public void add(WatchmakerAction action) {
		super.add((Action)action);
	}
}
