package net.richarddawkins.watchmaker.swing.morphview;

import java.awt.Component;
import java.awt.Cursor;
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

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.cursor.WatchmakerCursor;
import net.richarddawkins.watchmaker.cursor.WatchmakerCursorFactory;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.BoxesDrawer;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.GeometryManager;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;
import net.richarddawkins.watchmaker.phenotype.DrawingPreferences;
import net.richarddawkins.watchmaker.phenotype.Phenotype;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;
import net.richarddawkins.watchmaker.util.Globals;

public class SwingMorphViewPanel extends JPanel implements MorphViewPanel {
    protected GeometryManager geometryManager;
    protected WatchmakerCursorFactory cursors = null;
    @Override
    public void gainFocus() {
        addPropertyChangeListener(morphView.getGeneBoxStrip());
        morphView.getScaleSlider().setBoxManager(boxedMorphCollection.getBoxManager());

    }
    @Override
    public void loseFocus() {
        removePropertyChangeListener(morphView.getGeneBoxStrip());
    }
    @Override
    public void setCursor(Object cursor) {
        super.setCursor((Cursor) cursor);
    }
    
    protected boolean autoScale = false;
    @Override
    public boolean isAutoScale() {
        return autoScale;
    }

    @Override
    public void setAutoScale(boolean autoScale) {
        this.autoScale = autoScale;
    }

    @Override
    public MorphView getMorphView() {
        return morphView;

    }

    protected class ResizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            autoScaleBasedOnMorphs(special);
        }

    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("MorphViewPanel " + this.getName());
        BoxedMorphCollection boxedMorphs = this.getBoxedMorphCollection();
        if (boxedMorphs != null) {
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
        AppData appData = morphView.getAppData();
        this.geometryManager = appData.getGeometryManager();
        this.cursors = appData.getWatchmakerCursorFactory();
        if (page == null) {
            logger.warning("SwingMorphViewPanel(" + morphView.toString()
                    + ", page) has null page.");
        } else {
            this.setBoxedMorphCollection(page);
        }
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                processMouseClicked(geometryManager.toWatchmakerPoint(e.getPoint()),
                        geometryManager.toWatchmakerDim(
                                ((Component) e.getSource()).getSize()));
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                logger.fine("mouseDragged");
                processMouseDragged(geometryManager.toWatchmakerPoint(e.getPoint()),
                        geometryManager.toWatchmakerDim(
                                ((Component) e.getSource()).getSize()));
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                processMouseMotion(geometryManager.toWatchmakerPoint(e.getPoint()),
                        geometryManager.toWatchmakerDim(
                                ((Component) e.getSource()).getSize()));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                logger.fine("mousePressed");
                processMousePressed(geometryManager.toWatchmakerPoint(e.getPoint()),
                        geometryManager.toWatchmakerDim(
                                ((Component) e.getSource()).getSize()));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                logger.fine("mouseReleased");
                processMouseReleased(geometryManager.toWatchmakerPoint(e.getPoint()),
                        geometryManager.toWatchmakerDim(
                                ((Component) e.getSource()).getSize()));
            }

        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        addComponentListener(new ResizeListener());

    }

    public void autoScaleBasedOnMorphs(Rect special) {
//        logger.fine("autoScaleBasedOnMorphs " + special + " includeChildren "
//                + this.includeChildrenInAutoScale);
        Vector<Dim> dims = new Vector<Dim>();

        BoxManager boxes = boxedMorphCollection.getBoxManager();
        if (special != null) {
            BoxedMorph boxedMorphSpecial = boxedMorphCollection
                    .getBoxedMorph(special);
            if (boxedMorphSpecial != null) {
                Morph specialMorph = boxedMorphSpecial.getMorph();

                if (includeChildrenInAutoScale) {
                    Vector<Morph> morphs = specialMorph.getMorphAndChildren();
                    for (Morph morph : morphs) {
                        Phenotype phenotype = morph.getPhenotype();
                        if (phenotype != null) {
                            Rect margin = phenotype.getMargin();
                            Dim dim = margin.getDim();
//                            logger.fine("Adding dim " + dim);
                            dims.add(dim);
                        } else {
//                            logger.fine(
//                                    "autoScaleBasedOnMorphs: No phenotype for morph "
//                                            + morph);
                        }
                    }
                } else {
                    dims.add(specialMorph.getPhenotype().getMargin().getDim());
                }
            }
        } else {
//            logger.fine(
//                    "autoScaleBasedOnMorphs: no special boxed morph. Defaulting to entire boxedMorphCollection");
            for (BoxedMorph boxedMorph : boxedMorphCollection
                    .getBoxedMorphs()) {
                dims.addElement(boxedMorph.getMorph().getPhenotype().getMargin()
                        .getDim());
            }

        }
        Dim largestMorphDim;
        largestMorphDim = Dim.getLargest(dims);
        if(boxes.getBoxCount() > 0) {
            Dim boxDim = boxes.firstElement().getDim();
            int newScale = boxDim.getScale(largestMorphDim, Globals.zoomBase);
            DrawingPreferences drawingPreferences = morphView.getAppData()
                    .getPhenotypeDrawer().getDrawingPreferences();
            if (newScale != boxes.getScale()) {
                boxes.setScale(newScale);
            }
        }
    }


    public void clearMorphImages() {
        for (Morph morph : boxedMorphCollection.getMorphs()) {
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
        BoxManager boxes = boxedMorphCollection.getBoxManager();
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
    
    protected void drawMorphs(Object graphicsContext, Dim size, boolean showBoundingBoxes) {

        synchronized (boxedMorphCollection) {
            int counter = 0;
            Iterator<BoxedMorph> iter = boxedMorphCollection.iterator();
            logger.fine("Boxed morphs to paint: "
                    + boxedMorphCollection.getBoxedMorphs().size());
            while (iter.hasNext()) {
                logger.fine(
                        "SwingMorphView.paintMorphViewPanel() Getting BoxedMorph "
                                + counter);
                BoxedMorph boxedMorph = iter.next();
                boolean isSelected = boxedMorph == this.boxedMorphCollection
                        .getSelectedBoxedMorph();
                

                morphView.getMorphDrawer().draw(boxedMorph, graphicsContext,
                        size, isSelected, showBoundingBoxes);
                counter++;
            }
        }
    }

    @Override
    public BoxedMorphCollection getBoxedMorphCollection() {
        return boxedMorphCollection;
    }

    @Override
    public Dim getDim() {
        return geometryManager.toWatchmakerDim(super.getSize());
    }
    protected boolean includeChildrenInAutoScale = false;
    public void setIncludeChildrenInAutoScale(boolean includeChildrenInAutoScale) {
        this.includeChildrenInAutoScale = includeChildrenInAutoScale;
    }

    public boolean getIncludeChildrenInAutoScale() {
        return includeChildrenInAutoScale;
    }

    @Override
    public Morph getMorphOfTheHour() {
        BoxedMorph boxedMorph = boxedMorphCollection.getSelectedBoxedMorph();
        if (boxedMorph == null && selectedBox != null) {
            boxedMorph = boxedMorphCollection.getBoxedMorph(selectedBox);
        }

        Morph morph = null;

        if (boxedMorph != null)
            morph = boxedMorph.getMorph();

        if (morph == null) {
            Rect midBox = boxedMorphCollection.getBoxManager().getMidBox();
            if (midBox != null)
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
                geometryManager.toWatchmakerDim(this.getSize()));
    }
    protected boolean showBoundingBoxes = false;

    public boolean isShowBoundingBoxes() {
        return showBoundingBoxes;
    }

    public void setShowBoundingBoxes(boolean showBoundingBoxes) {
        this.showBoundingBoxes = showBoundingBoxes;
        repaint();
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
        if (getCursor() == null) {
            updateCursor();
        }
        morphView.seed();
        synchronized (boxedMorphCollection) {
            if (morphView.isShowBoxes()) {
                drawBoxes(graphicsContext, size);
            }
            drawMorphs(graphicsContext, size, showBoundingBoxes);
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
        Cursor cursor = this.getCursor();
        boolean cursorTypeIsntHighlight = ! cursors.isCursorType(WatchmakerCursor.highlight, cursor);
        BoxedMorph selectedBoxedMorph = boxedMorphCollection.getSelectedBoxedMorph();
        boolean selectedBoxedMorphIsntNull = selectedBoxedMorph != null;
        if (cursorTypeIsntHighlight && selectedBoxedMorphIsntNull)  {
            BoxManager boxes = boxedMorphCollection.getBoxManager();
            Rect box = boxes.getBoxNoContainingPoint(myPt, size);
            if (box != selectedBox) {
                logger.info("Setting SelectedBox to box " + box + " containing boxed morph " + 
            selectedBoxedMorph);
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
        if (event.getPropertyName().equals("scale")) {
            clearMorphImages();
        }
    }

    @Override
    public void setBoxedMorphCollection(BoxedMorphCollection newValue) {
        BoxedMorphCollection oldValue = this.boxedMorphCollection;
        if (oldValue != null) {
            oldValue.getBoxManager().removePropertyChangeListener("scale",
                    this);
        }
        this.boxedMorphCollection = newValue;
        if (newValue != null) {
            newValue.getBoxManager().addPropertyChangeListener("scale", this);
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
            Dim size = geometryManager.toWatchmakerDim(this.getSize());
            logger.fine("updateCursor called");
            processMouseMotion(geometryManager.toWatchmakerPoint(p), size);
            logger.fine("updateCursor returned");
        }

    }

}
