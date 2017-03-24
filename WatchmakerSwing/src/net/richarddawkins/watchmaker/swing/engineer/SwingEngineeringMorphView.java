package net.richarddawkins.watchmaker.swing.engineer;

import java.beans.PropertyChangeEvent;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.GridBoxManager;
import net.richarddawkins.watchmaker.geom.LocatedMorph;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphViewConfig;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphView;

public class SwingEngineeringMorphView extends SwingMorphView {
    @SuppressWarnings("unused")
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

    public SwingEngineeringMorphView(MorphViewConfig config) {
        super(config);
    }

//    @Override
//    public void seed() {
//        if (!seedMorphs.isEmpty()) {
//            synchronized (seedMorphs) {
//                SwingEngineeringMorphViewPanel panel = (SwingEngineeringMorphViewPanel)panels.firstElement();
//                BoxedMorphCollection boxedMorphs = panel
//                        .getBoxedMorphCollection();
//                if (!boxedMorphs.isEmpty()) {
//                    for (BoxedMorph boxedMorph : boxedMorphs.getBoxedMorphs()) {
//                        boxedMorph.getMorph()
//                                .removePropertyChangeListener(this);
//                    }
//                    boxedMorphs.removeAllElements();
//                }
//                BoxManager boxes = boxedMorphs.getBoxManager();
//                int index = 0;
//                MorphConfig config = appData.getMorphConfig();
//                Morph morph = config.copyMorph(seedMorphs.firstElement());
//                BoxedMorph boxedMorph = new BoxedMorph(boxes, morph,
//                        boxes.getBox(index++));
//                morph.addPropertyChangeListener(panel);
//                boxedMorphs.add(boxedMorph);
//                seedMorphs.remove(morph);
//
//                // GEneBoxStrip shouldbe told? How does Breeding do it?
//            }
//        }
//        backup(true);
//
//    }

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
        super.propertyChange(evt);
        if(evt.getPropertyName() == "genome") {
            repaint();
        }
    }


    @Override
    public void addSeedMorphs(Vector<Morph> seedMorphsToAdd) {
        super.addSeedMorphs(seedMorphsToAdd);
        if (this.seedMorphs.isEmpty()) {
            MorphConfig morphConfig = appData.getMorphConfig();
            this.seedMorphs.add(morphConfig.newMorph(0));
        }

    }

    @Override
    public void buildMenu(WatchmakerMenuBar menuBar) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void cleanMenu(WatchmakerMenuBar menuBar) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateMenu(WatchmakerMenuBar menuBar) {
        // TODO Auto-generated method stub
        
    }

}
