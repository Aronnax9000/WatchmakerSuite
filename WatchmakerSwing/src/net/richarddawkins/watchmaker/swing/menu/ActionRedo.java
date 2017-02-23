package net.richarddawkins.watchmaker.swing.menu;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import net.richarddawkins.watchmaker.app.AppData;

public class ActionRedo extends SwingWatchmakerAction {

	private static final long serialVersionUID = 4121419685469500509L;
	protected AppData appData;
	
	public ActionRedo(AppData appData, String name, Icon icon) {
		super(appData, name, icon);
		this.appData = appData;
	}
	public ActionRedo(AppData appData) {
		this(appData, "Redo", null);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	    appData.getMorphViewsTabbedPane().getSelectedMorphView().redo();
	}

}