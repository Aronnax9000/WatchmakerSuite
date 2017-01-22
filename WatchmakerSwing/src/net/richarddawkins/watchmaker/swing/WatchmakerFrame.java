package net.richarddawkins.watchmaker.swing;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenuBar;
import net.richarddawkins.watchmaker.swing.zoo.SwingMultiMorphTypeTabbedPanel;

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
		morphTypePane = new SwingMultiMorphTypeTabbedPanel(menuBar);
		
		getContentPane().add(morphTypePane);
    }
      
}
