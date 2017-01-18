package net.richarddawkins.watchmaker.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import net.richarddawkins.watchmaker.morph.Morph;

public class ActionEngineering extends AbstractAction {


	private static final long serialVersionUID = 4121419685469500509L;
	protected SwingAppData swingAppData;
	
	public ActionEngineering(SwingAppData swingAppData, String name, Icon icon) {
		super(name, icon);
		this.swingAppData = swingAppData;
	}
	public ActionEngineering(SwingAppData swingAppData) {
		this(swingAppData, "Engineering", null);
		this.swingAppData = swingAppData;

	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	    Morph morph = swingAppData.getMorphOfTheHour();
	    swingAppData.addEngineeringMorphView(morph);
	}

}
