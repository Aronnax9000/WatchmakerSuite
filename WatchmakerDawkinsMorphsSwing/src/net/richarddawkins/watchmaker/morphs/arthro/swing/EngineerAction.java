package net.richarddawkins.watchmaker.morphs.arthro.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import net.richarddawkins.watchmaker.gui.SwingAppData;

public class EngineerAction extends AbstractAction {

	protected SwingAppData swingAppData;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JFrame frame = null;
	public EngineerAction(SwingAppData swingAppData) {
		super("Engineer");
		this.swingAppData = swingAppData;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(frame == null) {
			frame = new ArthromorphsEngineer(swingAppData);
	
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	
			frame.pack();
		}
//		frame.setLocationRelativeTo(WatchmakerGUI.INSTANCE);
		frame.setVisible(true);
		
	}

}
