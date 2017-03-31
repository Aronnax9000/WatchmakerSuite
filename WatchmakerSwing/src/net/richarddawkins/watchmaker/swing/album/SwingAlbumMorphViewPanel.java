package net.richarddawkins.watchmaker.swing.album;

import java.awt.Cursor;
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

public class SwingAlbumMorphViewPanel extends SwingMorphViewPanel
        implements AlbumMorphViewPanel {

    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.album.SwingAlbumMorphViewPanel");

    SwingAlbumMorphViewPanel(MorphView morphView, BoxedMorphCollection page) {
        super(morphView, page);
    }

    @Override
    public void setBoxedMorphCollection(
            BoxedMorphCollection newBoxedMorphCollection) {
        super.setBoxedMorphCollection(newBoxedMorphCollection);
        newBoxedMorphCollection.getBoxManager().setAccentuateMidBox(false);
    }

    @Override
    public void processMouseClicked(Point myPt, Dim size) {
        if (morphView.isIndexed()) {
            super.processMouseClicked(myPt, size);
            this.setCursor(
                    (Cursor) cursors.getCursor(WatchmakerCursor.highlight));
        } else if (cursors.isCursorType(WatchmakerCursor.highlight,
                this.getCursor())) {
            BoxManager boxes = boxedMorphCollection.getBoxManager();
            Rect box = boxes.getBoxContainingPoint(myPt, size);
            BoxedMorph boxedMorph = boxedMorphCollection.getBoxedMorph(box);
            this.boxedMorphCollection.setSelectedBoxedMorph(boxedMorph);
            if (boxedMorph != null) {
                pcs.firePropertyChange("genome", null,
                        boxedMorph.getMorph().getGenome());
            }
            repaint();
        }
    }

    protected Rect selectedBox = null;

}
