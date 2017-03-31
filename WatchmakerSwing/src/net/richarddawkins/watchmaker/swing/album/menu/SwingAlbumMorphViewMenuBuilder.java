package net.richarddawkins.watchmaker.swing.album.menu;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;

import javax.swing.AbstractAction;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.menu.WatchmakerAction;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.morphview.triangle.TriangleMorphViewMenuBuilder;

public class SwingAlbumMorphViewMenuBuilder extends TriangleMorphViewMenuBuilder {

    public SwingAlbumMorphViewMenuBuilder() {

    }

    @Override
    public void buildMenu(WatchmakerMenuBar menuBar) {
        WatchmakerMenu menu;
        menu = menuBar.getMenu("Edit");
        menu.add(new ActionAlbumSave());
        menu.add(new ActionAlbumSaveAs());
        menu.add(new ActionAlbumDelete());
        menu.add(new ActionAlbumExport());
        menu.add(new ActionAlbumShow());

    }

    @Override
    public void cleanMenu(WatchmakerMenuBar menuBar) {
        // TODO Auto-generated method stub
        
    }



    @Override
    public void updateMenu(WatchmakerMenuBar menuBar) {
//        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//        ((AbstractAction)addBiomorph).setEnabled(clipboard.isDataFlavorAvailable(new DataFlavor(Genome.class, "Genome")));
    }

}
