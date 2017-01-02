package net.richarddawkins.watchmaker.morph.util;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

public class ActionQuit extends AbstractAction {
	  private Component component;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	public ActionQuit(Component component)
 {
		
		super("Quit");
		this.component = component;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		component.dispatchEvent(
				new WindowEvent(frame, 
						WindowEvent.WINDOW_CLOSING));
		
	}

}
