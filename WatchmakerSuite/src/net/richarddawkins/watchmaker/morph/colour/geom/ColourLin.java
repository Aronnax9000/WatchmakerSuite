package net.richarddawkins.watchmaker.morph.colour.geom;

import net.richarddawkins.watchmaker.morph.arthro.geom.ArthroLin;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Lin;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Point;
import net.richarddawkins.watchmaker.morph.biomorph.geom.QuickDrawColor;
import net.richarddawkins.watchmaker.morph.colour.genome.type.LimbFillType;
import net.richarddawkins.watchmaker.morph.colour.genome.type.LimbShapeType;

public class ColourLin extends Lin implements Cloneable {
    public Integer color = QuickDrawColor.BLACK;
    public LimbShapeType limbShape = LimbShapeType.Stick;
    public LimbFillType limbFill = LimbFillType.Open;
    public ColourLin(Point startPt, Point endPt, int thickness, int color) {
        super(startPt, endPt, thickness);
        this.color = color;
    }
    public ColourLin(Point startPt, Point endPt, int thickness, int color, 
            LimbShapeType limbShape, LimbFillType limbFill) {
        this(startPt, endPt, thickness, color);
        this.limbShape = limbShape;
        this.limbFill = limbFill;

    }
    @Override
    public ColourLin clone() {
        ColourLin clone = (ColourLin) super.clone();
        clone.limbShape = limbShape;
        clone.limbFill = limbFill;
        return clone;
      }

}
