package net.richarddawkins.watchmaker.swing.album.menu;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import net.richarddawkins.watchmaker.image.ClassicImageLoaderService;
import net.richarddawkins.watchmaker.swing.images.AWTClassicImage;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class ActionAlbumOpenClassics extends SwingWatchmakerAction {

    private static final long serialVersionUID = 4121419685469500509L;

    public ActionAlbumOpenClassics() {
        super("Open Classic Albums",
                new ImageIcon(((AWTClassicImage) ClassicImageLoaderService
                        .getInstance().getClassicImageLoader()
                        .getPicture("IconAlbum_ALAN_32x32")).getImage()));
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        getAppData().addClassicAlbums();
    }

}