package net.richarddawkins.watchmaker.morphs.mono.menu.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import net.richarddawkins.watchmaker.app.MultiMorphTypeTabbedPanel;
import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;

public class AboutMonochromeAction extends AbstractAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Component component;
  public AboutMonochromeAction(MultiMorphTypeTabbedPanel multiMorphTypeTabbedPanel) {
    super("About Monochrome Biomorphs");
    this.component = (Component) multiMorphTypeTabbedPanel;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
	  
    JOptionPane.showMessageDialog(component,
        new ImageIcon(
            ClassicImageLoader.getPicture("AboutBlindWatchmaker_PICT_26817_463x287").getImage()),
        "About Blind Watchmaker", JOptionPane.PLAIN_MESSAGE, null);
  
  }

}