package net.richarddawkins.watchmaker.swing.menubuilder;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import net.richarddawkins.watchmaker.swing.appdata.SwingAppData;
import net.richarddawkins.watchmaker.swing.appdata.SwingAppDataFactory;
import net.richarddawkins.watchmaker.swing.appdata.SwingAppDataFactoryService;
import net.richarddawkins.watchmaker.swing.wtp.SwingMultiMorphTypeTabbedPanel;
public class NewMorphTypeAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4736552636289435148L;
	
	protected SwingMultiMorphTypeTabbedPanel pane;
	public NewMorphTypeAction(String morphType, Icon icon, SwingMultiMorphTypeTabbedPanel SwingMultiMorphTypeTabbedPanel) {
		super(morphType, icon);
		this.pane = SwingMultiMorphTypeTabbedPanel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		SwingAppDataFactory factory = 
		SwingAppDataFactoryService.getInstance().getFactory();
		
		factory.setMorphType((String)this.getValue(NAME));
		SwingAppData newSwingAppData = factory.newSwingAppData();
		
		pane.addSwingAppData(newSwingAppData);
		
	}
	
}
