package net.richarddawkins.watchmaker.morph.snails;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

public class EngineerAction extends AbstractAction {

	protected ArthromorphConfig config;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JFrame frame = null;
	public EngineerAction(ArthromorphConfig config) {
		super("Engineer");
		this.config = config;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(frame == null) {
			frame = new ArthromorphsEngineer(config);
	
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	
			frame.pack();
		}
//		frame.setLocationRelativeTo(WatchmakerGUI.INSTANCE);
		frame.setVisible(true);
		
	}

}
