package net.richarddawkins.watchmaker.morphs.colour.geom.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import net.richarddawkins.watchmaker.morphs.bio.geom.Lin;
import net.richarddawkins.watchmaker.morphs.bio.geom.Pic;
import net.richarddawkins.watchmaker.morphs.colour.genome.type.LimbFillType;
import net.richarddawkins.watchmaker.morphs.colour.geom.ColourLin;
import net.richarddawkins.watchmaker.morphs.colour.geom.ColourPic;
import net.richarddawkins.watchmaker.morphs.swing.SwingPicDrawer;

public class SwingColourPicDrawer extends SwingPicDrawer {

    protected void limbRect(Graphics2D g2, ColourLin line, Rectangle square) {
        g2.drawRect(square.x, square.y, square.width, square.height);
        if (line.limbFill == LimbFillType.Filled)
            g2.fillRect(square.x, square.y, square.width, square.height);
    }

    protected void limbOval(Graphics2D g2, ColourLin line, Rectangle square) {
        g2.drawOval(square.x, square.y, square.width, square.height);
        if (line.limbFill == LimbFillType.Filled)
            g2.fillOval(square.x, square.y, square.width, square.height);
    }
    
    protected void picSpecifics(Graphics2D g2, Pic pic) {
        g2.setStroke(new BasicStroke(((ColourPic)pic).getThickness()));
    }
    
    @Override
    protected void limb(Graphics2D g2, Pic pic, Lin line) {
        ColourLin colourLin = (ColourLin) line;
        g2.setColor(new Color(colourLin.color));
        Rectangle square = new Rectangle(Math.min(line.startPt.h, line.endPt.h), Math.min(line.startPt.v, line.endPt.v),
                Math.abs(line.endPt.h - line.startPt.h), Math.abs(line.endPt.v - line.startPt.v));

        switch (colourLin.limbShape) {
        case Oval:
            limbOval(g2, colourLin, square);
        case Rectangle:
            limbRect(g2, colourLin, square);
        default:
        }
        g2.drawLine(line.startPt.h, line.startPt.v, line.endPt.h, line.endPt.v);
    }
}
