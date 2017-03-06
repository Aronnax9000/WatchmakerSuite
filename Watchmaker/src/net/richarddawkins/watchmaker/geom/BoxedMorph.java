package net.richarddawkins.watchmaker.geom;

import java.util.logging.Logger;

import net.richarddawkins.watchmaker.morph.Morph;

/**
 * 
 * @author Alan
 *
 */
public class BoxedMorph extends LocatedMorph {
    private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.geom.BoxedMorph");
    protected Rect box;
    protected BoxManager boxes;
	protected Rect destinationBox = null;
	public BoxedMorph(BoxManager boxes, Morph morph, Rect box) {
		logger.info("Adding morph to box  " + box);
		this.boxes = boxes;
		this.morph = morph;
		this.box = box;
	}

	public boolean genomicallyEquals(BoxedMorph thatBoxedMorph) {
        Morph thatMorph = thatBoxedMorph.getMorph();
        
        return morph.genomicallyEquals(thatMorph);
    }

	public Rect getBox() {
		return box;
	}

	public BoxManager getBoxes() {
		return boxes;
	}

	public Rect getDestinationBox() {
		return destinationBox;
	}

	public Point getPosition(Dim dimension) {
		if (destinationBox == null) {
			return boxes.getMidPoint(dimension, box);
		} else {
			Point origin = boxes.getMidPoint(dimension, box);
			Point destination = boxes.getMidPoint(dimension, destinationBox);
			Point position = new Point();
			position.h = origin.h + (int) ((double) (destination.h - origin.h) * progress);
			position.v = origin.v + (int) ((double) (destination.v - origin.v) * progress);
			return position;
		}
	}

	@Override
    public void kill() {
	    box = null;
	    destinationBox = null;
	    boxes = null;
        super.kill();
        
    }

	public void setBox(Rect newValue) {
	    Rect oldValue = this.box;
		this.box = newValue;
		firePropertyChange("Box", oldValue, newValue);
	}

	public void setBoxes(BoxManager boxes) {
		this.boxes = boxes;
	}

    public void setDestinationBox(Rect newValue) {
		logger.info("Destination box number " + newValue);
		Rect oldValue = this.destinationBox;
		this.destinationBox = newValue;
		firePropertyChange("destinationBox", oldValue, newValue);
	}
    @Override
    public String toString() {
        return "BoxedMorph box:" + box + " morph:" + morph;
    }

}
