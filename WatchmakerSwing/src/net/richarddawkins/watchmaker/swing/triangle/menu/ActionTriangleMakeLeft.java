package net.richarddawkins.watchmaker.swing.triangle.menu;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.image.ClassicImageLoaderService;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.swing.images.AWTClassicImage;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class ActionTriangleMakeLeft  extends SwingWatchmakerAction {
    private static final long serialVersionUID = 4121419685469500509L;
    

    public ActionTriangleMakeLeft() {
        super("Make left of triangle",
                new ImageIcon(((AWTClassicImage) ClassicImageLoaderService
                        .getInstance().getClassicImageLoader()
                        .getPicture("IconTriangleLeft_ALAN_32x32")).getImage()));
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        AppData appData = getAppData();
        Morph morph = appData.getMorphOfTheHour();
        appData.getMorphConfig().setTriangleMorph(1, morph);
    }
}
