package net.richarddawkins.watchmaker.morphs.concho.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import net.richarddawkins.watchmaker.image.ClassicImageLoader;
import net.richarddawkins.watchmaker.image.ClassicImageLoaderService;
import net.richarddawkins.watchmaker.swing.images.AWTClassicImage;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class AboutSnailsAction extends SwingWatchmakerAction {

    private static final long serialVersionUID = 1L;

    public AboutSnailsAction() {
        super("About Snailmaker");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClassicImageLoader loader = ClassicImageLoaderService.getInstance()
                .getClassicImageLoader();
        AWTClassicImage classicImage = (AWTClassicImage) loader
                .getPicture("AboutSnailsSnikwad_PICT_00002_145x113");
        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor((Component) e.getSource()),
                new ImageIcon(classicImage.getImage()),
                "About Blind Snailmaker", JOptionPane.PLAIN_MESSAGE, null);

    }

}
