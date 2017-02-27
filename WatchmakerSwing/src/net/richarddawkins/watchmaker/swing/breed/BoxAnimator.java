package net.richarddawkins.watchmaker.swing.breed;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;

public class BoxAnimator extends TimerTask {
    enum Phase {
        deactivate_grid, animate_mother, reactivate_grid, box_next_offspring, draw_out_offspring, breed_complete;
    }

    private static Logger logger = Logger
            .getLogger("net.richarddawkins.watchmaker.swing.breed.BoxAnimator");
    protected int vacantBoxNumber = 0;
    protected SwingBreedingMorphViewPanel breedingPanel;
    protected Morph newestOffspring;
    protected BoxedMorph boxedNewestOffspring;
    protected Phase phase;
    protected BoxedMorphCollection boxedMorphVector;
    protected Rect midBox;
    protected BoxedMorph boxedMorphParent;
    protected BoxManager boxes;

    public BoxAnimator(SwingBreedingMorphViewPanel breedingPanel, Rect special,
            BoxedMorph boxedMorphParent, Morph newestOffspring) {
        this.breedingPanel = breedingPanel;
        this.boxedMorphVector = breedingPanel.getBoxedMorphCollection();
        this.boxes = boxedMorphVector.getBoxes();
        this.midBox = boxes.getMidBox();
        this.boxedMorphParent = boxedMorphParent;
        this.newestOffspring = newestOffspring;
        if (boxedMorphParent == null) {
            cancel();
        } else {
            prepareBoxAnimator(special);
        }
        Timer timer = new Timer();
        timer.schedule(this, 0,
                breedingPanel.morphView.getAppData().getTickDelay());

    }

    private void prepareBoxAnimator(Rect special) {

        // Clear out the breeding panel's collection of morphs
        boxedMorphVector.removeAllElements();
        // Put the parent morph back in collection, still in the same box.
        boxedMorphVector.add(boxedMorphParent);
        // Get the eldest child of a new litter of offspring of the parent
        // morph

        breedingPanel.autoScaleBasedOnMorphs(special, breedingPanel.getIncludeChildrenInAutoScale());

        // We don't box it yet, because we still have to animate the parent.
        this.boxedNewestOffspring = null;

        if (special != midBox) {
            // Need to animate mother
            boxedMorphParent.setDestinationBox(midBox);
            boxedMorphParent.setProgress(0.0d);
            boxedMorphParent.setScaleWithProgress(false);
            phase = Phase.deactivate_grid;
        } else {
            phase = Phase.reactivate_grid;
        }

    }

    @Override
    public void run() {
        // synchronized(breedingPanel.getBoxedMorphCollection()) {
        doRun();
        // }
    }

    protected void doRun() {
        logger.fine("BoxAnimator.doRun() " + phase);
        switch (phase) {
        case deactivate_grid:
            breedingPanel.morphView.setShowBoxes(false);
            phase = Phase.animate_mother;
            break;
        case animate_mother:
            logger.fine("Animate Mother");
            boxedMorphParent.nudge();
            if (boxedMorphParent.getProgress() == 1.0d) {
                phase = Phase.reactivate_grid;
            }
            break;
        case reactivate_grid:
            logger.fine("Reactivate Grid");
            breedingPanel.morphView.setShowBoxes(true);
            boxedMorphParent.setBox(midBox);
            breedingPanel.setSpecial(midBox);
            boxedMorphParent.setDestinationBox(null);
            boxedMorphParent.setProgress(0.0d);
            phase = Phase.box_next_offspring;
            break;
        case box_next_offspring:
            if (newestOffspring != null) {
                boxedNewestOffspring = new BoxedMorph(boxes, newestOffspring,
                        midBox);

                logger.fine("Boxing to vacantBoxNumber:" + vacantBoxNumber);
                boxedNewestOffspring
                        .setDestinationBox(boxes.getBox(vacantBoxNumber));
                vacantBoxNumber++;
                boxedNewestOffspring.setScaleWithProgress(true);
                // If the pointer to the next 'vacant' box points to the midBox,
                if (vacantBoxNumber < boxes.getBoxCount()
                        && boxes.getBox(vacantBoxNumber) == midBox) {
                    // boxedMorphVector.add(boxedMorphParent);
                    // skip it (the parent already occupies it.)
                    vacantBoxNumber++;
                    logger.fine("Skipped midbox index, new one is:"
                            + vacantBoxNumber);
                }
                logger.fine("BoxAnimator.run() box_next_offspring:"
                        + boxedNewestOffspring.getBox() + " to "
                        + boxedNewestOffspring.getDestinationBox());
                boxedNewestOffspring.setScaleWithProgress(true);
                // Add the newest boxed morph to the view's collection of boxed
                // morphs
                synchronized (boxedMorphVector) {
                    logger.fine("Adding boxedMorph at index:"
                            + (vacantBoxNumber - 1));
                    boxedMorphVector.add(vacantBoxNumber - 1,
                            boxedNewestOffspring);
                }
//                breedingPanel.autoScaleBasedOnMorphs();
                phase = Phase.draw_out_offspring;
            } else {
                phase = Phase.breed_complete;
            }
            break;
        case draw_out_offspring:
            boxedNewestOffspring.getMorph().setImage(null);
            boxedNewestOffspring.nudge();
            if (boxedNewestOffspring.getProgress() == 1.0d) {
                // newestOffspring has made it to its new home box.
                boxedNewestOffspring
                        .setBox(boxedNewestOffspring.getDestinationBox());
                boxedNewestOffspring.setDestinationBox(null);
                boxedNewestOffspring.setProgress(0.0d);
                boxedNewestOffspring.setScaleWithProgress(false);
                // Finished with the reference to the newest boxed offspring
                // (It's still safely in the collection)
                boxedNewestOffspring = null;
                newestOffspring = newestOffspring.getPedigree().youngerSib;
                phase = Phase.box_next_offspring;
            }
            break;

        case breed_complete:
            this.cancel();
            breedingPanel.setCursor(WatchmakerCursors.breed);
            breedingPanel.updateCursor();
            logger.info("Breed Complete");
            break;
        }
        breedingPanel.repaint();
    }

}