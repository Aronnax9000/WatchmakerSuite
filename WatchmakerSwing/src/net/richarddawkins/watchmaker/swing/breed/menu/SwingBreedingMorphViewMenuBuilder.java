package net.richarddawkins.watchmaker.swing.breed.menu;

import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.morphview.triangle.TriangleMorphViewMenuBuilder;
import net.richarddawkins.watchmaker.swing.menu.ActionNewRandomStart;
import net.richarddawkins.watchmaker.swing.menu.ActionStartTimer;
import net.richarddawkins.watchmaker.swing.menu.ActionStopTimer;

public class SwingBreedingMorphViewMenuBuilder extends TriangleMorphViewMenuBuilder {

    public SwingBreedingMorphViewMenuBuilder() {
    }

    @Override
    public void buildMenu(WatchmakerMenuBar menuBar) {
        WatchmakerMenu menu;
        
        menu = menuBar.getMenu("Operation");
        menu.add(new ActionNewRandomStart());
        menu.add(new ActionStartTimer());
        menu.add(new ActionStopTimer());

        
        menu = menuBar.getMenu("File");
//        menu.add(new SwingWatchmakerMenuItem("Timing"));
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
