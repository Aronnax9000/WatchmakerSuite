package net.richarddawkins.watchmaker.swing.album;

import java.awt.Component;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.album.Album;
import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphView;

public class SwingAlbumMorphView extends SwingMorphView {
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.album.SwingAlbumMorphView");

    public SwingAlbumMorphView(AppData appData,
            Album album) {
        super(appData, "IconAlbum_ALAN_32x32",
                album.getName() + " Album", false,
                appData.isGeneBoxToSide(), album);
        this.setBoxedMorphVector(album.getPage(0));
        boxedMorphVector.getBoxes().setAccentuateMidBox(false);
//        this.remove(centrePanel);
//
//        JScrollPane scrollPane = new JScrollPane(centrePanel);
//        this.add(scrollPane, BorderLayout.CENTER);
        ((Component) centrePanel).setCursor(WatchmakerCursors.highlight);
        updateCursor();
    }

    @Override
    public void processMouseClicked(Point myPt, Dim size) {
        BoxManager boxes = boxedMorphVector.getBoxes();
        if (centrePanel.getCursor() == WatchmakerCursors.highlight) {
            Rect box = boxes.getBoxNoContainingPoint(myPt, size);
            // SwingGeom.toWatchmakerDim(centrePanel.getSize())
            BoxedMorph boxedMorph = boxedMorphVector.getBoxedMorph(box);
            this.boxedMorphVector.setSelectedBoxedMorph(boxedMorph);
            pcs.firePropertyChange("genome", null,
                    boxedMorph.getMorph().getGenome());
            repaint();
        }
    }

    protected Rect selectedBox = null;

    @Override
    public void processMouseMotion(Point myPt, Dim size) {
        // logger.info("SwingAlbumMorphView.processMouseMotion(" + myPt + ", " +
        // size + ")");

        BoxManager boxes = boxedMorphVector.getBoxes();
        Rect box = boxes.getBoxNoContainingPoint(myPt, size);
        if (box != null && box != selectedBox) {
            synchronized (boxedMorphVector) {
                BoxedMorph boxedMorph = boxedMorphVector.getBoxedMorph(box);

                if (boxedMorph != null) {
                    logger.fine("SwingAlbumMorphView.processMouseMotion(" + myPt
                            + ", " + size + ") firing genome change");
                    pcs.firePropertyChange("genome", null,
                            boxedMorph.getMorph().getGenome());
                    selectedBox = box;

                }
            }
        } else {
            logger.warning("boxNo is -1");
        }

    }

    @Override
    public Morph getMorphOfTheHour() {
        return boxedMorphVector.getSelectedBoxedMorph().getMorph();
    }

    /**
     * 
     */
    private static final long serialVersionUID = 8224824610112892419L;

    @Override
    public void seed(Morph morph) {
        // TODO Auto-generated method stub

    }

}
