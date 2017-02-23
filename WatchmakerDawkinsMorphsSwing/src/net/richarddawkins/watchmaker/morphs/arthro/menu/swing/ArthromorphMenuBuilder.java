package net.richarddawkins.watchmaker.morphs.arthro.menu.swing;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.morphs.arthro.swing.AboutArthromorphsAction;
import net.richarddawkins.watchmaker.morphs.arthro.swing.EngineerAction;
import net.richarddawkins.watchmaker.morphs.arthro.swing.PreferencesAction;
import net.richarddawkins.watchmaker.morphs.arthro.swing.ShowAsTextAction;
import net.richarddawkins.watchmaker.swing.menu.SwingMenuBuilder;
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

	@Override
	public WatchmakerMenu buildOperationMenu() {
		WatchmakerMenu menu = super.buildOperationMenu();
		menu.add(new SwingWatchmakerMenuItem(new ShowAsTextAction(appData)));
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
	@Override
	public WatchmakerMenu buildHelpMenu() {
		WatchmakerMenu menu = super.buildHelpMenu();
		menu.add(new AboutArthromorphsAction(appData));
		return menu;
	}
	
}
