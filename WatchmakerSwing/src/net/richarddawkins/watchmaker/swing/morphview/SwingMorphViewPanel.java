package net.richarddawkins.watchmaker.swing.morphview;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.BoxesDrawer;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;
import net.richarddawkins.watchmaker.swing.SwingGeom;

public class SwingMorphViewPanel extends JPanel implements MorphViewPanel {
    protected Point lastMouseDown;
    protected Dim lastMouseDownSize;
    protected Point lastMouseDrag;
    protected Dim lastMouseDragSize;
    private static final long serialVersionUID = 1L;
    protected BoxedMorphCollection boxedMorphCollection;

    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewPanel");
    public MorphView morphView;
    @Override
    public Dim getDim() {
        return SwingGeom.toWatchmakerDim(super.getSize());
    }
    protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    protected BoxManager boxManager;
    public SwingMorphViewPanel(MorphView morphView, BoxedMorphCollection page) {
        this.morphView = morphView;
        this.boxedMorphCollection = page;
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                processMouseClicked(SwingGeom.toWatchmakerPoint(e.getPoint()),
                        SwingGeom.toWatchmakerDim(
                                ((Component) e.getSource()).getSize()));
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                logger.info("mouseDragged");
                processMouseDragged(SwingGeom.toWatchmakerPoint(e.getPoint()),
                        SwingGeom.toWatchmakerDim(
                                ((Component) e.getSource()).getSize()));
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                processMouseMotion(SwingGeom.toWatchmakerPoint(e.getPoint()),
                        SwingGeom.toWatchmakerDim(
                                ((Component) e.getSource()).getSize()));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                logger.info("mousePressed");
                processMousePressed(SwingGeom.toWatchmakerPoint(e.getPoint()),
                        SwingGeom.toWatchmakerDim(
                                ((Component) e.getSource()).getSize()));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                logger.info("mouseReleased");
                processMouseReleased(SwingGeom.toWatchmakerPoint(e.getPoint()),
                        SwingGeom.toWatchmakerDim(
                                ((Component) e.getSource()).getSize()));
            }

        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        
    }

    @Override
    public void paintComponent(Graphics g) {
        logger.fine("centrePanel.paintComponent()");
        super.paintComponent(g);
        paintMorphViewPanel((Graphics2D) g,
                SwingGeom.toWatchmakerDim(this.getSize()));
    }
    
    @Override
    public BoxedMorphCollection getBoxedMorphCollection() {
        return boxedMorphCollection;
    }
    @Override
    public Morph getMorphOfTheHour() {
        Morph morph = boxedMorphCollection.getSelectedBoxedMorph().getMorph();
        
        if(morph == null) {
            Rect midBox = boxedMorphCollection.getBoxes().getMidBox();
            if(midBox != null)
                morph = boxedMorphCollection.getBoxedMorph(midBox).getMorph();
            else
                morph = boxedMorphCollection.lastElement().getMorph();
        }
        return morph;
    }

    /**
     * Draw boxes in box order (not in boxed Morph order.)
     * 
     * @param graphicsContext
     *            the abstract graphics context onto which subclasses should do
     *            their drawing.
     * @param size
     *            the current display size of the graphics context drawing area.
     */
    protected void drawBoxes(Object graphicsContext, Dim size) {
        BoxManager boxes = boxedMorphCollection.getBoxes();
        Vector<Integer> backgroundColors = new Vector<Integer>();
        for (int i = 0; i < boxes.getBoxCount(); i++) {
            BoxedMorph boxedMorph = boxedMorphCollection
                    .getBoxedMorph(boxes.getBox(i));
            if (boxedMorph != null) {
                backgroundColors.add(boxedMorph.getMorph().getPhenotype()
                        .getBackgroundColor());
            } else {
                backgroundColors.add(-1);
            }
        }

        BoxesDrawer boxesDrawer = morphView.getAppData().getBoxesDrawer();
        boolean midBoxOnly = boxedMorphCollection.getBoxedMorphs().size() == 1;
        boxesDrawer.draw(graphicsContext, size, boxes, midBoxOnly,
                backgroundColors);
    }    
    @Override
    public void setBoxedMorphCollection(BoxedMorphCollection boxedMorphVector) {
        this.boxedMorphCollection = boxedMorphVector;
    }
    /**
     * Draw the MorphView's breeding box outlines (if showBoxes is set) and its
     * morphs, on the MorphView's centre panel.
     * 
     * Morph phenotypes have a background color, which PhenotypeDrawer instances
     * may use to fill the bounding rectangle of the morph. In order to also
     * paint the morph's background color within its breeding box, this
     * implementation iterates through the morph collection in box number order,
     * and builds a list of background colors to be passed to
     * BoxesDrawer.draw().
     * 
     */
    @Override
    public synchronized void paintMorphViewPanel(Object graphicsContext,
            Dim size) {
        // Add any pending morphs
        morphView.seed();
        synchronized (boxedMorphCollection) {
            if (morphView.isShowBoxes()) {
                drawBoxes(graphicsContext, size);
            }
            drawMorphs(graphicsContext, size);
        }
    }

    protected void processMouseClicked(Point point, Dim size) {
    }

    protected void processMouseMotion(Point myPt, Dim size) {
    }

    protected void processMousePressed(Point watchmakerPoint,
            Dim watchmakerDim) {
        this.lastMouseDown = watchmakerPoint;
        this.lastMouseDownSize = watchmakerDim;

    }

    protected void processMouseDragged(Point watchmakerPoint,
            Dim watchmakerDim) {
        this.lastMouseDrag = watchmakerPoint;
        this.lastMouseDragSize = watchmakerDim;
        repaint();
    }

    protected void processMouseReleased(Point watchmakerPoint,
            Dim watchmakerDim) {
        this.lastMouseDown = null;
        this.lastMouseDownSize = null;
        this.lastMouseDrag = null;
        this.lastMouseDragSize = null;
        repaint();
    }



    @Override
    public Vector<Morph> getMorphs() {
        return boxedMorphCollection.getMorphs();
    }

    protected void drawMorphs(Object graphicsContext, Dim size) {

        int counter = 0;
        Iterator<BoxedMorph> iter = boxedMorphCollection.iterator();
        logger.fine("Boxed morphs to paint: "
                + boxedMorphCollection.getBoxedMorphs().size());
        while (iter.hasNext()) {
            logger.fine(
                    "SwingMorphView.paintMorphViewPanel() Getting BoxedMorph "
                            + counter);
            BoxedMorph boxedMorph = iter.next();
            morphView.getMorphDrawer().draw(boxedMorph, graphicsContext, size,
                    boxedMorph == this.boxedMorphCollection
                            .getSelectedBoxedMorph());
            counter++;
        }

    }
    @Override
    public void updateCursor() {
        java.awt.Point p = MouseInfo.getPointerInfo().getLocation();
        logger.fine("Raw point " + p);
        SwingUtilities.convertPointFromScreen(p, this);
        logger.fine("Converted point " + p);
        if (p.x > -1 && p.y > -1) {
            Dim size = SwingGeom.toWatchmakerDim(this.getSize());
            logger.fine("processMouseMotion called");
            processMouseMotion(SwingGeom.toWatchmakerPoint(p), size);
            logger.fine("processMouseMotion returned");
        }

    }

    @Override
    public void setBoxManager(BoxManager boxManager) {
        this.boxManager = boxManager;
        
    }
}
