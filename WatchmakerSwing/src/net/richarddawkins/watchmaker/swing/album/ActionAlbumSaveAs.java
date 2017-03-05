package net.richarddawkins.watchmaker.swing.album;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class ActionAlbumSaveAs extends SwingWatchmakerAction {

    private static final long serialVersionUID = 4121419685469500509L;

    public ActionAlbumSaveAs(AppData appData, String name, Icon icon) {
        super(appData, name, icon);
    }

    public ActionAlbumSaveAs(AppData appData) {
        super(appData, "Save As Album...");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        appData.albumOpen();
    }

}