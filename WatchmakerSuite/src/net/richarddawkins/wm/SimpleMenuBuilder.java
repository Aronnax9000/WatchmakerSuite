package net.richarddawkins.wm;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import net.richarddawkins.watchmaker.gui.menu.MenuBuilder;
import net.richarddawkins.watchmaker.morph.common.MorphConfig;

public abstract class SimpleMenuBuilder implements MenuBuilder {
  protected MorphConfig config;

  protected void addFileQuitAction(JMenu menu) {
    menu.add(new JMenuItem(new ActionQuit()));
  }

  protected JCheckBoxMenuItem viewBoundingBoxes = new JCheckBoxMenuItem(
      new AbstractAction("View Bounding Boxes") {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
          config.setShowBoundingBoxes(viewBoundingBoxes.isSelected());
          BreedingPanelOld breedingPanelOld = config.getBreedingPanel();
          breedingPanelOld.repaint();
          breedingPanelOld.revalidate();
        }
      });

}
