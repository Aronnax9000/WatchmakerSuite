package net.richarddawkins.watchmaker.swing.album;

import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class ActionAlbumOpenClassics extends SwingWatchmakerAction {

    private static final long serialVersionUID = 4121419685469500509L;

    public ActionAlbumOpenClassics(AppData appData, String name, Icon icon) {
        super(appData, name, icon);
        this.appData = appData;
    }

    public ActionAlbumOpenClassics(AppData appData) {
        this(appData, "Show Album", new ImageIcon(ClassicImageLoader
                .getPicture("IconAlbum_ALAN_32x32").getImage()));
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        appData.addClassicAlbums();
    }

}