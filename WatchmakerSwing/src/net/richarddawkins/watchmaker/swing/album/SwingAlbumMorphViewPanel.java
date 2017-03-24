package net.richarddawkins.watchmaker.swing.album;

import java.util.logging.Logger;

import net.richarddawkins.watchmaker.cursor.WatchmakerCursor;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.album.AlbumMorphViewPanel;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewPanel;

public class SwingAlbumMorphViewPanel extends SwingMorphViewPanel implements AlbumMorphViewPanel {


    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.album.SwingAlbumMorphViewPanel");
    
    public void initCursor() {
        setCursor(cursors.getCursor(WatchmakerCursor.highlight));
    }
    
    SwingAlbumMorphViewPanel(MorphView morphView, BoxedMorphCollection page) {
        super(morphView, page);
    }

    @Override
    public void setBoxedMorphCollection(BoxedMorphCollection newBoxedMorphCollection) {
        super.setBoxedMorphCollection(newBoxedMorphCollection);
        newBoxedMorphCollection.getBoxManager().setAccentuateMidBox(false);
    }
    
    @Override
    public void processMouseClicked(Point myPt, Dim size) {
        BoxManager boxes = boxedMorphCollection.getBoxManager();
        if (cursors.isCursorType(WatchmakerCursor.highlight, this.getCursor())) {
            Rect box = boxes.getBoxContainingPoint(myPt, size);
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

        BoxManager boxes = boxedMorphCollection.getBoxManager();
        Rect box = boxes.getBoxContainingPoint(myPt, size);
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
