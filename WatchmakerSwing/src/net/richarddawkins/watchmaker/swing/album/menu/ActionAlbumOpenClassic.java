package net.richarddawkins.watchmaker.swing.album.menu;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import net.richarddawkins.watchmaker.image.ClassicImageLoaderService;
import net.richarddawkins.watchmaker.swing.images.AWTClassicImage;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class ActionAlbumOpenClassic extends SwingWatchmakerAction {

    private static final long serialVersionUID = 4121419685469500509L;
    protected String albumName;
    public ActionAlbumOpenClassic(String albumName) {
        super(albumName,
                new ImageIcon(((AWTClassicImage) ClassicImageLoaderService
                        .getInstance().getClassicImageLoader()
                        .getPicture("IconAlbum_ALAN_32x32")).getImage()));
        this.albumName = albumName;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        getAppData().addClassicAlbum(albumName);
    }

}