package net.richarddawkins.watchmaker.swing.engineer;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.GridBoxManager;
import net.richarddawkins.watchmaker.geom.LocatedMorph;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.resourceloader.Messages;
import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewGridBoxManaged;

public class SwingEngineeringMorphView extends SwingMorphViewGridBoxManaged {
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.engineer.SwingEngineeringMorphView");

    public SwingEngineeringMorphView(AppData appData, Morph morph) {
        super(appData, "Hypodermic_PICT_03937_16x16", "Engineering", true,
                appData.isGeneBoxToSide(), null);
        boxedMorphCollection.setBoxes(new GridBoxManager(1, 1));
        ((Component) this.getCentrePanel())
                .setCursor(WatchmakerCursors.hypodermic);
        MorphConfig config = appData.getMorphConfig();

        if (morph != null) {
            Morph copy = config.copyMorph(morph);
            seed(copy);
        } else {
            morph = config.newMorph(0);
            seed(morph);
        }

    }

    @Override
    public Morph getMorphOfTheHour() {
        return boxedMorphCollection.getBoxedMorphs().lastElement().getMorph();
    }

    @Override
    public void seed(Morph morph) {
        if (!boxedMorphCollection.isEmpty()) {
            for (BoxedMorph boxedMorph : boxedMorphCollection.getBoxedMorphs()) {
                boxedMorph.getMorph().removePropertyChangeListener(this);
            }
            boxedMorphCollection.removeAllElements();
        }
        morph.addPropertyChangeListener(this);
        BoxManager boxes = boxedMorphCollection.getBoxes();
        BoxedMorph boxedMorph = new BoxedMorph(boxes, morph, boxes.getBox(0));
        boxedMorphCollection.add(boxedMorph);
        pcs.firePropertyChange("genome", null,
                boxedMorph.getMorph().getGenome());
        backup(true);

    }

    @Override
    public void undo() {
        super.undo();
        
        LocatedMorph boxedMorph = boxedMorphCollection.firstElement();
        Morph morph = boxedMorph.getMorph();
        morph.addPropertyChangeListener(this);
        pcs.firePropertyChange("genome", null,
                boxedMorph.getMorph().getGenome());
        
    }
    @Override
    public void redo() {
        super.redo();
        LocatedMorph boxedMorph = boxedMorphCollection.firstElement();
        Morph morph = boxedMorph.getMorph();
        morph.addPropertyChangeListener(this);
        pcs.firePropertyChange("genome", null,
                boxedMorph.getMorph().getGenome());
    }
    
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
// too late?
        if (evt.getPropertyName().equals("genome")) {
            backup(true);
        }
        super.propertyChange(evt);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 8224824610112892419L;

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

    @Override
    protected void processMouseClicked(Point point, Dim size) {
        logger.info("Showing hypodermic message dialog");
        Object[] options = { "Okay" };
        JOptionPane.showOptionDialog(this, new HypodermicWarning(), null,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                options, options[0]);

    }

    @Override
    protected void processMouseMotion(Point myPt, Dim size) {
        // TODO Auto-generated method stub

    }

}
