package net.richarddawkins.watchmaker.morphs.arthro.swing;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class AboutArthromorphsAction extends SwingWatchmakerAction  {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  protected Component component;
  
  public AboutArthromorphsAction() {
    super("About Arthromorphs");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
	  Window window = SwingUtilities.getWindowAncestor(component);
	  JOptionPane.showMessageDialog(window,
        new ImageIcon(
            ClassicImageLoader.getPicture("AboutArthromorphs_PICT_00001_282x107").getImage()),
        "About Blind Watchmaker", JOptionPane.PLAIN_MESSAGE, null);
  }
}
