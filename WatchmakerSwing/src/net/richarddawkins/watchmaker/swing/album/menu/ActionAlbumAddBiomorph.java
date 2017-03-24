package net.richarddawkins.watchmaker.swing.album.menu;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

import javax.swing.KeyStroke;

import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class ActionAlbumAddBiomorph extends SwingWatchmakerAction {

    private static final long serialVersionUID = 4121419685469500509L;
    @SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.menu.ActionAlbumAddBiomorph");
    public ActionAlbumAddBiomorph() {
        super("Add Biomorph to Album", null, KeyStroke.getKeyStroke(KeyEvent.VK_A, 
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        getAppData().addMorphToAlbum();
    }

}