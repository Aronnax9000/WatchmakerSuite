package net.richarddawkins.watchmaker.morph.arthro.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import net.richarddawkins.watchmaker.gui.SwingAppData;

public class PreferencesAction extends AbstractAction {

	protected SwingAppData swingAppData;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JFrame frame = null;
	public PreferencesAction(SwingAppData swingAppData) {
		super("Preferences");
		this.swingAppData = swingAppData;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(frame == null) {
			frame = new ArthromorphPreferences(swingAppData);
	
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	
			frame.pack();
		}
//		frame.setLocationRelativeTo(WatchmakerGUI.INSTANCE);
		frame.setVisible(true);
		
	}

}
