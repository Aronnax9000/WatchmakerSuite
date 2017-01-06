package net.richarddawkins.watchmaker.gui.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.richarddawkins.watchmaker.gui.WatchmakerTabbedPane;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.MorphConfigFactory;
import net.richarddawkins.watchmaker.morph.MorphType;
import net.richarddawkins.watchmaker.morph.MorphTypeNotSupportedException;

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
		
		try {
			MorphConfigFactory factory = MorphConfigFactory.getInstance(morphType);
			MorphConfig morphConfig = factory.createConfig();
			pane.addMorphConfig(morphConfig);
		} catch (MorphTypeNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
