package net.richarddawkins.watchmaker.morphs.arthro;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import net.richarddawkins.watchmaker.ClassicImageLoader;
import net.richarddawkins.watchmaker.WatchmakerGUI;

public class AboutArthromorphsAction extends AbstractAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public AboutArthromorphsAction() {
    super("About Arthromorphs");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JOptionPane.showMessageDialog(WatchmakerGUI.INSTANCE,
        new ImageIcon(
            ClassicImageLoader.getPicture("AboutArthromorphs_PICT_00001_282x107").getImage()),
        "About Blind Watchmaker", JOptionPane.PLAIN_MESSAGE, null);
  }
}
