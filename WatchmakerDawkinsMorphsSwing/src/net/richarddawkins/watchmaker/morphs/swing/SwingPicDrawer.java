package net.richarddawkins.watchmaker.morphs.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morphs.mono.geom.Lin;
import net.richarddawkins.watchmaker.morphs.mono.geom.Pic;
import net.richarddawkins.watchmaker.phenotype.DrawingPreferences;
import net.richarddawkins.watchmaker.phenotype.Phenotype;
import net.richarddawkins.watchmaker.phenotype.PhenotypeDrawer;
import net.richarddawkins.watchmaker.swing.SwingGeom;



public abstract class SwingPicDrawer implements PhenotypeDrawer {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morphs.swing.SwingPicDrawer");

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
    	this.picSpecifics(g2, phenotype);

    	if (drawingPreferences.isSpinBabyMorphs()) {
    		g2.rotate(-Math.PI * 4 * scale);
    	}
        for (Lin line : ((Pic) phenotype).lines) {
            limb(g2, phenotype, line);
        }
        logger.fine("SwingPicDrawer.getImage() complete");
		return bufferedImage;
    }

}
