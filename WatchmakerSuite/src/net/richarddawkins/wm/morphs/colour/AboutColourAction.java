package net.richarddawkins.wm.morphs.colour;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import net.richarddawkins.wm.ClassicImageLoader;
import net.richarddawkins.wm.WatchmakerGUI;

public class AboutColourAction extends AbstractAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public AboutColourAction() {
    super("About Colour Biomorphs");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JOptionPane.showMessageDialog(WatchmakerGUI.INSTANCE,
        new ImageIcon(
            ClassicImageLoader.getPicture("AboutColourWatchmaker_PICT_00257_486x352").getImage()),
        "About Colour Blind Watchmaker", JOptionPane.PLAIN_MESSAGE, null);
  }

}
