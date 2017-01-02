package net.richarddawkins.watchmaker.gui;
import java.awt.BorderLayout;

import javax.swing.JApplet;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

public class WatchmakerApplet extends JApplet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7331548283345089340L;
	WatchmakerTabbedPane morphTypePane;

    //Called when this applet is loaded into the browser.
    public void init() {
        //Execute a job on the event-dispatching thread; creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                	setJMenuBar(new JMenuBar());
            		getContentPane().setLayout(new BorderLayout());
            		morphTypePane = new WatchmakerTabbedPane(getJMenuBar());
            		getContentPane().add(morphTypePane);                }
            });
        } catch (Exception e) {
            System.err.println("createGUI didn't complete successfully");
        }
    }
}

