package net.richarddawkins.watchmaker.swing.menu;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.menu.WatchmakerAction;

abstract public class SwingWatchmakerAction extends AbstractAction implements WatchmakerAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected AppData appData;
	public SwingWatchmakerAction(String name) {
		super(name);
	}
	public SwingWatchmakerAction(AppData appData, String string) {
		super(string);
		this.appData = appData;
	}

	public SwingWatchmakerAction(AppData appData, String name, Icon icon) {
		super(name, icon);
		this.appData = appData;
	}


}
