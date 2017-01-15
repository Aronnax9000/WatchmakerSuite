package net.richarddawkins.watchmaker.gui.menu;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import net.richarddawkins.watchmaker.gui.WatchmakerTabbedPane;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.MorphType;
import net.richarddawkins.watchmaker.morph.util.ActionQuit;

public abstract class SimpleMenuBuilder implements MenuBuilder, PropertyChangeListener {

    protected MorphConfig config;

    public SimpleMenuBuilder(MorphConfig config) {
        this.config = config;
        config.addPropertyChangeListener(this);
        viewBoundingBoxes.setSelected(config.isShowBoundingBoxes());
    }

    protected void addFileQuitAction(JMenu menu) {
        menu.add(new JMenuItem(new ActionQuit(menu)));
    }

    public void buildMenu(JMenuBar menuBar) {
        menuBar.removeAll();
        menuBar.add(buildWatchmakerMenu());
    }

    protected void addColourTestAction(JMenu menu) {
        WatchmakerTabbedPane frame = config.getFrame();
        menu.add(new JMenuItem(new ColourTestAction(frame)));

    }

    protected JMenu buildWatchmakerMenu() {
        JMenu watchMakerMenu = new JMenu("Watchmaker");
        for (MorphType morphType : MorphType.values()) {
            WatchmakerTabbedPane frame = config.getFrame();
            NewMorphTypeAction morphTypeAction = new NewMorphTypeAction(morphType, frame);
            if(morphType == MorphType.SNAIL) 
                morphTypeAction.setEnabled(false);
            watchMakerMenu.add(morphTypeAction);
        }
        addColourTestAction(watchMakerMenu);
        addFileQuitAction(watchMakerMenu);
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
            config.setShowBoundingBoxes(viewBoundingBoxes.isSelected());
            /*
             * BreedingPanel breedingPanel =
             * config.getBreedingAndGeneBoxPanel().getBreedingPanel();
             * breedingPanel.repaint(); breedingPanel.revalidate();
             */
        }
    });

    @Override
    public void setMorphConfig(MorphConfig config) {
        this.config = config;

    }

    @Override
    public MorphConfig getMorphConfig() {
        return config;
    }
}
