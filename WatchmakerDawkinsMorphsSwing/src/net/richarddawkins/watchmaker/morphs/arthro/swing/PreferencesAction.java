package net.richarddawkins.watchmaker.morphs.arthro.swing;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class PreferencesAction extends SwingWatchmakerAction {


	private static final long serialVersionUID = 1L;
	public static JFrame frame = null;

	public PreferencesAction(AppData appData) {
		super(appData, "Preferences");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(frame == null) {
			frame = new ArthromorphPreferences(
					appData.getPhenotypeDrawer().getDrawingPreferences());
	
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	
			frame.pack();
		}
		frame.setVisible(true);
		
	}

}
