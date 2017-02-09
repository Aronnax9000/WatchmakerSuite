package net.richarddawkins.watchmaker.morphs.colour.swing;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.swing.app.SwingAppData;
import net.richarddawkins.watchmaker.swing.menu.ActionEngineering;
import net.richarddawkins.watchmaker.swing.menu.SwingMenuBuilder;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenu;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenuItem;

/**
 * Application (About Colour Watchmaker) File (Timing, Quit) Edit ( | | Copy,
 * Paste) Operation (Breed, New Random Start) Help ()
 * 
 * @author alan
 *
 */
public class ColourMenuBuilder extends SwingMenuBuilder implements PropertyChangeListener {


	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	public ColourMenuBuilder(SwingAppData swingAppData) {
	    super(swingAppData);
	}

	public void buildMenu(WatchmakerMenuBar menuBar) {
		super.buildMenu(menuBar);
		
		menuBar.add(buildFileMenu());
		menuBar.add(buildEditMenu());
		menuBar.add(buildOperationMenu());
		menuBar.add(buildViewMenu());
		menuBar.add(buildHelpMenu());
		menuBar.repaint();
	}

	private WatchmakerMenu buildFileMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("File");
		menu.add(new SwingWatchmakerMenuItem("Timing"));
		return menu;
	}


	@Override
	protected WatchmakerMenu buildOperationMenu() {
		WatchmakerMenu menu = super.buildOperationMenu();
		menu.add(new SwingWatchmakerMenuItem(new ColourActionBreed(appData)));
		menu.add(new ActionEngineering(appData));

		
		
		return menu;
	}

	private WatchmakerMenu buildHelpMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("Help");
		menu.add(new AboutColourAction((Component)appData.getFrame()));
		return menu;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

	
}
