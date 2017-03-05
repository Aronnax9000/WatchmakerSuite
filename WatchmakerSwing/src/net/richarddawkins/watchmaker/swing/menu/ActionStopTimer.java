package net.richarddawkins.watchmaker.swing.menu;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import net.richarddawkins.watchmaker.app.AppData;

public class ActionStopTimer extends SwingWatchmakerAction {

	private static final long serialVersionUID = 4121419685469500509L;

	public ActionStopTimer(AppData appData) {
		super(appData, "Stop Timer", null, KeyStroke.getKeyStroke(KeyEvent.VK_I, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	    appData.stopTimedBreed();
	}

}