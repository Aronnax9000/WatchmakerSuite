package net.richarddawkins.watchmaker.morphs.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morphs.bio.geom.Lin;
import net.richarddawkins.watchmaker.morphs.bio.geom.Pic;
import net.richarddawkins.watchmaker.phenotype.DrawingPreferences;
import net.richarddawkins.watchmaker.phenotype.Phenotype;
import net.richarddawkins.watchmaker.phenotype.PhenotypeDrawer;
import net.richarddawkins.watchmaker.swing.SwingGeom;



public abstract class SwingPicDrawer implements PhenotypeDrawer {
	
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
    public void drawPic(Object graphicsContext, Phenotype phenotype) {
    	Graphics2D g2 = (Graphics2D) graphicsContext;
        AffineTransform saveTransform = g2.getTransform();
        Pic pic = (Pic) phenotype;
        for (Lin line : pic.lines) {
            limb(g2, pic, line);
        }
        if (drawingPreferences.isShowBoundingBoxes()) {
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.BLUE);
            Rectangle rectangle = SwingGeom.toRectangle(pic.getMargin());
            g2.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
        g2.setTransform(saveTransform);
    }
    @Override
    public Object getImage(Phenotype phenotype, double scale) {
    	Rect margin = phenotype.getMargin();
    	
    	BufferedImage bufferedImage = new BufferedImage((int) (margin.getWidth() * scale + 1)
    			, 
    			(int) (margin.getHeight() * scale + 1),
    			BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g2 = bufferedImage.createGraphics();
    	
        if (drawingPreferences.isShowBoundingBoxes()) {
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.BLUE);
            Rectangle rectangle = SwingGeom.toRectangle(phenotype.getMargin());
            g2.drawRect(rectangle.x, rectangle.y, (int) (rectangle.width * scale), (int)(rectangle.height * scale));
        }
    	
    	g2.translate(- margin.left * scale, - margin.top * scale);
    	g2.scale(scale, scale);
    	if (drawingPreferences.isSpinBabyMorphs()) {
    		g2.rotate(-Math.PI * 4 * scale);
    	}
        for (Lin line : ((Pic) phenotype).lines) {
            limb(g2, phenotype, line);
        }

		return bufferedImage;
    }

}
