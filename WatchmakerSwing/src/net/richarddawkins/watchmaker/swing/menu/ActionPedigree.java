package net.richarddawkins.watchmaker.swing.menu;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import net.richarddawkins.watchmaker.app.AppData;

public class ActionPedigree  extends SwingWatchmakerAction {
    private static final long serialVersionUID = 4121419685469500509L;
    
    public ActionPedigree(AppData appData, String name, Icon icon) {
        super(appData, name, icon);
        this.appData = appData;
    }
    public ActionPedigree(AppData appData) {
        this(appData, "Display Pedigree", null);
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        
        appData.addPedigreeMorphView();
    }
}
