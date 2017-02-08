package net.richarddawkins.watchmaker.morphs.colour.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class AboutColourAction extends SwingWatchmakerAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;



  private Component component;
  public AboutColourAction(Component component) {
    super("About Colour Biomorphs");
    this.component = component;
  }


  @Override
  public void actionPerformed(ActionEvent e) {
	  
    JOptionPane.showMessageDialog(component,
        new ImageIcon(
            ClassicImageLoader.getPicture("AboutColourWatchmaker_PICT_00257_486x352").getImage()),
        "About Blind Watchmaker", JOptionPane.PLAIN_MESSAGE, null);
  
  }
}
