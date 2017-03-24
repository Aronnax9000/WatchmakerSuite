package net.richarddawkins.watchmaker.swing.engineer.menu;

import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.image.ClassicImageLoaderService;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.swing.images.AWTClassicImage;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class ActionEngineering extends SwingWatchmakerAction {

    private static final long serialVersionUID = 4121419685469500509L;

    public ActionEngineering() {
        super("Engineering",
                new ImageIcon(((AWTClassicImage) ClassicImageLoaderService
                        .getInstance().getClassicImageLoader()
                        .getPicture("IconEngineering_ALAN_32x32")).getImage()
                                .getScaledInstance(32, 32,
                                        Image.SCALE_DEFAULT)));
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        AppData appData = getAppData();
        Morph morph = appData.getMorphOfTheHour();
        appData.addEngineeringMorphView(morph);
    }

}
