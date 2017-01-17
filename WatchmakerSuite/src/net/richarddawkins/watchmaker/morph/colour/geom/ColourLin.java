package net.richarddawkins.watchmaker.morph.colour.geom;

import net.richarddawkins.watchmaker.morph.biomorph.geom.Lin;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Point;
import net.richarddawkins.watchmaker.morph.biomorph.geom.QuickDrawColor;
import net.richarddawkins.watchmaker.morph.colour.genome.type.LimbFillType;
import net.richarddawkins.watchmaker.morph.colour.genome.type.LimbShapeType;

public class ColourLin extends Lin implements Cloneable {
    public Integer color = QuickDrawColor.BLACK;
    public LimbShapeType limbShape = LimbShapeType.Stick;
    public LimbFillType limbFill = LimbFillType.Open;
    public ColourLin(Point startPt, Point endPt, int color) {
        super(startPt, endPt);
        this.color = color;
    }

    @Override
    public ColourLin clone() {
        ColourLin clone = (ColourLin) super.clone();
        clone.color = color;
        return clone;
      }

}
