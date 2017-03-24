package net.richarddawkins.watchmaker.swing.pedigree;

import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.FreeBoxManager;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphview.MorphViewConfig;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;
import net.richarddawkins.watchmaker.morphview.pedigree.MirrorType;
import net.richarddawkins.watchmaker.morphview.pedigree.PedigreeMorphView;
import net.richarddawkins.watchmaker.morphview.pedigree.PedigreeMorphViewPanel;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphView;

public class SwingPedigreeMorphView extends SwingMorphView
        implements PedigreeMorphView {
    
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.pedigree.SwingPedigreeMorphView");


    protected MirrorType mirrorType = MirrorType.NONE;

    public MirrorType getMirrorType() {
        return mirrorType;
    }

    public void setMirrorType(MirrorType mirrorType) {
        this.mirrorType = mirrorType;
        PedigreeMorphViewPanel currentPanel = (PedigreeMorphViewPanel)this.getSelectedPanel();
        
    }

    /**
     * The Macintosh II, the first color model, had a screen resolution of
     * 512x384. Richard apparently had a screen height of only 342 to work with,
     * which is 42 pixels shorter. RIP DNA. - ABC
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
    public SwingPedigreeMorphView(MorphViewConfig config) {
        super(config);
        this.showBoxes = false;
    }

    @Override
    public void addPanels() {

        MorphViewPanel panel = new SwingPedigreeMorphViewPanel(this,
                album.getPage(0));
        addPanel(panel);
    }

    @Override
    public BoxManager newBoxManager() {
        return new FreeBoxManager();
    }

    @Override
    public void seed() {
        if (!seedMorphs.isEmpty()) {
            synchronized (seedMorphs) {
                logger.info("SwingPedigreeMorphView.seed() with "
                        + seedMorphs.size() + " seedMorphs");
                Morph morph = seedMorphs.firstElement();
                // Dim dim = new Dim(512, 342);
                BoxManager boxes = album.firstElement().getBoxManager();
                BufferedImage image = (BufferedImage) morph.getImage();
                MorphViewPanel panel = panels.firstElement();
                Dim size = panel.getDim();
                // Point upperLeft = new Point(255, 170);
                Point upperLeft = new Point(size.width / 2, size.height / 2);
                Rect margin = morph.getPhenotype().getMargin();
                int boxPad = 4;
                int width = margin.getWidth() + boxPad;
                int height = margin.getHeight() + boxPad;
                upperLeft = upperLeft
                        .subtract(new Point(width / 2, height / 2));
                Rect newRect = new Rect(upperLeft.h, upperLeft.v,
                        upperLeft.h + width / 2, upperLeft.v + height / 2);
                logger.info("Adding rect " + newRect + " with screen size: "
                        + size);
                boxes.addBox(newRect, size);
                BoxedMorph boxedMorph = new BoxedMorph(boxes, morph, newRect);
                album.firstElement().add(boxedMorph);
                seedMorphs.clear();
            }
        }
    }


}
