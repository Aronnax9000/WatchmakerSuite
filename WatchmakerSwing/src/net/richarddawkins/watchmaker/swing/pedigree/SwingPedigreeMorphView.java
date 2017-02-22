package net.richarddawkins.watchmaker.swing.pedigree;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.FreeBoxManager;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.pedigree.MirrorType;
import net.richarddawkins.watchmaker.pedigree.PedigreeMorphView;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphView;

public class SwingPedigreeMorphView extends SwingMorphView
        implements PedigreeMorphView {
    protected static Point[] trianglePoints = new Point[] { new Point(234, 51),
            new Point(134, 250), new Point(333, 250) };
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.pedigree.SwingPedigreeMorphView");

    private static final long serialVersionUID = -5445629768562940527L;

    protected MirrorType mirrorType = MirrorType.NONE;

    public MirrorType getMirrorType() {
        return mirrorType;
    }

    public void setMirrorType(MirrorType mirrorType) {
        this.mirrorType = mirrorType;
    }

    /**
     * The Macintosh II, the first color model, had a screen resolution of
     * 512x384. Richard apparently had a screen height of only 342 to work with,
     * which is 42 pixels shorter. RIP DNA. - ABC
     * 
     * <pre>
     * 		a.h := round(234 * ScreenWidth / 512);
     * 		a.v := round(51 * ScreenHeight / 342);
     * 		b.h := round(134 * ScreenWidth / 512);
     * 		b.v := round(250 * ScreenHeight / 342);
     * 		c.h := round(333 * ScreenWidth / 512);
     * 		c.v := round(250 * ScreenHeight / 342);
     * </pre>
     * 
     * @param appData
     *            the appData for the morph type which owns this morph view.
     */
    public SwingPedigreeMorphView(AppData appData, Morph morph) {
        super(appData);
        setName("Pedigree");
        BoxManager boxes = new FreeBoxManager();
        boxedMorphVector.setBoxes(boxes);
        seed(morph);
        centrePanel.setCursor(WatchmakerCursors.pedigree);
    }

    protected Vector<Point> getEndPoints(Point origin, Point delta) {
        int dx = delta.h - origin.h;
        int dy = delta.v - origin.v;
        Vector<Point> endPoints = new Vector<Point>();
        
        // Egregious use of fall-through ahead. - ABC
        switch (mirrorType) {
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

    @Override
    public synchronized void paintMorphViewPanel(Object graphicsContext,
            Dim size) {
        Graphics2D g2 = (Graphics2D) graphicsContext;
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));
        if (centrePanel.getCursor() != WatchmakerCursors.move
                && this.lastMouseDown != null && this.lastMouseDrag != null) {
            Point midPoint = selectedBox.getMidPoint();
            Vector<Point> endPoints = getEndPoints(midPoint,
                    lastMouseDrag);
            for (Point endPoint : endPoints) {
                g2.drawLine(midPoint.h, midPoint.v, endPoint.h,
                        endPoint.v);
            }
        }
        for (BoxedMorph putativeParent : boxedMorphVector.getBoxedMorphs()) {
            Morph putativeParentMorph = putativeParent.getMorph();
            for (BoxedMorph putativeChild : boxedMorphVector.getBoxedMorphs()) {
                Morph putativeChildMorph = putativeChild.getMorph();
                if (putativeChildMorph
                        .getPedigree().parent == putativeParentMorph) {
                    BoxManager boxes = boxedMorphVector.getBoxes();

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
    public Morph getMorphOfTheHour() {
        return boxedMorphVector.getBoxedMorphs()
                .elementAt(boxedMorphVector.getBoxedMorphs().size() - 1)
                .getMorph();
    }

    @Override
    public void seed(Morph morph) {

        Dim dim = new Dim(512, 342);
        BoxManager boxes = this.boxedMorphVector.getBoxes();
        BufferedImage image = (BufferedImage) morph.getImage();
        Point upperLeft = new Point(255, 170);
        Rect margin = morph.getPhenotype().getMargin();

        Rect correctedMargin = new Rect(0, 0, margin.getWidth(),
                margin.getHeight());

        Rect newRect = new Rect(upperLeft.h, upperLeft.v + 2,
                upperLeft.h + (margin.getWidth() - 3) / 2 + 1,
                upperLeft.v + (margin.getHeight() + 14) / 2);

        boxes.addBox(newRect, dim);
        BoxedMorph boxedMorph = new BoxedMorph(boxes, morph, newRect);
        boxedMorphVector.add(boxedMorph);
    }

    protected Rect selectedBox = null;

    @Override
    protected void processMousePressed(Point point, Dim size) {
        logger.info("Pedigree box pressed at " + point);
        BoxManager boxes = this.boxedMorphVector.getBoxes();
        selectedBox = boxes.getBoxNoContainingPoint(point, size);
        
        if (selectedBox != null) {
            BoxedMorph boxedMorph = boxedMorphVector.getBoxedMorph(selectedBox);
            Morph morph = boxedMorph.getMorph();
            if(boxedMorph != boxedMorphVector.getBoxedMorphs().lastElement()) {
                boxedMorphVector.moveToEnd(boxedMorph);
            }
            
            this.lastMouseDown = point;
            this.lastMouseDownSize = size;
            if (centrePanel.getCursor() == WatchmakerCursors.pedigree) {
                centrePanel.setCursor(WatchmakerCursors.createCustomCursor(
                        ((BufferedImage) morph.getImage()).getScaledInstance(16,
                                16, Image.SCALE_DEFAULT)));
            } else if (centrePanel.getCursor() == WatchmakerCursors.detach) {
                morph.getPedigree().parent = null;
            } else if (centrePanel.getCursor() == WatchmakerCursors.kill) {
               
                Vector<BoxedMorph> boxedMorphsToKill = boxedMorphVector
                        .findBoxedMorphsForMorphAndDescendents(boxedMorph);
                for (BoxedMorph victim : boxedMorphsToKill) {
                    this.boxedMorphVector.remove(victim);
                }
            }
        }

        repaint();
    }

    @Override
    protected void processMouseDragged(Point point, Dim size) {
        if (centrePanel.getCursor() == WatchmakerCursors.move) {
            if (selectedBox != null) {
                BoxManager boxes = boxedMorphVector.getBoxes();
                Dim dim = boxes.getBoxSize(selectedBox, size);
                
                Point delta = point.subtract(lastMouseDown);
                
                selectedBox.left += delta.h;
                selectedBox.right += delta.h;
                selectedBox.top += delta.v;
                selectedBox.bottom += delta.v;
                
                boxedMorphVector.getBoxes().setBox(selectedBox, selectedBox,
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
        if (centrePanel.getCursor() == WatchmakerCursors.move) {

        } else if (centrePanel.getCursor() == WatchmakerCursors.detach) {
        } else if (centrePanel.getCursor() == WatchmakerCursors.kill) {

        } else {

            BoxManager boxes = this.boxedMorphVector.getBoxes();
            Rect releasedInRect = boxes.getBoxNoContainingPoint(point, size);
            // Cancel spawn if released in same rectangle.
            if(selectedBox != releasedInRect) {
                Morph parentMorph = boxedMorphVector.getBoxedMorph(selectedBox)
                        .getMorph();
                MorphConfig config = this.appData.getMorphConfig();
    
                Vector<Point> spawnPoints = this.getEndPoints(selectedBox.getMidPoint(),
                        point);
                for (Point spawnPoint : spawnPoints) {
    
                    Morph morph = config.reproduce(parentMorph);
    
                    Rect margin = morph.getPhenotype().getMargin();
                    int width = margin.getWidth();
                    int height = margin.getHeight();
                    spawnPoint = spawnPoint
                            .subtract(new Point(width / 2, height / 2));
                    Rect newRect = new Rect(spawnPoint.h, spawnPoint.v,
                            spawnPoint.h + width, spawnPoint.v + height);
                    boxes.addBox(newRect, size);
    
                    BoxedMorph boxedMorph = new BoxedMorph(boxes, morph, newRect);
                    boxedMorphVector.add(boxedMorph);
                }
            }
            centrePanel.setCursor(WatchmakerCursors.pedigree);

        }
        super.processMouseReleased(point, size);
    }

}
