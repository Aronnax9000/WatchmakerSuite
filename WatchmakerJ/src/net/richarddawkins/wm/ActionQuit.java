package net.richarddawkins.watchmaker;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class ActionQuit extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActionQuit() {
		super("Quit");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
//		WatchmakerGUI.INSTANCE.dispatchEvent(
//				new WindowEvent(WatchmakerGUI.INSTANCE, 
//						WindowEvent.WINDOW_CLOSING));
		
	}

}
