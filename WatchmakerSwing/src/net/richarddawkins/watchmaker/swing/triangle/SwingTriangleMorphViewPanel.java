package net.richarddawkins.watchmaker.swing.triangle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.Triangler;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.triangle.TriangleMorphViewPanel;
import net.richarddawkins.watchmaker.phenotype.PhenotypeDrawer;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewPanel;

public class SwingTriangleMorphViewPanel extends SwingMorphViewPanel
        implements TriangleMorphViewPanel {
    public SwingTriangleMorphViewPanel(MorphView morphView,
            BoxedMorphCollection page) {
        super(morphView, page);
        showBoundingBoxes = true;

        // includeChildrenInAutoScale = false;
    }

    protected static Point[] trianglePoints = new Point[] { new Point(234, 51),
            new Point(134, 250), new Point(333, 250) };
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.triangle.SwingTriangleMorphView");

    @Override
    public synchronized void paintMorphViewPanel(Object graphicsContext,
            Dim size) {

        logger.fine("SwingTriangleMorphViewPanel.paintMorphViewPanel() size "
                + size);
        Graphics2D g2 = (Graphics2D) graphicsContext;
        Vector<Point> points = new Vector<Point>();
        BoxManager boxes = boxedMorphCollection.getBoxManager();
        if (boxes.getBoxCount() > 2) {
            for (Rect box : boxes.getBoxes(size)) {
                Point midPoint = boxes.getMidPoint(size, box);
                points.add(midPoint);
            }
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(1));
            Point point0 = points.elementAt(0);
            Point point1 = points.elementAt(1);
            Point point2 = points.elementAt(2);
            g2.drawLine(point0.h, point0.v, point1.h, point1.v);
            g2.drawLine(point1.h, point1.v, point2.h, point2.v);
            g2.drawLine(point2.h, point2.v, point0.h, point0.v);
        }
        super.paintMorphViewPanel(graphicsContext, size);

    }

    @Override
    public Morph getMorphOfTheHour() {
        return boxedMorphCollection.getBoxedMorphs().lastElement().getMorph();
    }

    /**
     * 
     * <pre>
     *      r1 := y / k;
     *      r3 := (x - y / 2) / k;
     *      r2 := (k - x - y / 2) / k;
     * </pre>
     * 
     * @param point
     * @param size
     * @return
     */
    protected double[] point2TriangleDists(Point point, Dim size) {
        Point scaledPoint = new Point(512 * point.h / size.width,
                342 * point.v / size.height);
        logger.info("Scaled point: " + scaledPoint);
        double[] r = new double[SwingTriangleMorphViewPanel.trianglePoints.length];
        double rSum = 0;
        for (int i = 0; i < r.length; i++) {
            r[i] = Math.sqrt(Math.pow(
                    scaledPoint.h
                            - SwingTriangleMorphViewPanel.trianglePoints[i].h,
                    2)
                    + Math.pow(
                            scaledPoint.v
                                    - SwingTriangleMorphViewPanel.trianglePoints[i].v,
                            2));
            rSum += r[i];
            logger.info("Distance to point " + i + " " + r[i]);
        }
        // Normalize lengths
        logger.info("Sum of distances to points: " + rSum);
        double rNewSum = 0;
        double averageDistance = rSum / r.length;
        logger.info("averageDistance to points: " + averageDistance);
        for (int i = 0; i < r.length; i++) {
            r[i] = 1.0 / (r[i] / rSum);
            logger.info("1/(r[i]/rSum) " + i + " " + r[i]);
            rNewSum += r[i];
        }
        double closenesses = 0;
        for (int i = 0; i < r.length; i++) {
            r[i] = r[i] / rNewSum;
            logger.info("r[i]/newSum " + i + " " + r[i]);
            closenesses += r[i];

        }
        logger.info("Sum of closenesses: " + closenesses);

        return r;
    }

    protected Morph getTriangledMorph(Point point, Dim size) {
        MorphConfig config = morphView.getAppData().getMorphConfig();
        Genome genome = config.newGenome();
        Triangler triangler = config.getTriangler();
        double[] dists = point2TriangleDists(point, size);
        Genome[] genomes = new Genome[] {
                boxedMorphCollection.getBoxedMorphs().elementAt(0).getMorph()
                        .getGenome(),
                boxedMorphCollection.getBoxedMorphs().elementAt(1).getMorph()
                        .getGenome(),
                boxedMorphCollection.getBoxedMorphs().elementAt(2).getMorph()
                        .getGenome() };
        triangler.concoct(genome, dists, genomes);
        Morph morph = config.newMorph();
        morph.setGenome(genome);
        return morph;
    }

    @Override
    public void processMouseClicked(Point point, Dim size) {
        logger.info("Triangle box clicked at " + point);
        Morph morph = this.getTriangledMorph(point, size);
        BoxManager boxes = boxedMorphCollection.getBoxManager();
        Rect margin = morph.getPhenotype().getMargin();
        int width = margin.getWidth();
        int height = margin.getHeight();
        Rect newRect = new Rect(point.h, point.v, point.h + width,
                point.v + height);
        boxes.addBox(newRect, size);

        BoxedMorph boxedMorph = new BoxedMorph(boxes, morph, newRect);

        boxedMorphCollection.add(boxedMorph);
        repaint();

    }

    @Override
    public void processMouseMotion(Point point, Dim size) {
        PhenotypeDrawer drawer = morphView.getAppData().getPhenotypeDrawer();
        if(size.height * size.width != 0) {
            Morph morph = this.getTriangledMorph(point, size);
            BufferedImage image = (BufferedImage) drawer.getImage(morph.getPhenotype());
            Image img = image.getScaledInstance(16, 16, Image.SCALE_DEFAULT);
            BufferedImage scaledImage = toBufferedImage(img);
    
            this.setCursor(cursors.newCustomCursor(scaledImage));
        }
    }

}
