package net.richarddawkins.watchmaker.swing.menu;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import net.richarddawkins.watchmaker.app.AppData;

public class ActionTriangleMakeRight  extends SwingWatchmakerAction {
    private static final long serialVersionUID = 4121419685469500509L;
    protected AppData appData;
    
    public ActionTriangleMakeRight(AppData appData, String name, Icon icon) {
        super(appData, name, icon);
        this.appData = appData;
    }
    public ActionTriangleMakeRight(AppData appData) {
        this(appData, "Make right of triangle", null);
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        appData.getMorphConfig().getTriangleMorphs()[2] = appData.getMorphOfTheHour();
    }
}
