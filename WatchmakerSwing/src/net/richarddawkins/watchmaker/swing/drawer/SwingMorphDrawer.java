package net.richarddawkins.watchmaker.swing.drawer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.MorphDrawer;
import net.richarddawkins.watchmaker.phenotype.Phenotype;
import net.richarddawkins.watchmaker.phenotype.PhenotypeDrawer;
import net.richarddawkins.watchmaker.util.Globals;

public class SwingMorphDrawer implements MorphDrawer {
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.drawer.SwingMorphDrawer");
    protected AppData appData;

    public SwingMorphDrawer(AppData appData) {
        this.appData = appData;
    }

    /**
     * Copy an image
     * 
     * @param source
     *            the image to copy
     * @param scale
     *            the double precision scale to use.
     * @return a copy of the image.
     */
    public static BufferedImage copyImage(BufferedImage source, double scale) {
        int width = (int) Math.ceil(source.getWidth() * scale);
        int height = (int) Math.ceil(source.getHeight() * scale);
        BufferedImage b = null;
        if (width == 0 || height == 0) {
            logger.warning("BufferedImage.copyImage scale: " + scale
                    + " widthXheight:" + width + "x" + height);

        } else {
            try {
                b = new BufferedImage(width, height, source.getType());
                Graphics2D g = b.createGraphics();
                if (scale != 1.0) {
                    g.scale(scale, scale);
                }
                g.drawImage(source, 0, 0, null);
                g.dispose();
            } catch (IllegalArgumentException e) {
                logger.warning("copyImage scale " + scale + " width " + width
                        + " height " + height + " " + e.getMessage());
            } catch (NegativeArraySizeException e) {
                logger.warning("copyImage scale " + scale + " width " + width
                        + " height " + height + " " + e.getMessage());
            }
        }
        return b;
    }

    @Override
    public void draw(BoxedMorph boxedMorph, Object graphicsContext, Dim size,
            boolean selectionState, boolean showBoundingBox) {
        Phenotype phenotype = boxedMorph.getMorph().getPhenotype();
        Graphics2D g2 = (Graphics2D) ((Graphics2D) graphicsContext).create();
//        Graphics2D g2 = (Graphics2D) graphicsContext;
        AffineTransform saveTransform = g2.getTransform();
        Shape saveClip = g2.getClip();
        
        logger.fine("Draw BoxedMorph, saved transform and clip");
        if(boxedMorph.getDestinationBox() == null) {
            // not going anywhere, clip the home box.
            Rect box = boxedMorph.getBox();
            g2.setClip(box.left + 2, box.top + 2, box.getWidth() - 4,
                    box.getHeight() - 4);

        }
        double scale = 1;
        if (boxedMorph.isScaleWithProgress()) {
            scale = boxedMorph.getProgress();
            logger.fine("Scale: " + scale);
        }
        scale *= Math.pow(Globals.zoomBase, boxedMorph.getBoxes().getScale());
        logger.fine("Scale: " + scale);

        Morph morph = boxedMorph.getMorph();
        BufferedImage bufferedImage = (BufferedImage) morph.getImage();
        if (morph.getImage() == null) {
            PhenotypeDrawer picDrawer = appData.getPhenotypeDrawer();

            bufferedImage = (BufferedImage) picDrawer.getImage(phenotype);
            morph.setImage(bufferedImage);
        }
        if (scale > 0 && bufferedImage != null) { // not infinitesimal
            bufferedImage = copyImage(bufferedImage, scale);

            if (bufferedImage != null) {
                Point position = boxedMorph.getPosition(size);

                // Translate to the point where the centre of drawing is to take
                // place.
                g2.translate(position.h, position.v);
                // Translate up and to the left by the major radius of the
                // rectangle
                // to be drawn, so that the rectangle is centered on the above
                // position when drawn down and to the right.
                g2.translate((int) Math.ceil(-bufferedImage.getWidth() / 2.0d),
                        (int) Math.ceil(-bufferedImage.getHeight() / 2.0d));

                g2.clearRect(0, 0, bufferedImage.getWidth() - 1,
                        bufferedImage.getHeight() - 1);
                g2.drawImage(bufferedImage, 0, 0, null);
                if (showBoundingBox) {
                    g2.setColor(Color.BLACK);
                    g2.setStroke(new BasicStroke(1));
                    g2.drawRect(0, 0, bufferedImage.getWidth() - 1,
                            bufferedImage.getHeight() - 1);
                }
                if (selectionState) {
                    g2.setColor(Color.BLACK);
                    g2.setStroke(new BasicStroke(2));
                    g2.drawRect(0, 0, bufferedImage.getWidth() - 1,
                            bufferedImage.getHeight() - 1);
                }
                String name = morph.getName();
                if (name != null) {
                    FontRenderContext frc = g2.getFontRenderContext();

                    float width = (float) g2.getFont()
                            .getStringBounds(name, frc).getWidth();
                    float height = (float) g2.getFont()
                            .getStringBounds(name, frc).getHeight();
                    g2.translate((bufferedImage.getWidth() - width) / 2,
                            bufferedImage.getHeight() + height);
                    g2.drawString(name, 0, 0);
                }
            } else {
                // put placeholder morph here?
                logger.warning(
                        "Buffered image null on copy, probably too small.");
            }
        }
        g2.setTransform(saveTransform);
        g2.setClip(saveClip);
    }
}
