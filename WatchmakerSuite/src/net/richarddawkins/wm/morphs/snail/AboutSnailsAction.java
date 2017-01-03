package net.richarddawkins.wm.morphs.snail;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.richarddawkins.watchmaker.resourceloader.ClassicImageLoader;
import net.richarddawkins.wm.WatchmakerGUI;

public class AboutSnailsAction extends AbstractAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public static JFrame frame = null;

  public AboutSnailsAction() {
    super("About Snailmaker");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JOptionPane.showMessageDialog(WatchmakerGUI.INSTANCE,
        new ImageIcon(
            ClassicImageLoader.getPicture("AboutSnailsSnikwad_PICT_00002_145x113").getImage()),
        "About Blind Snailmaker", JOptionPane.PLAIN_MESSAGE, null);

  }

}