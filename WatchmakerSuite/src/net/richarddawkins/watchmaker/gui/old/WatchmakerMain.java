package net.richarddawkins.watchmaker.gui.old;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class WatchmakerMain {
  /**
   * @param args
   *          the command line arguments
   */
  public static void main(String args[]) {

    java.awt.EventQueue.invokeLater(new Runnable() {

      public void run() {
        JFrame frame = new JFrame();
        JMenuBar menuBar = new JMenuBar();
        frame.setTitle("Watchmaker Suite by Richard Dawkins");
        
        try {
          frame.getContentPane().add(new WatchmakerGUI(menuBar));
        } catch (Exception e) {
          e.printStackTrace();
        }
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
      }
    });
  }
}
