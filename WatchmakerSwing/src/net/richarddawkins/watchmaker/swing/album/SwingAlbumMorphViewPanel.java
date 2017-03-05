package net.richarddawkins.watchmaker.swing.album;

import java.util.logging.Logger;

import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.GridBoxManager;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewPanel;

public class SwingAlbumMorphViewPanel extends SwingMorphViewPanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.album.SwingAlbumMorphViewPanel");
    
    SwingAlbumMorphViewPanel(MorphView morphView, BoxedMorphCollection page) {
        super(morphView, page);
        GridBoxManager gridBoxManager = new GridBoxManager(5,3);
//        setBoxManager(gridBoxManager);
        setCursor(WatchmakerCursors.highlight);
        // TODO Auto-generated constructor stub
    }



    @Override
    public void setBoxedMorphCollection(BoxedMorphCollection newBoxedMorphCollection) {
        super.setBoxedMorphCollection(newBoxedMorphCollection);
        newBoxedMorphCollection.getBoxes().setAccentuateMidBox(false);
    }
    
    @Override
    public void processMouseClicked(Point myPt, Dim size) {
        BoxManager boxes = boxedMorphCollection.getBoxes();
        if (this.getCursor() == WatchmakerCursors.highlight) {
            Rect box = boxes.getBoxNoContainingPoint(myPt, size);
            // SwingGeom.toWatchmakerDim(centrePanel.getSize())
            BoxedMorph boxedMorph = boxedMorphCollection.getBoxedMorph(box);
            
            this.boxedMorphCollection.setSelectedBoxedMorph(boxedMorph);
            if(boxedMorph != null) {
                pcs.firePropertyChange("genome", null,
                        boxedMorph.getMorph().getGenome());
            }
            repaint();
        }
    }
    protected Rect selectedBox = null;
    @Override
    public void processMouseMotion(Point myPt, Dim size) {
        // logger.info("SwingAlbumMorphView.processMouseMotion(" + myPt + ", " +
        // size + ")");

        BoxManager boxes = boxedMorphCollection.getBoxes();
        Rect box = boxes.getBoxNoContainingPoint(myPt, size);
        if (box != null && box != selectedBox) {
            synchronized (boxedMorphCollection) {
                BoxedMorph boxedMorph = boxedMorphCollection.getBoxedMorph(box);

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


}
