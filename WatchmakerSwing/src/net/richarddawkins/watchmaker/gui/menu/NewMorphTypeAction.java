package net.richarddawkins.watchmaker.gui.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.richarddawkins.watchmaker.gui.SwingAppData;
import net.richarddawkins.watchmaker.gui.SwingAppDataFactory;
import net.richarddawkins.watchmaker.gui.SwingAppDataFactoryService;
import net.richarddawkins.watchmaker.gui.WatchmakerTabbedPane;
import net.richarddawkins.watchmaker.morph.MorphType;

public class NewMorphTypeAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4736552636289435148L;
	protected MorphType morphType;
	protected WatchmakerTabbedPane pane;
	public NewMorphTypeAction(MorphType morphType, WatchmakerTabbedPane pane) {
		super(morphType.getName(), morphType.getIcon());
		this.morphType = morphType;
		this.pane = pane;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		SwingAppDataFactory factory = 
		SwingAppDataFactoryService.getInstance().getFactory("Dawkins' Morphs");
		
		factory.setMorphType(morphType);
		SwingAppData newSwingAppData = factory.newSwingAppData();
		
		pane.addSwingAppData(newSwingAppData);
		
	}
	
}
