package net.richarddawkins.watchmaker.swing.menu;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;

public class ActionNewRandomStart extends SwingWatchmakerAction {

    private static final long serialVersionUID = 1L;

    public ActionNewRandomStart(AppData appData) {
        super(appData, "Hopeful Monster (New Random Start)",
                new ImageIcon(ClassicImageLoader
                        .getPicture("SixSidedDieShowsFiveIcon_ICON_00257_32x32")
                        .getImage()),
                KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.ALT_MASK));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        appData.newRandomStart();

    }
}
