package net.richarddawkins.watchmaker.swing.drift.menu;

import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.morphview.drift.DriftMenuBuilder;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenuItem;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerOpenMorphViewAction;

public class SwingDriftMenuBuilder extends DriftMenuBuilder {

    public SwingDriftMenuBuilder() {

    }
    
    @Override
    public void buildMenu(WatchmakerMenuBar menuBar) {
        WatchmakerMenu menu;
        menu = menuBar.getMenu("Operation");
        menu.add(new SwingWatchmakerMenuItem(
                new SwingWatchmakerOpenMorphViewAction("Drift")));
    }

}
