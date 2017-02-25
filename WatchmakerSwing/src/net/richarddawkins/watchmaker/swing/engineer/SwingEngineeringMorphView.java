package net.richarddawkins.watchmaker.swing.engineer;

import java.beans.PropertyChangeEvent;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.GridBoxManager;
import net.richarddawkins.watchmaker.geom.LocatedMorph;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewConfig;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewGridBoxManaged;

public class SwingEngineeringMorphView extends SwingMorphViewGridBoxManaged {
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.engineer.SwingEngineeringMorphView");

    protected BoxedMorphCollection boxedMorphCollection;

    public SwingEngineeringMorphView(SwingMorphViewConfig config) {
        super(config);
        BoxManager boxManager = new GridBoxManager(1, 1);
        boxedMorphCollection = new BoxedMorphCollection("backing", boxManager);
        MorphViewPanel panel = new SwingEngineeringMorphViewPanel(this,
                boxedMorphCollection);
        addPanel(panel);
        if (seedMorphs.isEmpty()) {
            MorphConfig morphConfig = appData.getMorphConfig();
            seedMorphs.add(morphConfig.newMorph(0));
        }
    }

    @Override
    public void seed() {
        if (!seedMorphs.isEmpty()) {
            synchronized (seedMorphs) {
                if (!boxedMorphCollection.isEmpty()) {
                    for (BoxedMorph boxedMorph : boxedMorphCollection
                            .getBoxedMorphs()) {
                        boxedMorph.getMorph()
                                .removePropertyChangeListener(this);
                    }
                    boxedMorphCollection.removeAllElements();
                }
                BoxManager boxes = boxedMorphCollection.getBoxes();
                int index = 0;
                for (Morph morph : seedMorphs) {
                    BoxedMorph boxedMorph = new BoxedMorph(boxes, morph,
                            boxes.getBox(index++));
                    morph.addPropertyChangeListener(this);
                    boxedMorphCollection.add(boxedMorph);
                }
            }
        }
        backup(true);

    }

    @Override
    public void undo() {
        super.undo();

        LocatedMorph boxedMorph = album.firstElement().firstElement();
        Morph morph = boxedMorph.getMorph();
        morph.addPropertyChangeListener(this);
    }

    @Override
    public void redo() {
        super.redo();
        LocatedMorph boxedMorph = album.firstElement().firstElement();
        Morph morph = boxedMorph.getMorph();
        morph.addPropertyChangeListener(this);
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

}
