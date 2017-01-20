package net.richarddawkins.watchmaker.geom;

import net.richarddawkins.watchmaker.morph.Morph;
/**
 * 
 * @author Alan
 *
 */
public class BoxedMorph extends LocatedMorph {

	public int boxNo;
	
	public BoxedMorph(Morph morph, int boxNo) {
		this.morph = morph;
		this.boxNo = boxNo;
	}
	
	public int getBoxNo() {
		return boxNo;
	}
	public void setBoxNo(int boxNo) {
		this.boxNo = boxNo;
	}
	
}
