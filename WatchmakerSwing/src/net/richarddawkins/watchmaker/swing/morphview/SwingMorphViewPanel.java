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
import java.beans.PropertyChangeListener;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.richarddawkins.watchmaker.cursor.WatchmakerCursor;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;
import net.richarddawkins.watchmaker.morphview.SimpleMorphViewPanel;

public class SwingMorphViewPanel extends SimpleMorphViewPanel  implements MorphViewPanel {
    protected class ResizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            autoScaleBasedOnMorphs(special);
        }

    }
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewPanel");
    
    protected JPanel panel;
    public SwingMorphViewPanel(MorphView morphView, BoxedMorphCollection page) {
        super(morphView, page);

        this.panel = new JPanel() {

            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                logger.fine("centrePanel.paintComponent()");
                super.paintComponent(g);
                paintMorphViewPanel((Graphics2D) g,
                        geometryManager.toWatchmakerDim(this.getSize()));
            }
        };
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
        panel.addMouseListener(mouseAdapter);
        panel.addMouseMotionListener(mouseAdapter);
        panel.addComponentListener(new ResizeListener());
    }
    @Override
    public Cursor getCursor() {
        return panel.getCursor();
    }
    


    @Override
    public Dim getDim() {
        return geometryManager.toWatchmakerDim(panel.getSize());
    }

    @Override
    public Object getPanel() { return panel; }

    protected void processMouseMotion(Point myPt, Dim size) {
        Cursor cursor = (Cursor) this.getCursor();
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

    @Override
    public void repaint() {
        panel.repaint();
        
    }
    @Override
    public void setCursor(Object cursor) {
        panel.setCursor((Cursor) cursor);
    }


    @Override
    public void updateCursor() {
        java.awt.Point p = MouseInfo.getPointerInfo().getLocation();
        logger.fine("Raw point " + p);
        SwingUtilities.convertPointFromScreen(p, panel);
        logger.fine("Converted point " + p);
        if (p.x > -1 && p.y > -1) {
            Dim size = geometryManager.toWatchmakerDim(panel.getSize());
            logger.fine("updateCursor called");
            processMouseMotion(geometryManager.toWatchmakerPoint(p), size);
            logger.fine("updateCursor returned");
        }

    }



}
