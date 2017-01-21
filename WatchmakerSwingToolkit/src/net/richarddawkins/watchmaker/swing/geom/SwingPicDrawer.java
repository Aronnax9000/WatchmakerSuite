package net.richarddawkins.watchmaker.swing.geom;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import net.richarddawkins.watchmaker.geom.Lin;
import net.richarddawkins.watchmaker.geom.Pic;
import net.richarddawkins.watchmaker.geom.PicDrawer;
import net.richarddawkins.watchmaker.geom.Point;



public abstract class SwingPicDrawer implements PicDrawer {

    protected boolean showBoundingBoxes = true;
    
    protected abstract void limb(Graphics2D g2, Pic pic, Lin limb);
    
    protected void picSpecifics(Graphics2D g2, Pic pic) {
        // Default no-op
    }
    
    public void drawPic(Object graphicsContext, Pic pic) {
    	Graphics2D g2 = (Graphics2D) graphicsContext;
        AffineTransform saveTransform = g2.getTransform();
        
        Point midPoint = pic.margin.getMidPoint();

        g2.translate(-midPoint.h, -midPoint.v);
        for (Lin line : pic.lines) {
            limb(g2, pic, line);
        }
        if (showBoundingBoxes) {
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.BLUE);
            Rectangle rectangle = pic.margin.toRectangle();
            g2.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
        g2.setTransform(saveTransform);
    }

    

}
