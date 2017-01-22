package net.richarddawkins.watchmaker.morphs.arthro.swing;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class EngineerAction extends SwingWatchmakerAction {


	private static final long serialVersionUID = 1L;
	public static JFrame frame = null;
	public EngineerAction(AppData appData) {
		super("Engineer");
		this.appData = appData;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(frame == null) {
			frame = new ArthromorphsEngineer(appData);
	
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	
			frame.pack();
		}
//		frame.setLocationRelativeTo(WatchmakerGUI.INSTANCE);
		frame.setVisible(true);
		
	}

}
