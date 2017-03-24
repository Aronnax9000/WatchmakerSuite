package net.richarddawkins.watchmaker.swing.driftsweep.menu;

import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.morphview.driftsweep.DriftSweepMenuBuilder;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenuItem;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerOpenMorphViewAction;

public class SwingDriftSweepMenuBuilder extends DriftSweepMenuBuilder {

    public SwingDriftSweepMenuBuilder() {

    }
    @Override
    public void buildMenu(WatchmakerMenuBar menuBar) {
        WatchmakerMenu menu;
        menu = menuBar.getMenu("Operation");
        menu.add(new SwingWatchmakerMenuItem(
                new SwingWatchmakerOpenMorphViewAction("Drift Sweep")));
    }

}
