package net.richarddawkins.watchmaker.swing.engineer;

import java.beans.PropertyChangeEvent;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.album.Album;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
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

    
    @Override
    public BoxManager newBoxManager() {
        BoxManager boxManager = new GridBoxManager(1, 1);
        return boxManager;
    }
    
    @Override
    public void addPanels() {
        MorphViewPanel panel = new SwingEngineeringMorphViewPanel(this,
                new BoxedMorphCollection("backing", newBoxManager()));
        addPanel(panel);   
    }
    public SwingEngineeringMorphView(SwingMorphViewConfig config) {
        super(config);
    }

    @Override
    public void seed() {
        if (!seedMorphs.isEmpty()) {
            synchronized (seedMorphs) {
                BoxedMorphCollection boxedMorphs = panels.firstElement().getBoxedMorphCollection();
                if (!boxedMorphs.isEmpty()) {
                    for (BoxedMorph boxedMorph : boxedMorphs
                            .getBoxedMorphs()) {
                        boxedMorph.getMorph()
                                .removePropertyChangeListener(this);
                    }
                    boxedMorphs.removeAllElements();
                }
                BoxManager boxes = boxedMorphs.getBoxes();
                int index = 0;
                for (Morph morph : seedMorphs) {
                    BoxedMorph boxedMorph = new BoxedMorph(boxes, morph,
                            boxes.getBox(index++));
                    morph.addPropertyChangeListener(this);
                    boxedMorphs.add(boxedMorph);
                }
                // GEneBoxStrip shouldbe told? How does Breeding do it?
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
    @Override
    public void addSeedMorphs(Vector<Morph> seedMorphsToAdd) {
        super.addSeedMorphs(seedMorphsToAdd);
        if (this.seedMorphs.isEmpty()) {
            MorphConfig morphConfig = appData.getMorphConfig();
            this.seedMorphs.add(morphConfig.newMorph(0));
        }
        
    }

}
