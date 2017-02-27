package net.richarddawkins.watchmaker.swing.triangle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
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
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewPanel;

public class SwingTriangleMorphViewPanel extends SwingMorphViewPanel {
    public SwingTriangleMorphViewPanel(MorphView morphView, BoxedMorphCollection page) {
        super(morphView, page);
    }

    protected static Point[] trianglePoints = new Point[] { new Point(234, 51),
            new Point(134, 250), new Point(333, 250) };
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.triangle.SwingTriangleMorphView");

    private static final long serialVersionUID = -5445629768562940527L;


    @Override
    public synchronized void paintMorphViewPanel(Object graphicsContext,
            Dim size) {

        super.paintMorphViewPanel(graphicsContext, size);
        logger.fine("SwingTriangleMorphViewPanel.paintMorphViewPanel() size " + size);
        Graphics2D g2 = (Graphics2D) graphicsContext;
        Vector<Point> points = new Vector<Point>();
        BoxManager boxes = boxedMorphCollection.getBoxes();
        for (Rect box: boxes.getBoxes(size)) {
            Point midPoint = boxes.getMidPoint(size, box);
            points.add(midPoint);
        }
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));
        g2.drawLine(points.elementAt(0).h, points.elementAt(0).v,
                points.elementAt(1).h, points.elementAt(1).v);
        g2.drawLine(points.elementAt(1).h, points.elementAt(1).v,
                points.elementAt(2).h, points.elementAt(2).v);
        g2.drawLine(points.elementAt(2).h, points.elementAt(2).v,
                points.elementAt(0).h, points.elementAt(0).v);

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
            r[i] = Math.sqrt(Math
                    .pow(scaledPoint.h
                            - SwingTriangleMorphViewPanel.trianglePoints[i].h, 2)
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

    @Override
    protected void processMouseClicked(Point point, Dim size) {
        logger.info("Triangle box clicked at " + point);
        MorphConfig config = morphView.getAppData().getMorphConfig();
        Genome genome = config.newGenome();
        Triangler triangler = config.getTriangler();
        double[] dists = point2TriangleDists(point, size);
        Genome[] genomes = new Genome[] {
                boxedMorphCollection.getBoxedMorphs().elementAt(0).getMorph().getGenome(),
                boxedMorphCollection.getBoxedMorphs().elementAt(1).getMorph().getGenome(),
                boxedMorphCollection.getBoxedMorphs().elementAt(2).getMorph().getGenome() };
        triangler.concoct(genome, dists, genomes);
        Morph morph = config.newMorph();
        morph.setGenome(genome);
        BoxManager boxes = boxedMorphCollection.getBoxes();
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
    protected void processMouseMotion(Point myPt, Dim size) {
        // TODO Auto-generated method stub

    }

}
