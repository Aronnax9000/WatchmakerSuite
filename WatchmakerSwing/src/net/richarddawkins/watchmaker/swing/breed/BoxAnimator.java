package net.richarddawkins.watchmaker.swing.breed;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;

public class BoxAnimator  {
    enum Phase {
        deactivate_grid, animate_mother, reactivate_grid, box_next_offspring, draw_out_offspring, breed_complete, idle;
    }

    private static Logger logger = Logger
            .getLogger("net.richarddawkins.watchmaker.swing.breed.BoxAnimator");
    protected int vacantBoxNumber;
    protected SwingBreedingMorphViewPanel breedingPanel;
    protected Morph newestOffspring;
    protected BoxedMorph boxedNewestOffspring;
    protected Phase phase = Phase.idle;
    protected BoxedMorphCollection boxedMorphVector;
    protected Rect midBox;
    protected BoxedMorph boxedMorphParent;
    protected BoxManager boxes;

    public BoxAnimator(SwingBreedingMorphViewPanel breedingPanel) {
        this.breedingPanel = breedingPanel;
        logger.info("BoxAnimator constructed");

    }

    public void setupBoxAnimator(Rect special, BoxedMorph boxedMorphParent,
            Morph newestOffspring) {
        this.boxedMorphVector = breedingPanel.getBoxedMorphCollection();
        this.boxes = boxedMorphVector.getBoxes();
        this.midBox = boxes.getMidBox();

        this.boxedMorphParent = boxedMorphParent;
        this.newestOffspring = newestOffspring;
        if (boxedMorphParent != null) {
            prepareBoxAnimator(special);
        } else {
            logger.warning("BoxAnimator setupBoxAnimator called with no boxedMorphParent.");
        }
        

    }

    private void prepareBoxAnimator(Rect special) {
        if (phase == Phase.idle) {
            // Clear out the breeding panel's collection of morphs
            boxedMorphVector.removeAllElements();
            // Put the parent morph back in collection, still in the same box.
            boxedMorphVector.add(boxedMorphParent);
            // Get the eldest child of a new litter of offspring of the parent
            // morph

            breedingPanel.autoScaleBasedOnMorphs(special,
                    breedingPanel.getIncludeChildrenInAutoScale());

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
                // if
                // (breedingPanel.getMorphView().getAppData().isBreedRightAway())
                // {
                // phase = Phase.reactivate_grid;
                // } else {
                // phase = Phase.breed_complete;
                // }
            }
        } else {
            logger.warning(
                    "prepareBoxAnimator skipped, because not breed_complete. Phase was "
                            + phase);
        }

    }



    protected void doRun() {
        synchronized (boxedMorphVector) {
            doSynchronizedRun();
        }
    }

    protected void doSynchronizedRun() {
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
            vacantBoxNumber = 0;
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
                // breedingPanel.autoScaleBasedOnMorphs();
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
            breedingPanel.setCursor(WatchmakerCursors.breed);
            breedingPanel.updateCursor();
            logger.fine("Breed Complete");
            phase = Phase.idle;
            break;
        case idle:
            timerTask.cancel();
            timer = null;
            logger.info("Finished idle");
            break;
        }
        breedingPanel.repaint();
    }

    Timer timer = null;
    TimerTask timerTask = null;
    /**
     * Start the animation. ("Laugh a-while you can, monkey boy.") - LJW
     */
    public void feetDoYourStuff() {
        logger.info("FeetDoYourStuff");
        if (timer == null) {
            timer = new Timer();
            timerTask = new TimerTask() {

                @Override
                public void run() {
                    // synchronized(breedingPanel.getBoxedMorphCollection()) {
                    doRun();
                    // }
                }
            };
            timer.schedule(timerTask, 0, breedingPanel.morphView.getAppData().getTickDelay());
        } else {
            logger.warning(
                    "BoxAnimator.feetDoYourStuff not running because animator is not idle. Phase was "
                            + phase);
        }
    }

}