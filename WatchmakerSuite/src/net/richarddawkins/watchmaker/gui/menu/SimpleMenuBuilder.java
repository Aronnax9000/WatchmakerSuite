package net.richarddawkins.watchmaker.gui.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import net.richarddawkins.watchmaker.morph.common.MorphType;
import net.richarddawkins.watchmaker.morph.util.ActionQuit;

public abstract class SimpleMenuBuilder implements MenuBuilder {

  protected void addFileQuitAction(JMenu menu) {
    menu.add(new JMenuItem(new ActionQuit(menu)));
  }
  public void buildMenu(JMenuBar menuBar) {
    menuBar.removeAll();
    menuBar.add(buildWatchmakerMenu());
  }
	protected JMenu buildWatchmakerMenu() {
		JMenu watchMakerMenu = new JMenu("Watchmaker");
		for(MorphType morphType: MorphType.values())
		{
			watchMakerMenu.add(new NewMorphTypeAction(morphType, this.getMorphConfig().getFrame()));
		}
		return watchMakerMenu;
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
