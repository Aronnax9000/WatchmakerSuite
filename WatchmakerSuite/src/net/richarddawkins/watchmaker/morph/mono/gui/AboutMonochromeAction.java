package net.richarddawkins.watchmaker.morph.mono.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.richarddawkins.watchmaker.resourceloader.ClassicImageLoader;

public class AboutMonochromeAction extends AbstractAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private JFrame frame;
  public AboutMonochromeAction(JFrame frame) {
    super("About Monochrome Biomorphs");
    this.frame = frame;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
	  
    JOptionPane.showMessageDialog(frame,
        new ImageIcon(
            ClassicImageLoader.getPicture("AboutBlindWatchmaker_PICT_26817_463x287").getImage()),
        "About Blind Watchmaker", JOptionPane.PLAIN_MESSAGE, null);
  
  }

}
