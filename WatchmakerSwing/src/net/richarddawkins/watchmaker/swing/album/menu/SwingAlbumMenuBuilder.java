package net.richarddawkins.watchmaker.swing.album.menu;

import java.util.Collection;

import net.richarddawkins.watchmaker.album.Album;
import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.menu.WatchmakerAction;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.morphview.album.AlbumMenuBuilder;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenu;
import net.richarddawkins.watchmaker.swing.zoo.SwingMultiMorphTypeTabbedPanel;

public class SwingAlbumMenuBuilder extends AlbumMenuBuilder {

    public SwingAlbumMenuBuilder() {

    }

    @Override
    public void buildMenu(WatchmakerMenuBar menuBar) {

        WatchmakerMenu menu;
        menu = menuBar.getMenu("File");
        menu.add(new SwingWatchmakerMenu("Open Classic Album"));
        menu = menuBar.getMenu("Edit");
        
        menu.add(new ActionAlbumAddBiomorph());
        menu.add(new ActionAlbumNew());
        menu.add(new ActionAlbumOpen());
    }

    @Override
    public void updateMenu(WatchmakerMenuBar menuBar) {
        WatchmakerMenu menu;
        menu = menuBar.getMenu("File");
        menu = (WatchmakerMenu)menu.getMenu("Open Classic Album");
        SwingMultiMorphTypeTabbedPanel smmttp = SwingMultiMorphTypeTabbedPanel.getInstance();
        AppData appData = smmttp
                .getSelectedAppData();
        if (appData != null) {
            Collection<Album> albums = appData.getMorphConfig().getAlbums();
            if(albums != null) {
                for (Album album : albums) {
                    String albumName = album.getName();
                    WatchmakerAction openClassics = new ActionAlbumOpenClassic(
                            albumName);
    
                    if(menu.getMenu(albumName) == null)
                            menu.add(openClassics);
                }
            }
        }
    }

}
