package net.richarddawkins.watchmaker.morphs.colour.geom;

import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morphs.bio.geom.Lin;

public class ColourLin extends Lin implements Cloneable {
    public int color;
    public int thickness;
    
    public ColourLin(Point startPt, Point endPt, int thickness, int color) {
        super(startPt, endPt);
        this.thickness = thickness;
        this.color = color;
    }

	@Override
    public ColourLin clone() {
        ColourLin clone = (ColourLin) super.clone();
        clone.color = color;
        clone.thickness = thickness;
        return clone;
    }
	@Override
	public String toString() {
		return super.toString() + " Color:" + color + " Thickness:" + thickness;
	}
	

}
