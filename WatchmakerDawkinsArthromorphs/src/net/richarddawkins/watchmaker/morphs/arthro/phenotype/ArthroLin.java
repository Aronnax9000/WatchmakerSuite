package net.richarddawkins.watchmaker.morphs.arthro.phenotype;

import java.util.logging.Logger;

import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morphs.colour.geom.ColourLin;

public class ArthroLin extends ColourLin implements Cloneable {
    private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morphs.arthro.phenotype.ArthroLin");

    protected ArthroLimbType arthroLimbType;
    protected int thickness;
    
    public ArthroLin(Point startPt, Point endPt, int color, int thickness) {
    	this(startPt, endPt, color, thickness, ArthroLimbType.LineSegment);
    }
    
    public ArthroLin(Point startPt, Point endPt, int color, int thickness, 
    		ArthroLimbType arthroLimbType) {
        super(startPt, endPt, thickness, color);
        this.thickness = thickness;
        this.arthroLimbType = arthroLimbType;
    }
    
    @Override
    public ArthroLin clone() {
        ArthroLin clone = (ArthroLin) super.clone();
        clone.arthroLimbType = arthroLimbType;
        clone.thickness = thickness;
        return clone;
      }
}
