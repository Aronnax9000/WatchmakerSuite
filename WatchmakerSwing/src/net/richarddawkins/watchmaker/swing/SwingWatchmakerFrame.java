package net.richarddawkins.watchmaker.swing;

import java.awt.BorderLayout;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenuBar;
import net.richarddawkins.watchmaker.swing.zoo.SwingMultiMorphTypeTabbedPanel;

public class SwingWatchmakerFrame extends JFrame {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.WatchmakerFrame");

	private static SwingWatchmakerFrame frame;
	public static SwingWatchmakerFrame getInstance() {
		if(frame == null) {
	 		frame = new SwingWatchmakerFrame();
			// Display the window.
	 		logger.fine("Maximizing Window");
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			frame.pack();
			frame.setVisible(true);
			logger.fine("Finished setting visible");
            
		}
		return frame;
	}

	private static final long serialVersionUID = 1800967943270519085L;
	SwingMultiMorphTypeTabbedPanel morphTypePane;

	protected SwingWatchmakerFrame() {
		super("Watchmaker Suite by Richard Dawkins");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WatchmakerMenuBar menuBar = SwingWatchmakerMenuBar.getInstance();
		setJMenuBar((JMenuBar) menuBar);
		getContentPane().setLayout(new BorderLayout());
		morphTypePane = SwingMultiMorphTypeTabbedPanel.getInstance();

		getContentPane().add(morphTypePane);
	}

}
