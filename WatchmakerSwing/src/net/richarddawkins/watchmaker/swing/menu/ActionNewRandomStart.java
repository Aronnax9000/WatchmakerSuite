package net.richarddawkins.watchmaker.swing.menu;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import net.richarddawkins.watchmaker.image.ClassicImageLoaderService;
import net.richarddawkins.watchmaker.swing.images.AWTClassicImage;

public class ActionNewRandomStart extends SwingWatchmakerAction {

    private static final long serialVersionUID = 1L;

    public ActionNewRandomStart() {
        super("Hopeful Monster (New Random Start)",
                new ImageIcon(((AWTClassicImage)ClassicImageLoaderService.getInstance().getClassicImageLoader()
                        .getPicture("SixSidedDieShowsFiveIcon_ICON_00257_32x32")).getImage()),
                KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getAppData().newRandomStart();
    }
}
