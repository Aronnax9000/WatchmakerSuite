package net.richarddawkins.watchmaker.swing.menu;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import net.richarddawkins.watchmaker.app.AppData;

public class ActionTriangleMakeTop  extends SwingWatchmakerAction {
    private static final long serialVersionUID = 4121419685469500509L;
    
    public ActionTriangleMakeTop(AppData appData, String name, Icon icon) {
        super(appData, name, icon);
        this.appData = appData;
    }
    public ActionTriangleMakeTop(AppData appData) {
        this(appData, "Make top of triangle", null);
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        appData.getMorphConfig().getTriangleMorphs()[0] = appData.getMorphOfTheHour();
    }
}
