package net.richarddawkins.watchmaker.swing.album.menu;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class ActionAlbumSave extends SwingWatchmakerAction {

    private static final long serialVersionUID = 4121419685469500509L;

    public ActionAlbumSave(AppData appData, String name, Icon icon) {
        super(appData, name, icon);
    }

    public ActionAlbumSave(AppData appData) {
        super(appData, "Save Album");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        appData.albumOpen();
    }

}