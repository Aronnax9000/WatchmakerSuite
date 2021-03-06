package net.richarddawkins.watchmaker.swing.menu;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;

public class ActionQuit extends AbstractAction {
	private Component component;

	private static final long serialVersionUID = 1L;

	public ActionQuit(Component menu) {
		super("Quit");
		this.component = menu;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Window window = SwingUtilities.getWindowAncestor(component);
		window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));

	}

}
