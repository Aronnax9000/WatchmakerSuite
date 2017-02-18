package net.richarddawkins.watchmaker.swing.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;

public class SwingWatchmakerMenuBar extends JMenuBar implements WatchmakerMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void add(WatchmakerMenu menu) {
		super.add((JMenu) menu);
		
	}

	private static SwingWatchmakerMenuBar instance;
	
	protected SwingWatchmakerMenuBar() {}
	
	public synchronized static WatchmakerMenuBar getInstance() {
		if(instance == null) {
			instance = new SwingWatchmakerMenuBar();
		}
		
		return instance;
	}

}
