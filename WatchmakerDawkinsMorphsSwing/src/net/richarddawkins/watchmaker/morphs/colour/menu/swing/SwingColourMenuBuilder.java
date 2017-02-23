package net.richarddawkins.watchmaker.morphs.colour.menu.swing;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.morphs.colour.swing.AboutColourAction;
import net.richarddawkins.watchmaker.morphs.mono.menu.swing.SwingMonochromeMenuBuilder;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenuItem;

/**
 * Application (About Colour Watchmaker) File (Timing, Quit) Edit ( | | Copy,
 * Paste) Operation (Breed, New Random Start) Help ()
 * 
 * @author alan
 *
 */
public class SwingColourMenuBuilder extends SwingMonochromeMenuBuilder {

	

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	public SwingColourMenuBuilder(AppData appData) {
	    super(appData);
	}

	@Override
	protected WatchmakerMenu buildFileMenu() {
		WatchmakerMenu menu = super.buildFileMenu();
		menu.add(new SwingWatchmakerMenuItem("Timing"));
		return menu;
	}

	@Override
	protected WatchmakerMenu buildHelpMenu() {
		WatchmakerMenu menu = super.buildHelpMenu();
		menu.add(new AboutColourAction(appData));
		return menu;
	}
	
}
