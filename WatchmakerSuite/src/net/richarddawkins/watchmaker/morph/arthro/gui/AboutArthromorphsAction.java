package net.richarddawkins.watchmaker.morph.arthro.gui;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import net.richarddawkins.watchmaker.resourceloader.ClassicImageLoader;

public class AboutArthromorphsAction extends AbstractAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  protected Component component;
  
  public AboutArthromorphsAction(Component component) {
    super("About Arthromorphs");
    this.component = component;
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
