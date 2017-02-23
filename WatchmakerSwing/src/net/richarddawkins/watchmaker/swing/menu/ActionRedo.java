package net.richarddawkins.watchmaker.swing.menu;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.KeyStroke;

import net.richarddawkins.watchmaker.app.AppData;

public class ActionRedo extends SwingWatchmakerAction {

	private static final long serialVersionUID = 4121419685469500509L;

	public ActionRedo(AppData appData) {
		super(appData, "Redo", null, KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.ALT_MASK));
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	    appData.getMorphViewsTabbedPane().getSelectedMorphView().redo();
	}

}