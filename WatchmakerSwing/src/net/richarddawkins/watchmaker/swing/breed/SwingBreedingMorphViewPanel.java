package net.richarddawkins.watchmaker.swing.breed;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.phenotype.DrawingPreferences;
import net.richarddawkins.watchmaker.swing.SwingGeom;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewPanel;
import net.richarddawkins.watchmaker.util.Globals;

public class SwingBreedingMorphViewPanel extends SwingMorphViewPanel {
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.breed.SwingBreedingMorphViewPanel");
    protected Rect selectedBox = null;
    public Rect special = null;
    private static final long serialVersionUID = 1L;

    public SwingBreedingMorphViewPanel(MorphView morphView,
            BoxedMorphCollection page) {
        super(morphView, page);
        this.addComponentListener(new ResizeListener());
        // if(morphView.getAppData().isBreedRightAway()) {
        // setCursor(WatchmakerCursors.breed);
        // } else {
        // setCursor(WatchmakerCursors.random);
        // }
    }

    @Override
    public void processMouseMotion(Point myPt, Dim size) {
        logger.fine("processMouseMotion(" + myPt + ", " + size);
        if (!(this.getCursor() == WatchmakerCursors.highlight
                && boxedMorphCollection.getSelectedBoxedMorph() != null)) {
            BoxManager boxes = boxedMorphCollection.getBoxes();
            Rect box = boxes.getBoxNoContainingPoint(myPt, size);
            if (box != null) {
                logger.fine("processMouseMotion found box " + box);
                // synchronized (boxedMorphVector) {
                BoxedMorph boxedMorph = boxedMorphCollection.getBoxedMorph(box);

                if (boxedMorph != null) {
                    logger.fine("processMouseMotion found BoxedMorph "
                            + boxedMorph);
                    if (box != selectedBox)
                        firePropertyChange("genome", null,
                                boxedMorph.getMorph().getGenome());
                    selectedBox = box;

                    if (this.getCursor() != WatchmakerCursors.watchCursor
                            && this.getCursor() != WatchmakerCursors.highlight) {
                        this.setCursor(WatchmakerCursors.breed);
                    }
                } else {
                    if (this.getCursor() != WatchmakerCursors.watchCursor
                            && this.getCursor() != WatchmakerCursors.highlight) {
                        this.setCursor(WatchmakerCursors.random);
                    }
                }
                // }
                logger.fine(
                        "processMouseMotion leaving formerly synchronized section");

            } else {
                // logger.warning("No box selected");
            }
        }
    }

    @Override
    public void processMouseClicked(Point myPt, Dim size) {
        logger.info("SwingBreedingMorphView.boxClicked(" + myPt + ")");
        BoxManager boxes = boxedMorphCollection.getBoxes();
        if (this.getCursor() == WatchmakerCursors.breed) {
            Rect box = boxes.getBoxNoContainingPoint(myPt,
                    SwingGeom.toWatchmakerDim(this.getSize()));
            if (box != null) {
                special = box;
                breedFromSpecial();
            }
        } else if (this.getCursor() == WatchmakerCursors.random) {
            this.setCursor(WatchmakerCursors.watchCursor);
            morphView.addSeedMorph(
                    morphView.getAppData().getMorphConfig().newMorph(0));
            this.setCursor(null);
            updateCursor();
        } else if (this.getCursor() == WatchmakerCursors.highlight) {
            Rect box = boxes.getBoxNoContainingPoint(myPt,
                    SwingGeom.toWatchmakerDim(this.getSize()));
            BoxedMorph boxedMorph = boxedMorphCollection.getBoxedMorph(box);
            this.boxedMorphCollection.setSelectedBoxedMorph(boxedMorph);
            pcs.firePropertyChange("genome", null,
                    boxedMorph.getMorph().getGenome());
            repaint();

        }
    }

    public void breedFromSpecial() {
        logger.info("Breeding from box " + special);
        this.setCursor(WatchmakerCursors.watchCursor);
        morphView.backup(false);
     // Get the morph associated with the box
        BoxedMorph boxedMorphParent= boxedMorphCollection.getBoxedMorph(special);
        if(boxedMorphParent != null) {
            Morph parent = boxedMorphParent.getMorph();
            Morph newestOffspring = morphView.getAppData().getMorphConfig().getLitter(parent,
                    boxedMorphCollection.getBoxes().getBoxCount() - 1);
            try {
                BoxAnimator animator = new BoxAnimator(this, special, boxedMorphParent,
                        newestOffspring);
            } catch (IllegalStateException e) {
                logger.warning("SwingBreedingMorphView.breedFromSpecial() "
                        + e.getMessage());
            }
        } else {
            logger.warning("SwingBreedingMorphViewPanel.breedFromSpecial: no boxed morph parent at special " + special);
        }
    }

    @Override
    public Morph getMorphOfTheHour() {
        BoxedMorph boxedMorph = boxedMorphCollection.getSelectedBoxedMorph();
        if (boxedMorph != null) {
            return boxedMorph.getMorph();
        } else {
            BoxManager boxes = boxedMorphCollection.getBoxes();

            return boxedMorphCollection.getBoxedMorph(boxes.getMidBox())
                    .getMorph();
        }
    }

    public void setSpecial(Rect midBox) {
        special = midBox;

    }

    public Rect getSpecial() {
        return special;
    }

    class ResizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            autoScaleBasedOnMorphs();
        }
    }

    public void autoScaleBasedOnMorphs() {
        Vector<Dim> dims = new Vector<Dim>();

        BoxManager boxes = boxedMorphCollection.getBoxes();
        BoxedMorph boxedMorphSpecial = boxedMorphCollection
                .getBoxedMorph(special);
        if (boxedMorphSpecial != null) {
            Morph specialMorph = boxedMorphSpecial.getMorph();
            Vector<Morph> morphs = specialMorph.getMorphAndChildren();
            for (Morph morph : morphs) {
                Dim dim = morph.getPhenotype().getMargin().getDim();
                logger.fine("Adding dim " + dim);
                dims.add(dim);
            }

            Dim largestMorphDim = Dim.getLargest(dims);

            Dim boxDim = boxes.firstElement().getDim();
            int newScale = boxDim.getScale(largestMorphDim, Globals.zoomBase);
            DrawingPreferences drawingPreferences = morphView.getAppData()
                    .getPhenotypeDrawer().getDrawingPreferences();
            if (newScale != boxes.getScale()) {
                boxes.setScale(newScale);
            }
        } else {
            logger.warning("autoScaleBasedOnMorphs: no special boxed morph");
        }
    }

}
