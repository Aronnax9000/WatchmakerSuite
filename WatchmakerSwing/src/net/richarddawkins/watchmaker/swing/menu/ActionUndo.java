package net.richarddawkins.watchmaker.swing.menu;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.MorphViewsTabbedPanel;

public class ActionUndo extends SwingWatchmakerAction {

    private static final long serialVersionUID = 4121419685469500509L;

    public ActionUndo(AppData appData) {
        super(appData, "Undo", null, KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.ALT_MASK));
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        MorphViewsTabbedPanel pane = appData.getMorphViewsTabbedPane();
        MorphView morphView = pane.getSelectedMorphView();
        morphView.undo();
    }

}