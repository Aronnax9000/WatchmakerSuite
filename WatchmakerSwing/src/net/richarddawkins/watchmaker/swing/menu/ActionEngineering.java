package net.richarddawkins.watchmaker.swing.menu;

import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;

public class ActionEngineering extends SwingWatchmakerAction {

    private static final long serialVersionUID = 4121419685469500509L;
    protected AppData appData;

    public ActionEngineering(AppData appData, String name, Icon icon) {
        super(appData, name, icon);
        this.appData = appData;
    }

    public ActionEngineering(AppData appData) {
        this(appData, "Engineering",
                new ImageIcon(ClassicImageLoader
                        .getPicture("Hypodermic_PICT_03937_16x16").getImage()
                        .getScaledInstance(32, 32, Image.SCALE_DEFAULT)));
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        Morph morph = appData.getMorphOfTheHour();
        appData.addEngineeringMorphView(morph);
    }

}
