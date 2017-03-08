package net.richarddawkins.watchmaker.swing.engineer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.cursor.WatchmakerCursor;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.engineer.EngineeringMorphViewPanel;
import net.richarddawkins.watchmaker.resourceloader.Messages;
import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewPanel;

public class SwingEngineeringMorphViewPanel extends SwingMorphViewPanel implements EngineeringMorphViewPanel {
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.engineer.SwingEngineeringMorphViewPanel");

    private static final long serialVersionUID = 1L;

    @Override
    public boolean getIncludeChildrenInAutoScale() {
        return false;
    }

    public SwingEngineeringMorphViewPanel(MorphView morphView,
            BoxedMorphCollection page) {
        super(morphView,
                new BoxedMorphCollection("backing", morphView.newBoxManager()));
        setCursor(cursors.getCursor(WatchmakerCursor.hypodermic));
    }

    @Override
    protected void processMouseClicked(Point point, Dim size) {
        logger.fine("Showing hypodermic message dialog");
        Object[] options = { "Okay" };
        JOptionPane.showOptionDialog(this, new HypodermicWarning(), null,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                options, options[0]);
    }

    @Override
    public synchronized void paintMorphViewPanel(Object graphicsContext,
            Dim size) {
        if (autoScale) {
            autoScaleBasedOnMorphs(
                    boxedMorphCollection.getBoxManager().getBox(0));
        }
        super.paintMorphViewPanel(graphicsContext, size);
    }

    class HypodermicWarning extends JPanel {
        private static final long serialVersionUID = 1L;

        public HypodermicWarning() {
            setLayout(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.ipadx = 16;
            constraints.ipady = 16;
            JLabel imageLabel = new JLabel(new ImageIcon(ClassicImageLoader
                    .getPicture("Hypodermic_PICT_03937_16x16").getImage()
                    .getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
            add(imageLabel, constraints);
            JLabel warningLabel = new JLabel(Messages.getMessages()
                    .getString("EngineeringHypodermicWarning"));
            constraints.gridx++;
            add(warningLabel, constraints);
        }

    }

}
