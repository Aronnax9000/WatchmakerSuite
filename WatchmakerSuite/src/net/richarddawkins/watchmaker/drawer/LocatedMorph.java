package net.richarddawkins.watchmaker.drawer;

import java.awt.Point;

import net.richarddawkins.watchmaker.morph.Morph;

public class LocatedMorph {
	protected Morph morph;
	protected Point origin;
	protected Point destination;
	protected double progress = 0.0d;
	protected boolean scaleWithProgress;
	public boolean isScaleWithProgress() {
		return scaleWithProgress;
	}

	public void setScaleWithProgress(boolean scaleWithProgress) {
		this.scaleWithProgress = scaleWithProgress;
	}
	public Point getDestination() {
		return destination;
	}

	
	public void setDestination(Point destination) {
		this.destination = destination;
	}

	
	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	public void setPosition(Point position) { origin = position; }
	public Point getPosition() { return origin; }

	public Morph getMorph() {
		return morph;
	}
	public void setMorph(Morph morph) {
		this.morph = morph;
	}
	public void nudge() {
		if(Math.abs(1.0d - progress) < 0.1d) {
    		progress = 1.0d;
    	} else {
    		progress += (1.0d - progress) * 0.1;
    	}
	}


}
