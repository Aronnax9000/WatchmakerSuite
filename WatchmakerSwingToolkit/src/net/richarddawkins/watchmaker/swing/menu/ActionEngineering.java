package net.richarddawkins.watchmaker.swing.menu;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.morph.Morph;

public class ActionEngineering extends SwingWatchmakerAction {


	private static final long serialVersionUID = 4121419685469500509L;
	protected AppData appData;
	
	public ActionEngineering(AppData appData, String name, Icon icon) {
		super(appData, name, icon);
		this.appData = appData;
	}
	public ActionEngineering(AppData appData) {
		this(appData, "Engineering", null);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	    Morph morph = appData.getMorphOfTheHour();
	    appData.addEngineeringMorphView(morph);
	}

}
