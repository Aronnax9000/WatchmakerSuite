package net.richarddawkins.watchmaker.morphview.pedigree;

import net.richarddawkins.watchmaker.menu.MenuBuilder;
import net.richarddawkins.watchmaker.menu.SimpleMenuBuilder;
import net.richarddawkins.watchmaker.menu.WatchmakerCheckBoxMenuItem;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;

public abstract class PedigreeMorphViewMenuBuilder extends SimpleMenuBuilder implements MenuBuilder {
    protected WatchmakerCheckBoxMenuItem drawOutOffspring;
    protected WatchmakerCheckBoxMenuItem move;
    protected WatchmakerCheckBoxMenuItem detach;
    protected WatchmakerCheckBoxMenuItem kill;
    protected WatchmakerCheckBoxMenuItem doubleMirrors;
    protected WatchmakerCheckBoxMenuItem noMirrors;
    protected WatchmakerCheckBoxMenuItem singleMirror;

    
    abstract public void createDrawOutOffspring();
    abstract public void createMove();
    abstract public void createDetach();
    abstract public void createKill();
    abstract public void createNoMirrors();
    abstract public void createSingleMirrors();
    abstract public void createDoubleMirrors();
    
    abstract public void groupMirrors();
    abstract public void groupActions();
    
    public PedigreeMorphViewMenuBuilder() {
        createDrawOutOffspring();
        createMove();
        createDetach();
        createKill();
        groupActions();
        createNoMirrors();
        createSingleMirrors();
        createDoubleMirrors();
        groupMirrors();
    }

    @Override 
    public void cleanMenu(WatchmakerMenuBar menuBar) {
        menuBar.remove("Pedigree");
    }


    @Override
    public void updateMenu(WatchmakerMenuBar menuBar) {
        
    }
}
