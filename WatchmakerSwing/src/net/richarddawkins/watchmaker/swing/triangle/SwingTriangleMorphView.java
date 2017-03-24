package net.richarddawkins.watchmaker.swing.triangle;

import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.FreeBoxManager;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphViewConfig;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;
import net.richarddawkins.watchmaker.morphview.triangle.TriangleMorphView;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphView;

/**
 * The Macintosh II, the first color model, had a screen resolution of
 * 512x384. Richard's display area was (apparently, based on his own source code)
 * 512x342 a, difference of 42 pixels in height. RIP Douglas Adams (1952-2001) - ABC
 * 
 * <pre>
 *      a.h := round(234 * ScreenWidth / 512);
 *      a.v := round(51 * ScreenHeight / 342);
 *      b.h := round(134 * ScreenWidth / 512);
 *      b.v := round(250 * ScreenHeight / 342);
 *      c.h := round(333 * ScreenWidth / 512);
 *      c.v := round(250 * ScreenHeight / 342);
 * </pre>
 * 
 */
public class SwingTriangleMorphView extends SwingMorphView implements TriangleMorphView {
    protected static final Point[] trianglePoints = new Point[] { new Point(234, 51),
            new Point(134, 250), new Point(333, 250) };
    protected static final Dim traditionalMacDim = new Dim(512,342);
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.triangle.SwingTriangleMorphView");


    @Override
    public BoxManager newBoxManager() {
        return new FreeBoxManager();
    }

    
    public SwingTriangleMorphView(MorphViewConfig config) {
        super(config);
        this.showBoxes = false;
    }
    @Override
    public Morph getMorphOfTheHour() {
        return selectedPanel.getBoxedMorphCollection().lastElement().getMorph();
    }

    @Override
    public void addPanels() {
        MorphViewPanel morphViewPanel = new SwingTriangleMorphViewPanel(this, album.getPage(0));
        addPanel(morphViewPanel);
    }

    @Override
    public void seed() {
        if(! seedMorphs.isEmpty()) {
            synchronized(seedMorphs) {
                Morph[] triangleMorphs = seedMorphs.toArray(new Morph[0]);
                BoxedMorphCollection boxedMorphs = selectedPanel.getBoxedMorphCollection();
                BoxManager boxes = boxedMorphs.getBoxManager();
                for (int i = 0; i < 3; i++) {
                    Morph morph = triangleMorphs[i];
                    BufferedImage image = (BufferedImage) morph.getImage();
                    Point trianglePoint = SwingTriangleMorphView.trianglePoints[i];
                    Point upperLeft = new Point(trianglePoint.h,
                            trianglePoint.v);
                    Rect margin = morph.getPhenotype().getMargin();
                    int halfMarginWidth = margin.getWidth() / 2;
                    int halfMarginHeight = margin.getHeight() / 2;
                    Rect newRect = new Rect(upperLeft.h, 
                            upperLeft.v - 2,
                            upperLeft.h + halfMarginWidth + 2,
                            upperLeft.v + halfMarginHeight + 4);
    
                    boxes.addBox(newRect, SwingTriangleMorphView.traditionalMacDim);
                    BoxedMorph boxedMorph = new BoxedMorph(boxes, morph, newRect);
                    logger.info("SwingTriangleMorphView.seed() adding boxedMorph " + boxedMorph);
                    boxedMorphs.add(boxedMorph);
                    seedMorphs.remove(0);
                }
            }
            repaint();
        }

    }




}
