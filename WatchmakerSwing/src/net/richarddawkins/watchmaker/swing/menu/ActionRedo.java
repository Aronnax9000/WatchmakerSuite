package net.richarddawkins.watchmaker.swing.menu;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

public class ActionRedo extends SwingWatchmakerAction {

	private static final long serialVersionUID = 4121419685469500509L;

	public ActionRedo() {
		super("Redo", null, KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	    getAppData().getSelectedMorphView().redo();
	}

}