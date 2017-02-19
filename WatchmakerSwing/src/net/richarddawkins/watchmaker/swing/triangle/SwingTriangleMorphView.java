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

    protected double[] point2TriangleDists(Point point) {
        double[] r = new double[SwingTriangleMorphView.trianglePoints.length];
        double rSum = 0;
        for(int i = 0; i < r.length; i++) {
            r[i] = Math.sqrt(Math.pow(point.h - SwingTriangleMorphView.trianglePoints[i].h, 2) +
                    Math.pow(point.v - SwingTriangleMorphView.trianglePoints[i].v, 2));
            rSum += r[i];
        }
        // Normalize lengths
        for(int i = 0; i < r.length; i++) {
            r[i] /= rSum;
        }
        return r;
    }
    
    @Override
    protected void boxClicked(Point point) {
        logger.info("Triangle box clicked at " + point);
        MorphConfig config = appData.getMorphConfig();
        Genome genome = config.newGenome();
        Triangler triangler = config.getTriangler();
        double[] dists = point2TriangleDists(point);
        Genome a = boxedMorphVector.getBoxedMorph(0).getMorph().getGenome();
        Genome b = boxedMorphVector.getBoxedMorph(1).getMorph().getGenome();
        Genome c = boxedMorphVector.getBoxedMorph(2).getMorph().getGenome();
        triangler.concoct(genome, dists[0], dists[1], dists[2], a, b, c);
        Morph morph = config.newMorph();
        morph.setGenome(genome);
        
        
    }

    @Override
    protected void processMouseMotion(Point myPt, Dim size) {
        // TODO Auto-generated method stub

    }

}
