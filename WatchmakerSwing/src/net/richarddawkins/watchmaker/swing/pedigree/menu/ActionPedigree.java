package net.richarddawkins.watchmaker.swing.pedigree.menu;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import net.richarddawkins.watchmaker.image.ClassicImageLoaderService;
import net.richarddawkins.watchmaker.swing.images.AWTClassicImage;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class ActionPedigree extends SwingWatchmakerAction {
    private static final long serialVersionUID = 4121419685469500509L;

    public ActionPedigree() {
        super("Display Pedigree",
                new ImageIcon(((AWTClassicImage) ClassicImageLoaderService
                        .getInstance().getClassicImageLoader().getPicture(
                                "IconPedigree_ALAN_CURS_00147_32x32"))
                                        .getImage()));
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        getAppData().addPedigreeMorphView();
    }
}
