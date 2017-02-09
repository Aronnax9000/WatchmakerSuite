package net.richarddawkins.watchmaker.morphs.arthro.swing;

import java.awt.Component;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.swing.menu.ActionEngineering;
import net.richarddawkins.watchmaker.swing.menu.SwingActionBreed;
import net.richarddawkins.watchmaker.swing.menu.SwingMenuBuilder;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenu;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenuItem;
/**
 * File (New | Open, Close, Save, Save As... | Quit)
 * Edit (Undo | Cut, Copy, Paste, Clear, Select All | Show Clipboard)
 * Operation (Breed , Show as Text, Engineer)
 * View (Preferences)
 * @author alan
 *
 */
public class ArthromorphMenuBuilder extends SwingMenuBuilder  {
	
	
	@SuppressWarnings("unused")

	private static final long serialVersionUID = 1L;
	public ArthromorphMenuBuilder(AppData appData) { super(appData);}
	
	public void buildMenu(WatchmakerMenuBar menuBar) {
		super.buildMenu(menuBar);
		menuBar.add(buildFileMenu());
		menuBar.add(buildEditMenu());
		menuBar.add(buildOperationMenu());
		menuBar.add(buildViewMenu());
		menuBar.add(buildHelpMenu());
		menuBar.repaint();

	}

	public WatchmakerMenu buildFileMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("File");
		menu.add(new SwingWatchmakerMenuItem("New"));
		menu.add(new SwingWatchmakerMenuItem("Open"));
		menu.add(new SwingWatchmakerMenuItem("Close"));
		menu.add(new SwingWatchmakerMenuItem("Save"));
		menu.add(new SwingWatchmakerMenuItem("Save As..."));
		
		return menu;
	}

	public WatchmakerMenu buildOperationMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("Operation");
		menu.add(new SwingWatchmakerMenuItem(new SwingActionBreed(appData)));
		menu.add(new SwingWatchmakerMenuItem(new ShowAsTextAction(appData)));
		menu.add(new ActionEngineering(appData));
		menu.add(new SwingWatchmakerMenuItem(new EngineerAction(appData)));
		return menu;
	}
	@Override
	public WatchmakerMenu buildViewMenu() {
		
		WatchmakerMenu menu = super.buildViewMenu();
		PreferencesAction preferencesAction = new PreferencesAction(appData); 
		
		menu.add(preferencesAction);

		return menu;
	}
	public WatchmakerMenu buildHelpMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("Help");
		menu.add(new AboutArthromorphsAction((Component)menu));
		return menu;
	}
	
}
