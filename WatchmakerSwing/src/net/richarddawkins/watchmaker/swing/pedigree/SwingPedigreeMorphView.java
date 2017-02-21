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
     * 512x384.
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

    @Override
    public synchronized void paintMorphViewPanel(Object graphicsContext,
            Dim size) {
        Graphics2D g2 = (Graphics2D) graphicsContext;
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));
        if (centrePanel.getCursor() != WatchmakerCursors.move
                && this.lastMouseDown != null && this.lastMouseDrag != null) {
            int dx = lastMouseDrag.h - lastMouseDown.h;
            int dy = lastMouseDrag.v - lastMouseDown.v;
            g2.drawLine(lastMouseDown.h, lastMouseDown.v, lastMouseDown.h + dx,
                    lastMouseDown.v + dy);
            if (mirrorType == MirrorType.SINGLE
                    || mirrorType == MirrorType.DOUBLE) {
                g2.drawLine(lastMouseDown.h, lastMouseDown.v,
                        lastMouseDown.h - dx, lastMouseDown.v - dy);
                if (mirrorType == MirrorType.DOUBLE) {
                    g2.drawLine(lastMouseDown.h, lastMouseDown.v,
                            lastMouseDown.h + dx, lastMouseDown.v - dy);
                    g2.drawLine(lastMouseDown.h, lastMouseDown.v,
                            lastMouseDown.h - dx, lastMouseDown.v + dy);
                }
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
        return boxedMorphVector
                .getBoxedMorphs().elementAt(boxedMorphVector.getBoxedMorphs().size() - 1)
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
        Morph morph = boxedMorphVector.getBoxedMorph(selectedBox).getMorph();
        if (selectedBox != null) {

            this.lastMouseDown = point;
            this.lastMouseDownSize = size;
            if (centrePanel.getCursor() == WatchmakerCursors.pedigree) {
                centrePanel.setCursor(WatchmakerCursors.createCustomCursor(
                        ((BufferedImage) morph.getImage()).getScaledInstance(16,
                                16, Image.SCALE_DEFAULT)));
            } else if (centrePanel.getCursor() == WatchmakerCursors.detach) {
                morph.getPedigree().parent = null;
            } else if (centrePanel.getCursor() == WatchmakerCursors.kill) {
                BoxedMorph boxedMorph = boxedMorphVector.getBoxedMorph(selectedBox);
                Vector<BoxedMorph> boxedMorphsToKill = boxedMorphVector.findBoxedMorphsForMorphAndDescendents(boxedMorph);
                for(BoxedMorph victim: boxedMorphsToKill) {
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
                
                int dx = point.h - this.lastMouseDown.h;
                int dy = point.v - this.lastMouseDown.v;
                
                selectedBox.left += dx;
                selectedBox.right += dx;
                selectedBox.top += dy;
                selectedBox.bottom += dy;
                boxedMorphVector.getBoxes().setBox(selectedBox, selectedBox, size);
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
            centrePanel.setCursor(WatchmakerCursors.pedigree);
            Morph parentMorph = boxedMorphVector.getBoxedMorph(selectedBox)
                    .getMorph();
            MorphConfig config = this.appData.getMorphConfig();
            Morph morph = config.reproduce(parentMorph);

            Rect margin = morph.getPhenotype().getMargin();
            int width = margin.getWidth();
            int height = margin.getHeight();
            point = point.subtract(new Point(width /2, height/ 2));
            Rect newRect = new Rect(point.h, point.v, point.h + width,
                    point.v + height);
            boxes.addBox(newRect, size);

            BoxedMorph boxedMorph = new BoxedMorph(boxes, morph, newRect);
            boxedMorphVector.add(boxedMorph);
        }
        super.processMouseReleased(point, size);
    }

}
