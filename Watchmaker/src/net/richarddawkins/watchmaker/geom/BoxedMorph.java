package net.richarddawkins.watchmaker.geom;

import net.richarddawkins.watchmaker.morph.Morph;

/**
 * 
 * @author Alan
 *
 */
public class BoxedMorph extends LocatedMorph {

	protected int boxNo;
	protected int destinationBoxNo = -1;
	protected Boxes boxes;

	public int getDestinationBoxNo() {
		return destinationBoxNo;
	}

	public BoxedMorph(Boxes boxes, Morph morph, int boxNo) {
		this.boxes = boxes;
		this.morph = morph;
		this.boxNo = boxNo;
	}

	public Point getPosition(Dim dimension) {
		if (destinationBoxNo == -1) {
			return boxes.getMidPoint(dimension, boxNo);
		} else {
			Point origin = boxes.getMidPoint(dimension, boxNo);
			Point destination = boxes.getMidPoint(dimension, destinationBoxNo);
			Point position = new Point();
			position.h = (int) ((double) (destination.h - origin.h) * progress);
			position.v = (int) ((double) (destination.v - origin.v) * progress);
			return position;
		}
	}

	public int getBoxNo() {
		return boxNo;
	}

	public void setBoxNo(int newValue) {
		int oldValue = this.boxNo;
		this.boxNo = newValue;
		firePropertyChange("boxNo", oldValue, newValue);
	}

	public void setDestinationBoxNo(int newValue) {
		int oldValue = this.destinationBoxNo;
		this.destinationBoxNo = newValue;
		firePropertyChange("destinationBoxNo", oldValue, newValue);
	}

}
