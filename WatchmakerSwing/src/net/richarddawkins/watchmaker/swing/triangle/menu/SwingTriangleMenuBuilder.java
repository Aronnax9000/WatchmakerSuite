package net.richarddawkins.watchmaker.swing.triangle.menu;

import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.morphview.triangle.TriangleMenuBuilder;

public class SwingTriangleMenuBuilder extends TriangleMenuBuilder {

    public SwingTriangleMenuBuilder() {

    }

    @Override
    public void buildMenu(WatchmakerMenuBar menuBar) {
        super.buildMenu(menuBar);
        
        
        
        WatchmakerMenu menu;
        menu = menuBar.getMenu("Operation");
        menu.add(new ActionTriangle());
        
        
        menu = menuBar.getMenu("View");
        menu.add(new ActionTriangleMakeTop());
        menu.add(new ActionTriangleMakeLeft());
        menu.add(new ActionTriangleMakeRight());
    }
}
