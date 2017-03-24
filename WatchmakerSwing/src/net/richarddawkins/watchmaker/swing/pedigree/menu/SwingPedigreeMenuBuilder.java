package net.richarddawkins.watchmaker.swing.pedigree.menu;

import net.richarddawkins.watchmaker.menu.MenuBuilder;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenuItem;

public class SwingPedigreeMenuBuilder implements MenuBuilder {

    public SwingPedigreeMenuBuilder() {

    }

    @Override
    public void buildMenu(WatchmakerMenuBar menuBar) {
        WatchmakerMenu menu;
        menu = menuBar.getMenu("Operation");
        menu.add(new SwingWatchmakerMenuItem(
                new ActionPedigree()));
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
