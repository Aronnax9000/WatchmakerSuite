package net.richarddawkins.watchmaker.swing.triangle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.Triangler;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.FreeBoxManager;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphView;

public class SwingTriangleMorphView extends SwingMorphView {
    protected static Point[] trianglePoints = new Point[] { new Point(234, 51),
            new Point(134, 250), new Point(333, 250) };
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.triangle.SwingTriangleMorphView");

    private static final long serialVersionUID = -5445629768562940527L;

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
    public SwingTriangleMorphView(AppData appData) {
        super(appData);
        setName("Triangle");
        BoxManager boxes = new FreeBoxManager();
        boxedMorphVector.setBoxes(boxes);
        Dim dim = new Dim(512, 342);
        Morph morph;
        MorphConfig config = appData.getMorphConfig();

        for (int i = 0; i < 3; i++) {
            morph = config.newMorph(i + 1);
            BufferedImage image = (BufferedImage) morph.getImage();
            Point upperLeft = new Point(trianglePoints[i].h,
                    trianglePoints[i].v);
            Rect margin = morph.getPhenotype().getMargin();

            Rect correctedMargin = new Rect(0, 0, margin.getWidth(),
                    margin.getHeight());

            Rect newRect = new Rect(upperLeft.h, upperLeft.v + 2,
                    upperLeft.h + (margin.getWidth() - 3) / 2 + 1,
                    upperLeft.v + (margin.getHeight() + 14) / 2);

            boxes.addBox(newRect, dim);
            BoxedMorph boxedMorph = new BoxedMorph(boxes, morph, i);
            boxedMorphVector.add(boxedMorph);
        }
    }

    @Override
    public synchronized void paintMorphViewPanel(Object graphicsContext,
            Dim size) {
        Graphics2D g2 = (Graphics2D) graphicsContext;
        Vector<Point> points = new Vector<Point>();
        BoxManager boxes = boxedMorphVector.getBoxes();
        for (int boxNo = 0; boxNo < 3; boxNo++) {
            Point midPoint = boxes.getMidPoint(size, boxNo);
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
        super.paintMorphViewPanel(graphicsContext, size);

    }
    // @Override
    // public void paintComponent(Graphics g) {
    // }

    @Override
    public Morph getMorphOfTheHour() {
        return boxedMorphVector
                .getBoxedMorph(boxedMorphVector.getBoxedMorphs().size() - 1)
                .getMorph();
    }

    @Override
    public void seed(Morph morph) {
        // TODO Auto-generated method stub

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
        double[] r = new double[SwingTriangleMorphView.trianglePoints.length];
        double rSum = 0;
        for (int i = 0; i < r.length; i++) {
            r[i] = Math.sqrt(Math
                    .pow(scaledPoint.h
                            - SwingTriangleMorphView.trianglePoints[i].h, 2)
                    + Math.pow(
                            scaledPoint.v
                                    - SwingTriangleMorphView.trianglePoints[i].v,
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
            r[i] = r[i]/rNewSum;
            logger.info("r[i]/newSum " + i + " " + r[i]);
            closenesses += r[i];

        }
        logger.info("Sum of closenesses: " + closenesses);
        
        
        
        return r;
    }

    @Override
    protected void processMouseClicked(Point point, Dim size) {
        logger.info("Triangle box clicked at " + point);
        MorphConfig config = appData.getMorphConfig();
        Genome genome = config.newGenome();
        Triangler triangler = config.getTriangler();
        double[] dists = point2TriangleDists(point, size);
        Genome[] genomes = new Genome[] {
                boxedMorphVector.getBoxedMorph(0).getMorph().getGenome(),
                boxedMorphVector.getBoxedMorph(1).getMorph().getGenome(),
                boxedMorphVector.getBoxedMorph(2).getMorph().getGenome() };
        triangler.concoct(genome, dists, genomes);
        Morph morph = config.newMorph();
        morph.setGenome(genome);
        BoxManager boxes = boxedMorphVector.getBoxes();
        Rect margin = morph.getPhenotype().getMargin();
        int width = margin.getWidth();
        int height = margin.getHeight();
        Rect newRect = new Rect(point.h, point.v, point.h + width,
                point.v + height);
        int boxNo = boxes.getBoxCount();
        boxes.addBox(newRect, size);

        BoxedMorph boxedMorph = new BoxedMorph(boxes, morph, boxNo);

        boxedMorphVector.add(boxedMorph);
        repaint();

    }

    @Override
    protected void processMouseMotion(Point myPt, Dim size) {
        // TODO Auto-generated method stub

    }

}
