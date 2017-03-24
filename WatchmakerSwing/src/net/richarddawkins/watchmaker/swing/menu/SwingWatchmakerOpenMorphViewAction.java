package net.richarddawkins.watchmaker.swing.menu;

import java.awt.event.ActionEvent;
import java.util.Vector;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.MorphViewFactory;
import net.richarddawkins.watchmaker.morphview.MorphViewFactoryService;

public class SwingWatchmakerOpenMorphViewAction extends SwingWatchmakerAction {


    private static final long serialVersionUID = 1L;

    public SwingWatchmakerOpenMorphViewAction(String name) {
        super(name);
        
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        MorphViewFactory factory = 
                MorphViewFactoryService.getInstance().getFactory();
        AppData appData = getAppData();
        Morph morphOfTheHour = appData.getMorphOfTheHour();
        Vector<Morph> seedMorphs = new Vector<Morph>();
        if(morphOfTheHour != null) {
            seedMorphs.add(morphOfTheHour);
        }
        MorphView morphView = factory.getMorphView(appData, (String)this.getValue(NAME), seedMorphs, null);
        appData.getMorphViewsTabbedPane().addMorphView(morphView);
    }
    
}
