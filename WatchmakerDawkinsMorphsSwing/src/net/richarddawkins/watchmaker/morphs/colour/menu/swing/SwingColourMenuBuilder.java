package net.richarddawkins.watchmaker.morphs.colour.menu.swing;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.morphs.colour.swing.AboutColourAction;
import net.richarddawkins.watchmaker.morphs.mono.menu.swing.SwingMonochromeMenuBuilder;

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

	@Override
    public void buildMenu(WatchmakerMenuBar menuBar) {

        WatchmakerMenu menu;
        
        menu = menuBar.getMenu("Help");
        menu.add(new AboutColourAction(appData));
        
    }

    public SwingColourMenuBuilder(AppData appData) {
	    super(appData);
	}
}
