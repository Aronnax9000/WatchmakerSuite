package net.richarddawkins.watchmaker.swing.menu;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

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

    public SwingWatchmakerMenuItem(String string, KeyStroke keyStroke) {
        super(string);
        this.setAccelerator(keyStroke);
    }

    public SwingWatchmakerMenuItem(Action action, KeyStroke keyStroke) {
        super(action);
        this.setAccelerator(keyStroke);
    }

}
