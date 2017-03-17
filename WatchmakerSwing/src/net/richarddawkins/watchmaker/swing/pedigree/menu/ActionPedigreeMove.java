package net.richarddawkins.watchmaker.swing.pedigree.menu;

import java.awt.Cursor;
import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.cursor.WatchmakerCursor;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;
import net.richarddawkins.watchmaker.swing.pedigree.SwingPedigreeMorphView;

public class ActionPedigreeMove extends SwingWatchmakerAction {
    private static final long serialVersionUID = 4121419685469500509L;

    public ActionPedigreeMove(AppData appData, String name, Icon icon) {
        super(appData, name, icon);
        this.appData = appData;
    }

    public ActionPedigreeMove(AppData appData) {
        this(appData, "Move", null);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        MorphView morphView = appData.getSelectedMorphView();
        if (morphView instanceof SwingPedigreeMorphView) {
            MorphViewPanel mvp = morphView.getPanels().firstElement();
            JPanel wp = (JPanel) mvp.getPanel();
            wp.setCursor((Cursor) appData.getWatchmakerCursorFactory()
                    .getCursor(WatchmakerCursor.move));
        }
    }
}
