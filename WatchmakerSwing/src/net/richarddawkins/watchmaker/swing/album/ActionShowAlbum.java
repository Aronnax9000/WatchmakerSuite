package net.richarddawkins.watchmaker.swing.album;

import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.richarddawkins.watchmaker.album.Album;
import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class ActionShowAlbum extends SwingWatchmakerAction {

    private static final long serialVersionUID = 4121419685469500509L;

    public ActionShowAlbum(AppData appData, String name, Icon icon) {
        super(appData, name, icon);
        this.appData = appData;
    }

    public ActionShowAlbum(AppData appData) {
        this(appData, "Show Album", new ImageIcon(ClassicImageLoader
                .getPicture("IconAlbum_ALAN_32x32").getImage()));
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        MorphConfig config = appData.getMorphConfig();
        Vector<Album> albums = config.getAlbums();
        if (albums != null) {
            for (Album album : albums) {
                appData.addAlbumMorphView(album);
            }
        }
    }

}