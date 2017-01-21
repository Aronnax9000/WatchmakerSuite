package net.richarddawkins.watchmaker.swing;
import java.awt.BorderLayout;

import javax.swing.JApplet;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

import net.richarddawkins.watchmaker.swing.wtp.SwingMultiMorphTypeTabbedPanel;
import net.richarddawkins.watchmaker.swing.wtp.SwingWatchmakerMenuBar;
import net.richarddawkins.watchmaker.swing.wtp.WatchmakerMenuBar;

public class WatchmakerApplet extends JApplet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7331548283345089340L;
	SwingMultiMorphTypeTabbedPanel morphTypePane;

    //Called when this applet is loaded into the browser.
    public void init() {
        //Execute a job on the event-dispatching thread; creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                	WatchmakerMenuBar menuBar = new SwingWatchmakerMenuBar();
                	setJMenuBar((JMenuBar)menuBar);
            		getContentPane().setLayout(new BorderLayout());
            		morphTypePane = new SwingMultiMorphTypeTabbedPanel();
            		morphTypePane.setMenuBar(menuBar);
            		getContentPane().add(morphTypePane);                }
            });
        } catch (Exception e) {
            System.err.println("createGUI didn't complete successfully");
        }
    }
}

