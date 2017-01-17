package net.richarddawkins.watchmaker.morphs.arthro.geom;

import net.richarddawkins.watchmaker.morphs.bio.geom.Point;
import net.richarddawkins.watchmaker.morphs.colour.geom.ColourLin;

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
