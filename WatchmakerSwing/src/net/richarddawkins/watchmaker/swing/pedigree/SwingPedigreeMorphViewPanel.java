package net.richarddawkins.watchmaker.swing.pedigree;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.cursor.WatchmakerCursor;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.pedigree.PedigreeMorphViewPanel;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewPanel;

public class SwingPedigreeMorphViewPanel extends SwingMorphViewPanel implements PedigreeMorphViewPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.pedigree.SwingPedigreeMorphViewPanel");

    public SwingPedigreeMorphViewPanel(MorphView morphView, BoxedMorphCollection page) {
        super(morphView, page);
        setCursor(cursors.getCursor(WatchmakerCursor.pedigree));
        includeChildrenInAutoScale = false;
        showBoundingBoxes = true;
    }
    @Override
    public synchronized void paintMorphViewPanel(Object graphicsContext,
            Dim size) {

        Graphics2D g2 = (Graphics2D) graphicsContext;
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));
        Cursor cursor = this.getCursor();
        if (cursors.isCursorType(WatchmakerCursor.move, cursor)
                && this.lastMouseDown != null && this.lastMouseDrag != null) {
            Point midPoint = selectedBox.getMidPoint();
            Vector<Point> endPoints = getEndPoints(midPoint, lastMouseDrag);
            for (Point endPoint : endPoints) {
                g2.drawLine(midPoint.h, midPoint.v, endPoint.h, endPoint.v);
            }
        }
        for (BoxedMorph putativeParent : boxedMorphCollection
                .getBoxedMorphs()) {
            Morph putativeParentMorph = putativeParent.getMorph();
            for (BoxedMorph putativeChild : boxedMorphCollection
                    .getBoxedMorphs()) {
                Morph putativeChildMorph = putativeChild.getMorph();
                if (putativeChildMorph
                        .getPedigree().parent == putativeParentMorph) {
                    BoxManager boxes = boxedMorphCollection.getBoxManager();

                    Point parentMidPoint = boxes.getMidPoint(size,
                            putativeParent.getBox());
                    Point childMidPoint = boxes.getMidPoint(size,
                            putativeChild.getBox());
                    g2.drawLine(parentMidPoint.h, parentMidPoint.v,
                            childMidPoint.h, childMidPoint.v);
                }
            }
        }
        super.paintMorphViewPanel(graphicsContext, size);

    }
    @Override
    public void processMousePressed(Point point, Dim size) {
        logger.info("Pedigree box pressed at " + point);
        BoxManager boxes = this.boxedMorphCollection.getBoxManager();
        selectedBox = boxes.getBoxNoContainingPoint(point, size);
        
        if (selectedBox != null) {
            
            BoxedMorph boxedMorph = boxedMorphCollection.getBoxedMorph(selectedBox);
            Morph morph = boxedMorph.getMorph();
            if(boxedMorph != boxedMorphCollection.getBoxedMorphs().lastElement()) {
                boxedMorphCollection.moveToEnd(boxedMorph);
            }
            Cursor cursor = this.getCursor();
            this.lastMouseDown = point;
            this.lastMouseDownSize = size;
            if(cursors.isCursorType(WatchmakerCursor.pedigree, cursor)) {
                BufferedImage rawImage = (BufferedImage) morph.getImage();
                BufferedImage scaledImage = (BufferedImage) rawImage.getScaledInstance(16,
                        16, Image.SCALE_DEFAULT);
                
                this.setCursor(cursors.newCustomCursor(scaledImage));
            } else if(cursors.isCursorType(WatchmakerCursor.detach, cursor)) {
                morph.getPedigree().parent = null;
            } else if(cursors.isCursorType(WatchmakerCursor.kill, cursor)) {
               
                Vector<BoxedMorph> boxedMorphsToKill = boxedMorphCollection
                        .findBoxedMorphsForMorphAndDescendents(boxedMorph);
                for (BoxedMorph victim : boxedMorphsToKill) {
                    this.boxedMorphCollection.remove(victim);
                }
            }
        }

        repaint();
    }

    @Override
    public void processMouseDragged(Point point, Dim size) {
       Cursor cursor = this.getCursor();
        
        if(cursors.isCursorType(WatchmakerCursor.move, cursor)) {
            if (selectedBox != null) {
                BoxManager boxes = boxedMorphCollection.getBoxManager();
                Dim dim = boxes.getBoxSize(selectedBox, size);
                
                Point delta = point.subtract(lastMouseDown);
                
                selectedBox.left += delta.h;
                selectedBox.right += delta.h;
                selectedBox.top += delta.v;
                selectedBox.bottom += delta.v;
                
                boxedMorphCollection.getBoxManager().setBox(selectedBox, selectedBox,
                        size);
                // Remember where the mouse is now, since
                // we are still dragging.
                lastMouseDown = point;
                lastMouseDownSize = size;
                repaint();
            }
        } else {
            super.processMouseDragged(point, size);
        }

    }

    @Override
    protected void processMouseReleased(Point point, Dim size) {
        logger.info("Pedigree box released at " + point);
        Cursor cursor = this.getCursor();
        if(cursors.isCursorType(WatchmakerCursor.move, cursor)) {
        } else if(cursors.isCursorType(WatchmakerCursor.detach, cursor)) {
        } else if(cursors.isCursorType(WatchmakerCursor.kill, cursor)) {
        } else {
            spawn(point, size);
        }
        super.processMouseReleased(point, size);
    }
    @Override
    public Morph getMorphOfTheHour() {
        return boxedMorphCollection.getBoxedMorphs()
                .elementAt(boxedMorphCollection.getBoxedMorphs().size() - 1)
                .getMorph();
    }
    protected Rect selectedBox = null;

    protected Vector<Point> getEndPoints(Point origin, Point delta) {
        int dx = delta.h - origin.h;
        int dy = delta.v - origin.v;
        Vector<Point> endPoints = new Vector<Point>();

        // Egregious use of fall-through ahead. - ABC
        switch (((SwingPedigreeMorphView)morphView).mirrorType) {
        case DOUBLE:
            endPoints.add(new Point(origin.h + dy, origin.v - dx));
            endPoints.add(new Point(origin.h - dy, origin.v + dx));
            // Fall through by design - ABC
        case SINGLE:
            endPoints.add(new Point(origin.h - dx, origin.v - dy));
            // Fall through by design - ABC
        case NONE:
            endPoints.add(new Point(origin.h + dx, origin.v + dy));
        }
        return endPoints;
    }

    protected void spawn(Point point, Dim size) {
        BoxManager boxes = this.boxedMorphCollection.getBoxManager();
        Rect releasedInRect = boxes.getBoxNoContainingPoint(point, size);
        // Cancel spawn if released in same rectangle.
        if (selectedBox != releasedInRect) {
            Morph parentMorph = boxedMorphCollection.getBoxedMorph(selectedBox)
                    .getMorph();
            MorphConfig config = this.morphView.getAppData().getMorphConfig();

            Vector<Point> spawnPoints = this
                    .getEndPoints(selectedBox.getMidPoint(), point);
            for (Point spawnPoint : spawnPoints) {

                Morph morph = config.reproduce(parentMorph);

                Rect margin = morph.getPhenotype().getMargin();
                int boxPadding = 0;
                int width = margin.getWidth() + boxPadding;
                int height = margin.getHeight() + boxPadding;
                spawnPoint = spawnPoint
                        .subtract(new Point(width / 2, height / 2));
                Rect newRect = new Rect(spawnPoint.h, spawnPoint.v,
                        spawnPoint.h + width, spawnPoint.v + height);
                boxes.addBox(newRect, size);

                BoxedMorph boxedMorph = new BoxedMorph(boxes, morph, newRect);
                boxedMorphCollection.add(boxedMorph);

            }
        }
        this.setCursor(cursors.getCursor(WatchmakerCursor.pedigree));

    }

}
