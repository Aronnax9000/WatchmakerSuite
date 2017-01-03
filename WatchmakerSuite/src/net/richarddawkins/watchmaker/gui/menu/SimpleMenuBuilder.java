package net.richarddawkins.watchmaker.gui.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import net.richarddawkins.watchmaker.morph.common.MorphConfig;
import net.richarddawkins.watchmaker.morph.util.ActionQuit;

public abstract class SimpleMenuBuilder implements MenuBuilder {
  protected MorphConfig config;

  protected void addFileQuitAction(JMenu menu) {
    menu.add(new JMenuItem(new ActionQuit(menu)));
  }

  protected JCheckBoxMenuItem viewBoundingBoxes = new JCheckBoxMenuItem(
      new AbstractAction("View Bounding Boxes") {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
/*          config.setShowBoundingBoxes(viewBoundingBoxes.isSelected());
          BreedingPanel breedingPanel = config.getBreedingAndGeneBoxPanel().getBreedingPanel();
          breedingPanel.repaint();
          breedingPanel.revalidate(); */
        }
      });

}
