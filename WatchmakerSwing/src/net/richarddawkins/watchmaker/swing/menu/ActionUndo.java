package net.richarddawkins.watchmaker.swing.menu;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import net.richarddawkins.watchmaker.morphview.MorphView;

public class ActionUndo extends SwingWatchmakerAction {

    private static final long serialVersionUID = 4121419685469500509L;

    public ActionUndo() {
        super("Undo", null, KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        MorphView morphView = getAppData().getSelectedMorphView();
        morphView.undo();
    }

}