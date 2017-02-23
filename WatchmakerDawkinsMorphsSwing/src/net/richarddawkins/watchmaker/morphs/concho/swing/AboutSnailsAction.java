package net.richarddawkins.watchmaker.morphs.concho.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class AboutSnailsAction extends SwingWatchmakerAction {

  private static final long serialVersionUID = 1L;
  public AboutSnailsAction(AppData appData) {
    super(appData, "About Snailmaker");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor((Component)e.getSource()),
        new ImageIcon(
            ClassicImageLoader.getPicture("AboutSnailsSnikwad_PICT_00002_145x113").getImage()),
        "About Blind Snailmaker", JOptionPane.PLAIN_MESSAGE, null);

  }

}
