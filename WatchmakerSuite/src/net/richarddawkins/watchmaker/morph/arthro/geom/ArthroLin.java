package net.richarddawkins.watchmaker.morph.arthro.geom;

import net.richarddawkins.watchmaker.morph.arthro.geom.gui.ArthroLimbType;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Point;
import net.richarddawkins.watchmaker.morph.colour.geom.ColourLin;

public class ArthroLin extends ColourLin implements Cloneable {
    protected ArthroLimbType arthroLimbType;
    public ArthroLin(Point startPt, Point endPt, int color, ArthroLimbType arthroLimbType) {
        super(startPt, endPt, color);
        this.arthroLimbType = arthroLimbType;
    }
    
    @Override
    public ArthroLin clone() {
        ArthroLin clone = (ArthroLin) super.clone();
        clone.arthroLimbType = arthroLimbType;
        return clone;
      }

}
