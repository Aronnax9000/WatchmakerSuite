package net.richarddawkins.watchmaker.morphs.arthro.phenotype;

import java.util.logging.Logger;

import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morphs.colour.geom.ColourLin;

public class ArthroLin extends ColourLin implements Cloneable {
	@SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morphs.arthro.phenotype.ArthroLin");

	public ArthroLimbType arthroLimbType;
    public int thickness;
    
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
    @Override
    public void expandMargin(Rect margin) {
        margin.expandPoint(startPt, thickness);
    	if(arthroLimbType == ArthroLimbType.LineSegment) {
	        margin.expandPoint(endPt, thickness);
    	} else {
    		margin.expandPoint(startPt.add(endPt), thickness);
    	}
    }
    
    @Override
    public String toString() {
    	return arthroLimbType.name() + " " + super.toString();
    }
}
