package net.richarddawkins.watchmaker.swing.engineer.menu;

import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.morphview.engineer.EngineeringMenuBuilder;

public class SwingEngineeringMenuBuilder extends EngineeringMenuBuilder {

    public SwingEngineeringMenuBuilder() {

    }
    @Override
    public void buildMenu(WatchmakerMenuBar menuBar) {
        WatchmakerMenu menu;
        menu = menuBar.getMenu("Operation");
        menu.add(new ActionEngineering());
    }

}
