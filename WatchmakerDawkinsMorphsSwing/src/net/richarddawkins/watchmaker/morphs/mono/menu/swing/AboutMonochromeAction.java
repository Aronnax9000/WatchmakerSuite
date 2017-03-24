package net.richarddawkins.watchmaker.morphs.mono.menu.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JOptionPane;

import net.richarddawkins.watchmaker.image.ClassicImageLoaderService;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.swing.images.AWTClassicImage;

public class AboutMonochromeAction extends AbstractAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Component component;

    public AboutMonochromeAction(WatchmakerMenu menu) {

        super("About Monochrome Biomorphs");
        this.component = ((JMenu) menu).getRootPane();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JOptionPane.showMessageDialog(component,
                new ImageIcon(((AWTClassicImage) ClassicImageLoaderService
                        .getInstance().getClassicImageLoader().getPicture(
                                "AboutBlindWatchmaker_PICT_26817_463x287"))
                                        .getImage()),
                "About Blind Watchmaker", JOptionPane.PLAIN_MESSAGE, null);

    }

}
