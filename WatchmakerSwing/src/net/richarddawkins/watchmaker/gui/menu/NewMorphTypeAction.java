package net.richarddawkins.watchmaker.gui.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import net.richarddawkins.watchmaker.gui.SwingAppData;
import net.richarddawkins.watchmaker.gui.SwingAppDataFactory;
import net.richarddawkins.watchmaker.gui.SwingAppDataFactoryService;
import net.richarddawkins.watchmaker.gui.WatchmakerTabbedPane;

public class NewMorphTypeAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4736552636289435148L;
	
	protected WatchmakerTabbedPane pane;
	public NewMorphTypeAction(String morphType, Icon icon, WatchmakerTabbedPane pane) {
		super(morphType, icon);
		this.pane = pane;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		SwingAppDataFactory factory = 
		SwingAppDataFactoryService.getInstance().getFactory("Dawkins' Morphs");
		
		factory.setMorphType((String)this.getValue(NAME));
		SwingAppData newSwingAppData = factory.newSwingAppData();
		
		pane.addSwingAppData(newSwingAppData);
		
	}
	
}
