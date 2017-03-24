package net.richarddawkins.watchmaker.morphs.arthro.swing;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import net.richarddawkins.watchmaker.image.ClassicImageLoader;
import net.richarddawkins.watchmaker.image.ClassicImageLoaderService;
import net.richarddawkins.watchmaker.swing.images.AWTClassicImage;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class AboutArthromorphsAction extends SwingWatchmakerAction {

    private static final long serialVersionUID = 1L;

    public AboutArthromorphsAction() {
        super("About Arthromorphs");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Window window = SwingUtilities
                .getWindowAncestor((Component) e.getSource());
        ClassicImageLoader loader = ClassicImageLoaderService.getInstance()
                .getClassicImageLoader();
        AWTClassicImage classicImage = (AWTClassicImage) loader
                .getPicture("AboutArthromorphs_PICT_00001_282x107");
        JOptionPane.showMessageDialog(window,
                new ImageIcon(classicImage.getImage()),
                "About Blind Watchmaker", JOptionPane.PLAIN_MESSAGE, null);
    }
}
