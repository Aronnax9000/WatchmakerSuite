package net.richarddawkins.watchmaker.swing.album.menu;

import java.awt.event.ActionEvent;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;
import net.richarddawkins.watchmaker.swing.zoo.SwingMultiMorphTypeTabbedPanel;

public class ActionAlbumShow extends SwingWatchmakerAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ActionAlbumShow() {
        super("Show Album");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AppData appData = SwingMultiMorphTypeTabbedPanel.getInstance()
                .getSelectedAppData();
        if (appData != null) {
            MorphView morphView = appData.getSelectedMorphView();
            if (morphView != null) {
                morphView.setIndexed(true);
            }
        }

    }
}
