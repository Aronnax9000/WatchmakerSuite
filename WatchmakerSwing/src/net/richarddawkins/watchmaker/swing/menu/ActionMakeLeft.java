package net.richarddawkins.watchmaker.swing.menu;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import net.richarddawkins.watchmaker.app.AppData;

public class ActionMakeLeft  extends SwingWatchmakerAction {
    private static final long serialVersionUID = 4121419685469500509L;
    
    public ActionMakeLeft(AppData appData, String name, Icon icon) {
        super(appData, name, icon);
        this.appData = appData;
    }
    public ActionMakeLeft(AppData appData) {
        this(appData, "Draw Out Offspring", null);
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
       
    }
}
