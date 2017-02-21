package net.richarddawkins.watchmaker.swing.menu;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import net.richarddawkins.watchmaker.app.AppData;

public class ActionTriangleMakeLeft  extends SwingWatchmakerAction {
    private static final long serialVersionUID = 4121419685469500509L;
    protected AppData appData;
    
    public ActionTriangleMakeLeft(AppData appData, String name, Icon icon) {
        super(appData, name, icon);
        this.appData = appData;
    }
    public ActionTriangleMakeLeft(AppData appData) {
        this(appData, "Make left of triangle", null);
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        appData.getMorphConfig().getTriangleMorphs()[1] = appData.getMorphOfTheHour();
    }
}
