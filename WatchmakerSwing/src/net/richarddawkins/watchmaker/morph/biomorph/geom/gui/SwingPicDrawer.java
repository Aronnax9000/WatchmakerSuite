package net.richarddawkins.watchmaker.morph.biomorph.geom.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import net.richarddawkins.watchmaker.morph.biomorph.geom.Lin;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Pic;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Point;

public abstract class SwingPicDrawer {

    protected boolean showBoundingBoxes = true;
    
    protected abstract void limb(Graphics2D g2, Pic pic, Lin limb);
    
    protected void picSpecifics(Graphics2D g2, Pic pic) {
        // Default no-op
    }
    
    public void drawPic(Graphics2D g2, Pic pic) {
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
