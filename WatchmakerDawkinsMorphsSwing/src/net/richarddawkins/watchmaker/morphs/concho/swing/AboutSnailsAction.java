package net.richarddawkins.watchmaker.morphs.concho.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;

public class AboutSnailsAction extends AbstractAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public Component component;
  public AboutSnailsAction(Component component	) {
    super("About Snailmaker");
    this.component = component;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(component),
        new ImageIcon(
            ClassicImageLoader.getPicture("AboutSnailsSnikwad_PICT_00002_145x113").getImage()),
        "About Blind Snailmaker", JOptionPane.PLAIN_MESSAGE, null);

  }

}
