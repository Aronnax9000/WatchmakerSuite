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
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphView;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewConfig;

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
    public SwingTriangleMorphView(SwingMorphViewConfig config) {
        super(config);
        BoxManager boxes = new FreeBoxManager();
        album.firstElement().setBoxes(boxes);
        Dim dim = new Dim(512, 342);
        MorphConfig morphConfig = appData.getMorphConfig();

        Morph[] triangleMorphs = morphConfig.getTriangleMorphs();
        for (int i = 0; i < 3; i++) {
            Morph morph = triangleMorphs[i];
            BufferedImage image = (BufferedImage) morph.getImage();
            Point upperLeft = new Point(trianglePoints[i].h,
                    trianglePoints[i].v);
            Rect margin = morph.getPhenotype().getMargin();


            Rect newRect = new Rect(upperLeft.h, upperLeft.v + 2,
                    upperLeft.h + (margin.getWidth() - 3) / 2 + 1,
                    upperLeft.v + (margin.getHeight() + 14) / 2);

            boxes.addBox(newRect, dim);
            BoxedMorph boxedMorph = new BoxedMorph(boxes, morph, newRect);
            album.firstElement().add(boxedMorph);
        }
    }
    @Override
    public Morph getMorphOfTheHour() {
        return selectedPanel.getBoxedMorphCollection().lastElement().getMorph();
    }


    @Override
    public void seed() {
        // TODO Auto-generated method stub

    }

}
