package net.richarddawkins.watchmaker.morphs.swing;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morphs.mono.geom.Lin;
import net.richarddawkins.watchmaker.morphs.mono.geom.Pic;
import net.richarddawkins.watchmaker.phenotype.DrawingPreferences;
import net.richarddawkins.watchmaker.phenotype.Phenotype;
import net.richarddawkins.watchmaker.phenotype.PhenotypeDrawer;

public abstract class SwingPicDrawer implements PhenotypeDrawer {
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.morphs.swing.SwingPicDrawer");

    protected DrawingPreferences drawingPreferences;

    @Override
    public DrawingPreferences getDrawingPreferences() {
        return this.drawingPreferences;
    }

    protected abstract void limb(Graphics2D g2, Phenotype pic, Lin limb);

    protected void picSpecifics(Graphics2D g2, Phenotype pic) {
        // Default no-op
    }

    @Override
    public Object getImage(Phenotype phenotype) {
        Rect margin = phenotype.getMargin();

        BufferedImage bufferedImage = null;

        int width = margin.getWidth();
        int height = margin.getHeight();
        try {
            bufferedImage = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = bufferedImage.createGraphics();

            g2.translate(-margin.left, -margin.top);
            this.picSpecifics(g2, phenotype);

            if (drawingPreferences.isSpinBabyMorphs()) {
                g2.rotate(-Math.PI * 4);
            }
            for (Lin line : ((Pic) phenotype).lines) {
                limb(g2, phenotype, line);
            }
            logger.fine("SwingPicDrawer.getImage() complete");
        } catch (IllegalArgumentException e) {
            logger.warning(
                    "SwingPicDrawer.getImage failed because of widthXheight: "
                            + width + "x" + height + " for phenotype "
                            + phenotype);
        }
        return bufferedImage;
    }

}
