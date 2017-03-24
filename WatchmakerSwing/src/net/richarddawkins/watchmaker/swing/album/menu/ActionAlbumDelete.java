package net.richarddawkins.watchmaker.swing.album.menu;

import java.awt.event.ActionEvent;

import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class ActionAlbumDelete extends SwingWatchmakerAction {

    private static final long serialVersionUID = 4121419685469500509L;

    public ActionAlbumDelete() {
        super("Delete Album");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        getAppData().albumDelete();
    }

}