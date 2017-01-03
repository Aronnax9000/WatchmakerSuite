package net.richarddawkins.watchmaker.gui.old;

import javax.swing.JApplet;

public class WatchmakerApplet extends JApplet {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public void init() {
    try {
       javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
          public void run() {
             createGUI();
          }
       });
    } catch (Exception e) {
       System.err.println("createGUI didn't successfully complete");
    }
 }

 private void createGUI() {
    getContentPane().add(new WatchmakerGUI(this));
 }
}
