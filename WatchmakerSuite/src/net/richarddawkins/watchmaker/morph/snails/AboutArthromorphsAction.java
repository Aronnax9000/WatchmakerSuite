package net.richarddawkins.watchmaker.morph.snails;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

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
//    JOptionPane.showMessageDialog(WatchmakerGUI.INSTANCE,
//        new ImageIcon(
//            ClassicImageLoader.getPicture("AboutArthromorphs_PICT_00001_282x107").getImage()),
//        "About Blind Watchmaker", JOptionPane.PLAIN_MESSAGE, null);
  }
}
