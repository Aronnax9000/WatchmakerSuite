package net.richarddawkins.watchmaker.morphs.arthro.menu.swing;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
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
    public void buildMenu(WatchmakerMenuBar menuBar) {
        WatchmakerMenu menu;
        menu = menuBar.getMenu("Help");
        menu.add(new AboutArthromorphsAction());
        menu = menuBar.getMenu("Operation");
        menu.add(new SwingWatchmakerMenuItem(new ShowAsTextAction()));
        menu.add(new SwingWatchmakerMenuItem(new EngineerAction()));
        menu = menuBar.getMenu("View");
        PreferencesAction preferencesAction = new PreferencesAction(); 
        menu.add(preferencesAction);
    }

    @Override
    public void cleanMenu(WatchmakerMenuBar menuBar) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateMenu(WatchmakerMenuBar menuBar) {
        // TODO Auto-generated method stub
        
    }
	
}
