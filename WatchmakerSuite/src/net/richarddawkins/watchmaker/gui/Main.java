package net.richarddawkins.watchmaker.gui;

import net.richarddawkins.watchmaker.resourceloader.InitFont;

public class Main {

    public static void main(String[] args) {

    	InitFont.initFonts();
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	WatchmakerFrame.newInstance();
            }
       
        });
    }
}
