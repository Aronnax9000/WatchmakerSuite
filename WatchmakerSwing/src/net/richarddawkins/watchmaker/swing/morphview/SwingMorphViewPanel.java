package net.richarddawkins.watchmaker.swing.morphview;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Logger;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;
import net.richarddawkins.watchmaker.swing.SwingGeom;

public class SwingMorphViewPanel extends JPanel implements MorphViewPanel {

        private static final long serialVersionUID = 1L;
        private static Logger logger = Logger.getLogger(
                "net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewPanel");
        private MorphView morphView;
        SwingMorphViewPanel(MorphView morphView) {
            this.morphView = morphView;
        }
        @Override
        public void paintComponent(Graphics g) {
            logger.fine("centrePanel.paintComponent()");
            morphView.paintMorphViewPanel((Graphics2D) g,
                    SwingGeom.toWatchmakerDim(this.getSize()));
        }

}
