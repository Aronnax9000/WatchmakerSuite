package net.richarddawkins.watchmaker.swing.menu;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

public class ActionStartTimer extends SwingWatchmakerAction {

	private static final long serialVersionUID = 4121419685469500509L;

	public ActionStartTimer() {
		super("Start Timer", null, KeyStroke.getKeyStroke(KeyEvent.VK_T, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	    getAppData().startTimedBreed();
	}

}