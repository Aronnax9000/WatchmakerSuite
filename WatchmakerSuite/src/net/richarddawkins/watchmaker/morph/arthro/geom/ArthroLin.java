package net.richarddawkins.watchmaker.morph.arthro.geom;

import net.richarddawkins.watchmaker.morph.biomorph.geom.Point;
import net.richarddawkins.watchmaker.morph.biomorph.geom.QuickDrawColor;
import net.richarddawkins.watchmaker.morph.colour.genome.type.LimbFillType;
import net.richarddawkins.watchmaker.morph.colour.genome.type.LimbShapeType;
import net.richarddawkins.watchmaker.morph.colour.geom.ColourLin;

public class ArthroLin extends ColourLin implements Cloneable {
    public Integer limbFillColor = QuickDrawColor.GREEN;
    public ArthroLin(Point startPt, Point endPt, int thickness, int color, 
            LimbShapeType limbShape, LimbFillType limbFill, int limbFillColor) {
        super(startPt, endPt, thickness, color, limbShape, limbFill);
        this.limbFillColor = limbFillColor;
    }
    
    @Override
    public ArthroLin clone() {
        ArthroLin clone = (ArthroLin) super.clone();
        clone.limbFillColor = limbFillColor;
        return clone;
      }

}
