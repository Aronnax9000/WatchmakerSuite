package net.richarddawkins.watchmaker.morphs.mono.menu.swing;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class ActionTriangle extends SwingWatchmakerAction {


	private static final long serialVersionUID = 4121419685469500509L;
	protected AppData appData;
	
	public ActionTriangle(AppData appData, String name, Icon icon) {
		super(appData, name, icon);
		this.appData = appData;
	}
	public ActionTriangle(AppData appData) {
		this(appData, "Triangle", null);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	    appData.addTriangleMorphView();
	}

}
