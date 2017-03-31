package net.richarddawkins.watchmaker.swing.morphview;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.MouseInfo;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import net.richarddawkins.watchmaker.component.WatchPanel;
import net.richarddawkins.watchmaker.cursor.WatchmakerCursor;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.SimpleMorphViewPanel;

public abstract class SwingMorphViewPanel extends SimpleMorphViewPanel
        implements WatchPanel {

    @Override
    public Object getComponent() {

        return panel;
    }

    @Override
    public void loseFocus() {
        super.loseFocus();
        if (morphView.isIndexed()) {
            this.setBorder(null);
        }
    }

    @Override
    public void gainFocus() {
        super.gainFocus();
        if (morphView.isIndexed()) {
            this.setBorder(new LineBorder(Color.BLACK, 3));
        }
    }

    @Override
    public void setLayout(Object mgr) {
        panel.setLayout((LayoutManager) mgr);

    }

    @Override
    public void setBorder(Object border) {
        panel.setBorder((Border) border);

    }

    @Override
    public void setOpaque(boolean isOpaque) {
        panel.setOpaque(isOpaque);

    }

    @Override
    public void add(Object newComponent) {
        panel.add((Component) newComponent);

    }

    @Override
    public void add(Object newComponent, Object constraints) {
        panel.add((Component) newComponent, constraints);

    }

    @Override
    public void removeAll() {
        panel.removeAll();

    }

    protected boolean isDraggable = false;

    @Override
    public Object getCursor() {
        return panel.getCursor();
    }

    protected class ResizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            autoScaleBasedOnMorphs();
        }

    }

    /**
     * Converts a given Image into a BufferedImage
     *
     * @param image
     *            The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(Object image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        Image img = (Image) image;

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null),
                img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewPanel");

    protected JPanel panel;

    public void initPanel() {

        this.panel = new JPanel() {

            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                logger.fine("centrePanel.paintComponent()");
                super.paintComponent(g);
                Dim size = geometryManager.toWatchmakerDim(this.getSize());
                paintMorphViewPanel((Graphics2D) g, size);

            }
        };
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                processMouseClicked(
                        geometryManager.toWatchmakerPoint(e.getPoint()),
                        geometryManager.toWatchmakerDim(
                                ((Component) e.getSource()).getSize()));
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                logger.fine("mouseDragged");
                if (isDraggable) {
                    processMouseDragged(
                            geometryManager.toWatchmakerPoint(e.getPoint()),
                            geometryManager.toWatchmakerDim(
                                    ((Component) e.getSource()).getSize()));
                } else {
                    processMousePressed(
                            geometryManager.toWatchmakerPoint(e.getPoint()),
                            geometryManager.toWatchmakerDim(
                                    ((Component) e.getSource()).getSize()));

                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                processMouseMotion(
                        geometryManager.toWatchmakerPoint(e.getPoint()),
                        geometryManager.toWatchmakerDim(
                                ((Component) e.getSource()).getSize()));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                logger.fine("mousePressed");
                processMousePressed(
                        geometryManager.toWatchmakerPoint(e.getPoint()),
                        geometryManager.toWatchmakerDim(
                                ((Component) e.getSource()).getSize()));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                logger.fine("mouseReleased");
                processMouseReleased(
                        geometryManager.toWatchmakerPoint(e.getPoint()),
                        geometryManager.toWatchmakerDim(
                                ((Component) e.getSource()).getSize()));
            }

        };
        panel.addMouseListener(mouseAdapter);
        panel.addMouseMotionListener(mouseAdapter);
        panel.addComponentListener(new ResizeListener());
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        if (name != null) {
            add(new JLabel(name), constraints);
        }
    }

    public SwingMorphViewPanel(MorphView morphView, BoxedMorphCollection page) {
        super(morphView, page);
    }

    @Override
    public Dim getDim() {
        return geometryManager.toWatchmakerDim(panel.getSize());
    }

    @Override
    public Object getPanel() {
        return panel;
    }

    @Override
    public void processMouseClicked(Point myPt, Dim Size) {
        if (morphView.isIndexed()) {
            Cursor cursor = (Cursor) this.getCursor();
            boolean cursorTypeIsMagnify = cursors
                    .isCursorType(WatchmakerCursor.magnify, cursor);
            if(cursorTypeIsMagnify) {
                morphView.setSelectedPanel(this);
                morphView.setIndexed(false);
            }
        } else {
        }
    }

    @Override
    public void processMouseMotion(Point myPt, Dim size) {
        if (morphView.isIndexed()) {

            this.setCursor(
                    (Cursor) cursors.getCursor(WatchmakerCursor.magnify));
            morphView.setSelectedPanel(this);
        } else {
            Cursor cursor = (Cursor) this.getCursor();
            boolean cursorTypeIsntHighlight = !cursors
                    .isCursorType(WatchmakerCursor.highlight, cursor);
            // boolean selectedBoxedMorphIsntNull = selectedBoxedMorph != null;
            // if (cursorTypeIsntHighlight && selectedBoxedMorphIsntNull) {
            if (cursorTypeIsntHighlight) {
                BoxedMorph boxedMorphUnderCursor = boxedMorphCollection
                        .getBoxedMorph(myPt, size);

                if (boxedMorphUnderCursor != selectedBoxedMorph) {
                    if (boxedMorphUnderCursor != null) {
                        logger.info("Setting selectedBoxMorph to boxed morph "
                                + boxedMorphUnderCursor);
                        setSelectedBoxedMorph(boxedMorphUnderCursor);
                    }
                }
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
