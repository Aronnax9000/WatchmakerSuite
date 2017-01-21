package net.richarddawkins.watchmaker.swing;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import net.richarddawkins.watchmaker.swing.wtp.SwingMultiMorphTypeTabbedPanel;
import net.richarddawkins.watchmaker.swing.wtp.SwingWatchmakerMenuBar;
import net.richarddawkins.watchmaker.swing.wtp.WatchmakerMenuBar;

public class WatchmakerFrame extends JFrame {
    
	public static void newInstance() {
    	WatchmakerFrame frame = new WatchmakerFrame();
        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}

	private static final long serialVersionUID = 1800967943270519085L;
	SwingMultiMorphTypeTabbedPanel morphTypePane;
	public WatchmakerFrame() {
		super("Watchmaker Suite by Richard Dawkins");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WatchmakerMenuBar menuBar = new SwingWatchmakerMenuBar();
		setJMenuBar((JMenuBar)menuBar);
		getContentPane().setLayout(new BorderLayout());
		morphTypePane = new SwingMultiMorphTypeTabbedPanel();
		morphTypePane.setMenuBar(menuBar);
		getContentPane().add(morphTypePane);
    }
      
}
