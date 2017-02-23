package net.richarddawkins.watchmaker.swing.breed;

import java.util.Iterator;
import java.util.Timer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.album.Album;
import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.GridBoxManager;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.swing.SwingGeom;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphView;

public class SwingBreedingMorphView extends SwingMorphView {

    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.breed.SwingBreedingMorphView");
    private static final long serialVersionUID = -5445629768562940527L;

    protected boolean busyBreeding = false;

    protected BoxedMorph boxedMorphSpecial;

    Vector<Morph> litter;

    public Rect special = null;

    public SwingBreedingMorphView(AppData appData, Morph morph) {
        super(appData, "IconFlipBirdToBreedingGrid_ICON_00261_32x32",
                "Breeding", false, appData.isGeneBoxToSide(), null);
        centrePanel.setCursor(WatchmakerCursors.breed);
        boxedMorphVector
                .setBoxes(new GridBoxManager(appData.getDefaultBreedingCols(),
                        appData.getDefaultBreedingRows()));
        if (morph != null) {
            MorphConfig config = appData.getMorphConfig();
            Morph copy = config.newMorph();
            Genome genome = config.newGenome();
            morph.getGenome().copy(genome);
            copy.setGenome(genome);
            seed(copy);
        } else {
            seed(null);
        }

    }

    @Override
    public void processMouseClicked(Point myPt, Dim size) {
        logger.info("SwingBreedingMorphView.boxClicked(" + myPt + ")");
        BoxManager boxes = boxedMorphVector.getBoxes();
        if (centrePanel.getCursor() == WatchmakerCursors.breed) {
            Rect box = boxes.getBoxNoContainingPoint(myPt,
                    SwingGeom.toWatchmakerDim(centrePanel.getSize()));
            if (box != null) {
                special = box;
                breedFromSpecial();
            }
        } else if (centrePanel.getCursor() == WatchmakerCursors.random) {
            centrePanel.setCursor(WatchmakerCursors.watchCursor);
            seed(appData.getMorphConfig().newMorph(0));
            centrePanel.setCursor(null);
            updateCursor();
        } else if (centrePanel.getCursor() == WatchmakerCursors.highlight) {
            Rect box = boxes.getBoxNoContainingPoint(myPt,
                    SwingGeom.toWatchmakerDim(centrePanel.getSize()));
            BoxedMorph boxedMorph = boxedMorphVector.getBoxedMorph(box);
            this.boxedMorphVector.setSelectedBoxedMorph(boxedMorph);
            pcs.firePropertyChange("genome", null,
                    boxedMorph.getMorph().getGenome());
            repaint();

        }
    }
    @Override
    public void undo() {
        int level = album.indexOfPage(boxedMorphVector);
        if(level > 0) {
            boxedMorphVector = album.getPage(level - 1);
            repaint();
        } 
    }
    @Override
    public void redo() {
        int level = album.indexOfPage(boxedMorphVector);
        if(level < album.size() - 1) {
            boxedMorphVector = album.getPage(level + 1);
            repaint();
        } 
    }
    
    public void breedFromSpecial() {
        logger.log(Level.INFO, "Breeding from box " + special);
        centrePanel.setCursor(WatchmakerCursors.watchCursor);
        GridBoxManager newBoxManager = new GridBoxManager(
                appData.getDefaultBreedingCols(),
                appData.getDefaultBreedingRows());
        
        // Delete undo history after this point, if any.
        album.removePagesAfter(boxedMorphVector);
        
        BoxedMorphCollection newBoxedMorphs = new BoxedMorphCollection(
                "backing", newBoxManager);
        Iterator<Rect> boxIterator = newBoxManager.getBoxes().iterator();
        for(BoxedMorph boxedMorph: boxedMorphVector.getBoxedMorphs()) {
            Rect box;
            if(boxedMorphVector.size() == 1) {
                box = newBoxManager.getMidBox();
            } else {
                box = boxIterator.next();
            }
            if(boxedMorph.getBox() == special) {
                special = box;
            }
            BoxedMorph newBoxedMorph = new BoxedMorph(newBoxManager, boxedMorph.getMorph(), box);
            
            newBoxedMorphs.add(newBoxedMorph);
        }
        album.addPage(newBoxedMorphs);
        if(album.size() > Album.MAX_PAGES) {
            album.removePage(0);
        }
        boxedMorphVector = newBoxedMorphs;
        
        try {

            Timer timer = new Timer();
            BoxAnimator animator = new BoxAnimator(this, special);
            timer.schedule(animator, 0, appData.getTickDelay());
        } catch (IllegalStateException e) {
            logger.warning("SwingBreedingMorphView.breedFromSpecial() "
                    + e.getMessage());
        }
    }

    @Override
    public Morph getMorphOfTheHour() {
        BoxedMorph boxedMorph = boxedMorphVector.getSelectedBoxedMorph();
        if (boxedMorph != null) {
            return boxedMorph.getMorph();
        } else {
            BoxManager boxes = boxedMorphVector.getBoxes();

            return boxedMorphVector.getBoxedMorph(boxes.getMidBox()).getMorph();
        }
    }

    protected Rect selectedBox = null;

    @Override
    public void processMouseMotion(Point myPt, Dim size) {
        logger.info("processMouseMotion(" + myPt + ", " + size);
        if (!(centrePanel.getCursor() == WatchmakerCursors.highlight
                && boxedMorphVector.getSelectedBoxedMorph() != null)) {
            BoxManager boxes = boxedMorphVector.getBoxes();
            Rect box = boxes.getBoxNoContainingPoint(myPt, size);
            if (box != null) {
                logger.info("processMouseMotion entering formerly synchronized section");
//                synchronized (boxedMorphVector) {
                    BoxedMorph boxedMorph = boxedMorphVector.getBoxedMorph(box);

                    if (boxedMorph != null) {
                        if (box != selectedBox)
                            pcs.firePropertyChange("genome", null,
                                    boxedMorph.getMorph().getGenome());
                        selectedBox = box;

                        if (centrePanel
                                .getCursor() != WatchmakerCursors.watchCursor
                                && centrePanel
                                        .getCursor() != WatchmakerCursors.highlight) {
                            centrePanel.setCursor(WatchmakerCursors.breed);
                        }
                    } else {
                        if (centrePanel
                                .getCursor() != WatchmakerCursors.watchCursor
                                && centrePanel
                                        .getCursor() != WatchmakerCursors.highlight) {
                            centrePanel.setCursor(WatchmakerCursors.random);
                        }
                    }
//                }
                logger.info("processMouseMotion leaving formerly synchronized section");

            } else {
                logger.warning("boxNo is -1");
            }
        }
    }

    @Override
    public void seed(Morph morph) {

        Morph parent;
        if (morph == null) {
            MorphConfig config = appData.getMorphConfig();
            parent = config.newMorph(config.getStartingMorphBasicType());
        } else {
            parent = morph;
        }
        BoxManager boxes = boxedMorphVector.getBoxes();

        Rect midBox = boxes.getMidBox();
        BoxedMorph boxedMorph = new BoxedMorph(boxes, parent, midBox);
        boxedMorphVector.removeAllElements();
        boxedMorphVector.add(boxedMorph);
        pcs.firePropertyChange("genome", null,
                boxedMorph.getMorph().getGenome());
        // Trigger first breeding
        special = midBox;
        if (appData.isBreedRightAway()) {
            breedFromSpecial();
        }
        parent.setImage(null);
        repaint();

    }

}
