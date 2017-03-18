package net.richarddawkins.watchmaker.morphview;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.app.AppData;
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
import net.richarddawkins.watchmaker.morph.draw.MorphDrawer;
import net.richarddawkins.watchmaker.util.Globals;

public abstract class SimpleMorphViewPanel implements MorphViewPanel {

    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.morphview.SimpleMorphViewPanel");
    protected boolean autoScale = false;

    protected BoxedMorphCollection boxedMorphCollection;
    protected WatchmakerCursorFactory cursors = null;
    protected GeometryManager geometryManager;

    protected boolean includeChildrenInAutoScale = false;
    protected Point lastMouseDown;
    protected Dim lastMouseDownSize;
    protected Point lastMouseDrag;

    protected Dim lastMouseDragSize;
    public MorphView morphView;
    protected String name;
    protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    protected BoxedMorph selectedBoxedMorph = null;

    protected boolean showBoundingBoxes = false;
    protected Rect special;

    public SimpleMorphViewPanel(MorphView morphView,
            BoxedMorphCollection page) {
        AppData appData = morphView.getAppData();
        this.morphView = morphView;
        if (page == null) {
            logger.warning("SwingMorphViewPanel(" + morphView.toString()
                    + ", page) has null page.");
        } else {
            this.setBoxedMorphCollection(page);
        }
        this.geometryManager = appData.getGeometryManager();
        this.cursors = appData.getWatchmakerCursorFactory();

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);

    }

    @Override
    public void autoScaleBasedOnMorphs() {
        if (autoScale) {
            Vector<Dim> dims = new Vector<Dim>();

            BoxManager boxes = boxedMorphCollection.getBoxManager();
            for (BoxedMorph boxedMorph : boxedMorphCollection
                    .getBoxedMorphs()) {
                dims.addElement(boxedMorph.getMorph().getPhenotype().getMargin()
                        .getDim());
            }

            Dim largestMorphDim;
            largestMorphDim = Dim.getLargest(dims);

            if (boxes.getBoxCount() > 0) {
                // Get a typical box dimension from GridboxManager
                Dim boxDim = boxes.firstElement().getDim();
                int newScale = boxDim.getScale(largestMorphDim,
                        Globals.zoomBase);
                if (newScale != boxes.getScale()) {
                    boxes.setScale(newScale);
                }
            }
        }
    }

    // @Override
    // public void autoScaleBasedOnMorphs(Rect special) {
    // logger.fine("autoScaleBasedOnMorphs " + special + " includeChildren "
    // + this.includeChildrenInAutoScale);
    // Vector<Dim> dims = new Vector<Dim>();
    //
    // BoxManager boxes = boxedMorphCollection.getBoxManager();
    // if (special != null) {
    // BoxedMorph boxedMorphSpecial = boxedMorphCollection
    // .getBoxedMorph(special);
    // if (boxedMorphSpecial != null) {
    // Morph specialMorph = boxedMorphSpecial.getMorph();
    //
    // if (includeChildrenInAutoScale) {
    // Vector<Morph> morphs = specialMorph.getMorphAndChildren();
    // for (Morph morph : morphs) {
    // Phenotype phenotype = morph.getPhenotype();
    // if (phenotype != null) {
    // Rect margin = phenotype.getMargin();
    // Dim dim = margin.getDim();
    // dims.add(dim);
    // } else {
    // }
    // }
    // } else {
    // dims.add(specialMorph.getPhenotype().getMargin().getDim());
    // }
    // }
    // } else {
    // for (BoxedMorph boxedMorph : boxedMorphCollection
    // .getBoxedMorphs()) {
    // dims.addElement(boxedMorph.getMorph().getPhenotype().getMargin()
    // .getDim());
    // }
    //
    // }
    // Dim largestMorphDim;
    // largestMorphDim = Dim.getLargest(dims);
    // if(boxes.getBoxCount() > 0) {
    // Dim boxDim = boxes.firstElement().getDim();
    // int newScale = boxDim.getScale(largestMorphDim, Globals.zoomBase);
    // if (newScale != boxes.getScale()) {
    // boxes.setScale(newScale);
    // }
    // }
    // }
    @Override
    public void clearMorphImages() {
        for (Morph morph : boxedMorphCollection.getMorphs()) {
            morph.setImage(null);
        }
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

    protected void drawMorphs(Object graphicsContext, Dim size,
            boolean showBoundingBoxes, boolean clip) {

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
                MorphDrawer drawer = morphView.getMorphDrawer();
                drawer.draw(boxedMorph, graphicsContext,
                        size, isSelected, showBoundingBoxes, clip);
                counter++;
            }
        }
    }

    @Override
    public void gainFocus() {
        addPropertyChangeListener(morphView.getGeneBoxStrip());
        morphView.getScaleSlider()
                .setBoxManager(boxedMorphCollection.getBoxManager());

    }

    @Override
    public BoxedMorphCollection getBoxedMorphCollection() {
        return boxedMorphCollection;
    }

    @Override
    public boolean getIncludeChildrenInAutoScale() {
        return includeChildrenInAutoScale;
    }

    @Override
    public Morph getMorphOfTheHour() {
        BoxedMorph boxedMorph = boxedMorphCollection.getSelectedBoxedMorph();
        if (boxedMorph == null && selectedBoxedMorph != null) {
            boxedMorph = selectedBoxedMorph;
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

    @Override
    public MorphView getMorphView() {
        return morphView;

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Rect getSpecial() {
        return special;
    }

    @Override
    public boolean isAutoScale() {
        return autoScale;
    }

    @Override
    public boolean isShowBoundingBoxes() {
        return showBoundingBoxes;
    }

    @Override
    public void loseFocus() {
        removePropertyChangeListener(morphView.getGeneBoxStrip());
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
            drawMorphs(graphicsContext, size, showBoundingBoxes, autoScale);
        }
    }

    @Override
    public void processMouseClicked(Point point, Dim size) {
    }

    @Override
    public void processMouseDragged(Point watchmakerPoint, Dim watchmakerDim) {
        this.lastMouseDrag = watchmakerPoint;
        this.lastMouseDragSize = watchmakerDim;
        repaint();
    }

    @Override
    public void processMousePressed(Point watchmakerPoint, Dim watchmakerDim) {
        this.lastMouseDown = watchmakerPoint;
        this.lastMouseDownSize = watchmakerDim;

    }

    @Override
    public void processMouseReleased(Point watchmakerPoint, Dim watchmakerDim) {
        this.lastMouseDown = null;
        this.lastMouseDownSize = null;
        this.lastMouseDrag = null;
        this.lastMouseDragSize = null;
        repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        String propertyName = event.getPropertyName();
        if (propertyName.equals("scale") || propertyName.equals("phenotype")) {
            logger.info("SimpleMorphViewPanel received property change event " + propertyName);
            clearMorphImages();
            repaint();
        }
        
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);

    }

    @Override
    public void setAutoScale(boolean autoScale) {
        this.autoScale = autoScale;
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

    @Override
    public void setIncludeChildrenInAutoScale(
            boolean includeChildrenInAutoScale) {
        this.includeChildrenInAutoScale = includeChildrenInAutoScale;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setSelectedBoxedMorph(BoxedMorph newValue) {
        BoxedMorph oldValue = this.selectedBoxedMorph;
        this.selectedBoxedMorph = newValue;
        pcs.firePropertyChange("selectedBoxedMorph", oldValue, newValue);
    }

    @Override
    public void setShowBoundingBoxes(boolean showBoundingBoxes) {
        this.showBoundingBoxes = showBoundingBoxes;
        repaint();
    }

    @Override
    public void setSpecial(Rect newValue) {
        this.special = newValue;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("MorphViewPanel " + name);
        BoxedMorphCollection boxedMorphs = this.getBoxedMorphCollection();
        if (boxedMorphs != null) {
            stringBuffer.append(" backed by " + boxedMorphs.getName());
        } else {
            stringBuffer.append(" with null boxed morph collection.");
        }
        return stringBuffer.toString();
    }

}
