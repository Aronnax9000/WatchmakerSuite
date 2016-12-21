package net.richarddawkins.watchmaker.gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.gui.breed.BreedingPanel;
import net.richarddawkins.watchmaker.morph.common.MorphConfig;
import net.richarddawkins.watchmaker.morph.mono.MonochromeMorphConfig;

public class Main {

	
	
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
    	JFrame frame = new WatchmakerFrame();
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        MorphConfig morphConfig = new MonochromeMorphConfig();
        morphConfig.setFrame(frame);
        morphConfig.getMenuBuilder().buildMenu(frame.getJMenuBar());
        JPanel centerPane = new BreedingPanel(morphConfig);
        contentPane.add(centerPane, BorderLayout.CENTER);
        contentPane.add(morphConfig.getGeneBoxStrip(), BorderLayout.PAGE_START);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
    	

    	
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
       
        });
    }
}
