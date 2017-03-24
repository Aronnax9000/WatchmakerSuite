package net.richarddawkins.watchmaker.swing.album.menu;

import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.morphview.album.AlbumMenuBuilder;

public class SwingAlbumMenuBuilder extends AlbumMenuBuilder {

    public SwingAlbumMenuBuilder() {

    }

    @Override
    public void buildMenu(WatchmakerMenuBar menuBar) {

        WatchmakerMenu menu;
        menu = menuBar.getMenu("Edit");
        menu.add(new ActionAlbumAddBiomorph());
        menu.add(new ActionAlbumNew());
        menu.add(new ActionAlbumOpen());
    }

}
