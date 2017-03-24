package net.richarddawkins.watchmaker.swing.breed.menu;

import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.morphview.breed.BreedingMenuBuilder;

public class SwingBreedingMenuBuilder extends BreedingMenuBuilder {

    public SwingBreedingMenuBuilder() {

    }

    @Override
    public void buildMenu(WatchmakerMenuBar menuBar) {
        WatchmakerMenu menu;
        menu = menuBar.getMenu("Operation");
        menu.add(new SwingActionBreed());
    }
}
