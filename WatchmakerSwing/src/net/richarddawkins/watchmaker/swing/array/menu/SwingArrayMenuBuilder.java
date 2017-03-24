package net.richarddawkins.watchmaker.swing.array.menu;

import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.morphview.array.ArrayMenuBuilder;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenuItem;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerOpenMorphViewAction;

public class SwingArrayMenuBuilder extends ArrayMenuBuilder {

    public SwingArrayMenuBuilder() {

    }

    @Override
    public void buildMenu(WatchmakerMenuBar menuBar) {
        WatchmakerMenu menu;
        menu = menuBar.getMenu("Operation");
        menu.add(new SwingWatchmakerMenuItem(
                new SwingWatchmakerOpenMorphViewAction("Array")));

    }
}
