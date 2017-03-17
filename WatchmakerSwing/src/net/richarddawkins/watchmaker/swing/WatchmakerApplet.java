package net.richarddawkins.watchmaker.swing;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JApplet;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenuBar;
import net.richarddawkins.watchmaker.swing.zoo.SwingMultiMorphTypeTabbedPanel;

public class WatchmakerApplet extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7331548283345089340L;
	SwingMultiMorphTypeTabbedPanel morphTypePane;

	// Called when this applet is loaded into the browser.
	public void init() {
		// Execute a job on the event-dispatching thread; creating this applet's
		// GUI.
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					WatchmakerMenuBar menuBar = SwingWatchmakerMenuBar.getInstance();
					setJMenuBar((JMenuBar) menuBar);
					getContentPane().setLayout(new BorderLayout());
					morphTypePane = SwingMultiMorphTypeTabbedPanel.getInstance();
					getContentPane().add((Component)morphTypePane.getComponent());
				}
			});
		} catch (Exception e) {
			System.err.println("createGUI didn't complete successfully");
		}
	}
}
