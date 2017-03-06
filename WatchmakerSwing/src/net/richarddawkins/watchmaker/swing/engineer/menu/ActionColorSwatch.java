package net.richarddawkins.watchmaker.swing.engineer.menu;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class ActionColorSwatch extends SwingWatchmakerAction {


	private static final long serialVersionUID = 4121419685469500509L;
	
	public ActionColorSwatch(AppData appData) {
		super(appData, "Color Swatch", null, KeyStroke.getKeyStroke(KeyEvent.VK_K, Event.ALT_MASK));
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {

	}
}