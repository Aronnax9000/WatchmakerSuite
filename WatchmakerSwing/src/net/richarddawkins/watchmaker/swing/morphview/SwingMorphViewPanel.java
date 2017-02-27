package net.richarddawkins.watchmaker.swing.morphview;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
import net.richarddawkins.watchmaker.phenotype.DrawingPreferences;
import net.richarddawkins.watchmaker.swing.SwingGeom;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;
import net.richarddawkins.watchmaker.util.Globals;

public class SwingMorphViewPanel extends JPanel implements MorphViewPanel {
    protected class ResizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            autoScaleBasedOnMorphs(special, getIncludeChildrenInAutoScale());
        }

    }
    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("MorphViewPanel " + this.getName());
        BoxedMorphCollection boxedMorphs = this.getBoxedMorphCollection();
        if(boxedMorphs != null) {
            stringBuffer.append(" backed by " + boxedMorphs.getName());
        } else {
            stringBuffer.append(" with null boxed morph collection.");
        }
        return stringBuffer.toString();
    }
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewPanel");
    private static final long serialVersionUID = 1L;
    protected BoxedMorphCollection boxedMorphCollection;
    protected Point lastMouseDown;
    protected Dim lastMouseDownSize;

    protected Point lastMouseDrag;
    protected Dim lastMouseDragSize;
    public MorphView morphView;
    protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    protected Rect selectedBox = null;
    protected Rect special;
    public SwingMorphViewPanel(MorphView morphView, BoxedMorphCollection page) {
        
        this.morphView = morphView;
        
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                processMouseClicked(SwingGeom.toWatchmakerPoint(e.getPoint()),
                        SwingGeom.toWatchmakerDim(
                                ((Component) e.getSource()).getSize()));
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                logger.fine("mouseDragged");
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
                logger.fine("mousePressed");
                processMousePressed(SwingGeom.toWatchmakerPoint(e.getPoint()),
                        SwingGeom.toWatchmakerDim(
                                ((Component) e.getSource()).getSize()));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                logger.fine("mouseReleased");
                processMouseReleased(SwingGeom.toWatchmakerPoint(e.getPoint()),
                        SwingGeom.toWatchmakerDim(
                                ((Component) e.getSource()).getSize()));
            }

        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        addComponentListener(new ResizeListener());
        
        setBoxedMorphCollection(page);

    }
    public void autoScaleBasedOnMorphs(Rect special, boolean includeChildren) {

        BoxManager boxes = boxedMorphCollection.getBoxes();
        BoxedMorph boxedMorphSpecial = boxedMorphCollection
                .getBoxedMorph(special);
        if (boxedMorphSpecial != null) {
            Morph specialMorph = boxedMorphSpecial.getMorph();

            Dim largestMorphDim;
            
            if(includeChildren) {
            Vector<Morph> morphs = specialMorph.getMorphAndChildren();
            Vector<Dim> dims = new Vector<Dim>();
            for (Morph morph : morphs) {
                Dim dim = morph.getPhenotype().getMargin().getDim();
                logger.fine("Adding dim " + dim);
                dims.add(dim);
            }            
            largestMorphDim = Dim.getLargest(dims);
            } else {
                largestMorphDim = specialMorph.getPhenotype().getMargin().getDim();
            }
            Dim boxDim = boxes.firstElement().getDim();
            int newScale = boxDim.getScale(largestMorphDim, Globals.zoomBase);
            DrawingPreferences drawingPreferences = morphView.getAppData()
                    .getPhenotypeDrawer().getDrawingPreferences();
            if (newScale != boxes.getScale()) {
                boxes.setScale(newScale);
            }
        } else {
            logger.warning("autoScaleBasedOnMorphs: no special boxed morph");
        }
    }
    public void clearMorphImages() {
        for (Morph morph : boxedMorphCollection
                .getMorphs()) {
            morph.setImage(null);
        }
        repaint();
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
    public BoxedMorphCollection getBoxedMorphCollection() {
        return boxedMorphCollection;
    }

    @Override
    public Dim getDim() {
        return SwingGeom.toWatchmakerDim(super.getSize());
    }
    
    public boolean getIncludeChildrenInAutoScale() {
        return true;
    }
    @Override
    public Morph getMorphOfTheHour() {
        Morph morph = boxedMorphCollection.getBoxedMorph(selectedBox).getMorph();
        
        if(morph == null) {
            Rect midBox = boxedMorphCollection.getBoxes().getMidBox();
            if(midBox != null)
                morph = boxedMorphCollection.getBoxedMorph(midBox).getMorph();
            else
                morph = boxedMorphCollection.lastElement().getMorph();
        }
        return morph;
    }

    @Override
    public Vector<Morph> getMorphs() {
        return boxedMorphCollection.getMorphs();
    }    
    public Rect getSpecial() {
        return special;
    }
    @Override
    public void paintComponent(Graphics g) {
        logger.fine("centrePanel.paintComponent()");
        super.paintComponent(g);
        paintMorphViewPanel((Graphics2D) g,
                SwingGeom.toWatchmakerDim(this.getSize()));
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
        logger.fine("SwingMorphViewPanel.paintMorphViewPanel()");
        // Add any pending morphs
        if(getCursor() == null) {
            updateCursor();
        }
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
    protected void processMouseDragged(Point watchmakerPoint,
            Dim watchmakerDim) {
        this.lastMouseDrag = watchmakerPoint;
        this.lastMouseDragSize = watchmakerDim;
        repaint();
    }
    
    protected void processMouseMotion(Point myPt, Dim size) {
        logger.fine("processMouseMotion(" + myPt + ", " + size);
        if (!(this.getCursor() == WatchmakerCursors.highlight
                && boxedMorphCollection.getSelectedBoxedMorph() != null)) {
            BoxManager boxes = boxedMorphCollection.getBoxes();
            Rect box = boxes.getBoxNoContainingPoint(myPt, size);
            if (box != selectedBox) {
                setSelectedBox(box);
            }
        }
    }

    protected void processMousePressed(Point watchmakerPoint,
            Dim watchmakerDim) {
        this.lastMouseDown = watchmakerPoint;
        this.lastMouseDownSize = watchmakerDim;

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
    public void propertyChange(PropertyChangeEvent event) {
        if(event.getPropertyName().equals("scale")) {
            clearMorphImages();
        }
    }



    @Override
    public void setBoxedMorphCollection(BoxedMorphCollection newValue) {
        BoxedMorphCollection oldValue = this.boxedMorphCollection;
        if(oldValue != null) {
            oldValue.getBoxes().removePropertyChangeListener("scale", this);
        }
        this.boxedMorphCollection = newValue;
        if(newValue != null) {
            newValue.getBoxes().addPropertyChangeListener("scale", this);
        }
    }

    public void setSelectedBox(Rect newValue) {
        Rect oldValue = this.selectedBox;
        this.selectedBox = newValue;
        firePropertyChange("selectedBox", oldValue, newValue);
    }
    public void setSpecial(Rect newValue) {
        this.special = newValue;
    }
    @Override
    public void updateCursor() {
        java.awt.Point p = MouseInfo.getPointerInfo().getLocation();
        logger.fine("Raw point " + p);
        SwingUtilities.convertPointFromScreen(p, this);
        logger.fine("Converted point " + p);
        if (p.x > -1 && p.y > -1) {
            Dim size = SwingGeom.toWatchmakerDim(this.getSize());
            logger.fine("updateCursor called");
            processMouseMotion(SwingGeom.toWatchmakerPoint(p), size);
            logger.fine("updateCursor returned");
        }

    }


}
