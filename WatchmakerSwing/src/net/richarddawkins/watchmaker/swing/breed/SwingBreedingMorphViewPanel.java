package net.richarddawkins.watchmaker.swing.breed;

import java.util.Collection;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.breed.BreedingMorphViewPanel;
import net.richarddawkins.watchmaker.swing.breed.BoxAnimator.Phase;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewPanel;

public class SwingBreedingMorphViewPanel extends SwingMorphViewPanel implements BreedingMorphViewPanel {
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.breed.SwingBreedingMorphViewPanel");
    private static final long serialVersionUID = 1L;


    
    public SwingBreedingMorphViewPanel(MorphView morphView,
            BoxedMorphCollection page) {
        super(morphView, page);
        includeChildrenInAutoScale = true;

    }

    @Override
    public void processMouseMotion(Point myPt, Dim size) {
        logger.fine("processMouseMotion(" + myPt + ", " + size);
        super.processMouseMotion(myPt, size);
        if (!(this.getCursor() == WatchmakerCursors.highlight
                && boxedMorphCollection.getSelectedBoxedMorph() != null)) {
            BoxManager boxes = boxedMorphCollection.getBoxManager();
            Rect box = boxes.getBoxNoContainingPoint(myPt, size);
            if (box != null) {
                logger.fine("processMouseMotion found box " + box);
                // synchronized (boxedMorphVector) {
                BoxedMorph boxedMorph = boxedMorphCollection.getBoxedMorph(box);

                if (boxedMorph != null) {
                    logger.fine("processMouseMotion found BoxedMorph "
                            + boxedMorph);

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
                logger.warning("No box found under cursor");
            }
        }
    }

    @Override
    public void processMouseClicked(Point myPt, Dim size) {
        logger.info("SwingBreedingMorphView.boxClicked(" + myPt + ")");
        BoxManager boxes = boxedMorphCollection.getBoxManager();
        if (this.getCursor() == WatchmakerCursors.breed) {
            Rect box = boxes.getBoxNoContainingPoint(myPt,
                    geometryManager.toWatchmakerDim(this.getSize()));
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
                    geometryManager.toWatchmakerDim(this.getSize()));
            BoxedMorph boxedMorph = boxedMorphCollection.getBoxedMorph(box);
            this.boxedMorphCollection.setSelectedBoxedMorph(boxedMorph);
            pcs.firePropertyChange("genome", null,
                    boxedMorph.getMorph().getGenome());
            repaint();

        }
    }

    
    
    @Override
    public synchronized void paintMorphViewPanel(Object graphicsContext,
            Dim size) {
        this.checkBreedFromMidBoxOnNextRepaint();
        super.paintMorphViewPanel(graphicsContext, size);
    }



    protected final BoxAnimator animator = new BoxAnimator(this);

    @Override
    public synchronized void breedFromSpecial() {
        if (animator.phase == Phase.idle) {
            logger.info("Breeding from box " + special);
            this.setCursor(WatchmakerCursors.watchCursor);
//            morphView.backup(false);
            // Get the morph associated with the box
            BoxedMorph boxedMorphParent = boxedMorphCollection
                    .getBoxedMorph(special);
            if (boxedMorphParent != null) {
                Morph parent = boxedMorphParent.getMorph();
                MorphConfig config = morphView.getAppData().getMorphConfig();
                int litterSize = boxedMorphCollection.getBoxManager().getBoxCount()
                        - 1;
                Morph newestOffspring = config.getLitter(parent, litterSize);
                try {
                    animator.setupBoxAnimator(special, boxedMorphParent,
                            newestOffspring);
                    animator.startAnimation();
                } catch (IllegalStateException e) {
                    logger.warning("SwingBreedingMorphView.breedFromSpecial() "
                            + e.getMessage());
                    this.setCursor(null);
                    this.updateCursor();
                }
            } else {
                logger.warning(
                        "SwingBreedingMorphViewPanel.breedFromSpecial: no boxed morph parent at special "
                                + special);
                this.setCursor(null);
                this.updateCursor();
            }
        } else {
            logger.warning(
                    "Skipping breed from special, animator is not idle.");
        }
    }

    @Override
    public Morph getMorphOfTheHour() {
        BoxedMorph boxedMorph = boxedMorphCollection.getSelectedBoxedMorph();
        if (boxedMorph != null) {
            return boxedMorph.getMorph();
        } else {
            BoxManager boxes = boxedMorphCollection.getBoxManager();

            return boxedMorphCollection.getBoxedMorph(boxes.getMidBox())
                    .getMorph();
        }
    }
    @Override
    public synchronized void breedFromSelector() {
        BoxManager boxes = boxedMorphCollection.getBoxManager();
        Rect midBox = boxes.getMidBox();
        BoxedMorph centreBoxedMorph = boxedMorphCollection
                .getBoxedMorph(midBox);
        Morph centreMorph = centreBoxedMorph.getMorph();
        if (centreMorph != null) {
            Collection<Morph> morphs = boxedMorphCollection.getMorphs();
            morphs.remove(centreMorph);
            if (!morphs.isEmpty()) {
                Morph target = centreMorph;
                Morph best = morphView.getAppData().getMorphConfig()
                        .getSelector().best(target, morphs);
                BoxedMorph bestBoxedMorph = null;
                for (BoxedMorph boxedMorph : boxedMorphCollection
                        .getBoxedMorphs()) {
                    if (boxedMorph.getMorph() == best) {
                        bestBoxedMorph = boxedMorph;
                        logger.fine(
                                "Found best boxed morph: " + bestBoxedMorph);
                    }

                }
                if (bestBoxedMorph != null) {
                    special = bestBoxedMorph.getBox();
                }
            }
            breedFromSpecial();

        } else {
            logger.warning("No centre boxedMorph for rect ");
        }
    }

    protected void checkBreedFromMidBoxOnNextRepaint() {
        synchronized(this) {
            if(breedFromMidBoxOnNextRepaint) {
                breedFromMidBoxOnNextRepaint = false;
                Rect midBox = boxedMorphCollection.getBoxManager()
                        .getMidBox();
                logger.fine("Setting panel special to " + midBox);

                setSpecial(midBox);
                breedFromSpecial();
            }
        }
    }
    
    protected boolean breedFromMidBoxOnNextRepaint = false;
    @Override
    public void setBreedFromMidBoxOnNextRepaint(boolean b) {
        breedFromMidBoxOnNextRepaint = b;
        
    }
}
