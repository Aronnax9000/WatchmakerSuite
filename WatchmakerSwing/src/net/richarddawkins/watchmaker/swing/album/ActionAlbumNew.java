package net.richarddawkins.watchmaker.swing.album;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class ActionAlbumNew extends SwingWatchmakerAction {

    private static final long serialVersionUID = 4121419685469500509L;

    public ActionAlbumNew(AppData appData, String name, Icon icon) {
        super(appData, name, icon);
    }

    public ActionAlbumNew(AppData appData) {
        super(appData, "New Album");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        appData.albumNew();
    }

}