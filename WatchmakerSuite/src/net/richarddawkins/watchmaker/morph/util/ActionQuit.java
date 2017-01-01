package net.richarddawkins.watchmaker.morph.util;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

public class ActionQuit extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JFrame frame;
	public ActionQuit(JFrame frame) {
		super("Quit");
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		frame.dispatchEvent(
				new WindowEvent(frame, 
						WindowEvent.WINDOW_CLOSING));
		
	}

}