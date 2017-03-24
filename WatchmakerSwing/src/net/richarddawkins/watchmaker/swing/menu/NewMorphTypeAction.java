package net.richarddawkins.watchmaker.swing.menu;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.app.AppDataFactory;
import net.richarddawkins.watchmaker.app.AppDataFactoryService;
import net.richarddawkins.watchmaker.swing.zoo.SwingMultiMorphTypeTabbedPanel;
public class NewMorphTypeAction extends SwingWatchmakerAction {

	private static final long serialVersionUID = -4736552636289435148L;

	
	public NewMorphTypeAction(String morphType, Icon icon) {
		super(morphType, icon);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		AppDataFactory factory = 
		AppDataFactoryService.getInstance().getFactory();
		
		factory.setMorphType((String)this.getValue(NAME));
		AppData newSwingAppData = factory.newAppData();
		SwingMultiMorphTypeTabbedPanel.getInstance().addAppData(newSwingAppData);
	}
	
}
