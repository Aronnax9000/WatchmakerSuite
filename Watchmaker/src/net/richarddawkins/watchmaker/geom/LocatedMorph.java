package net.richarddawkins.watchmaker.geom;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.morph.Morph;

public class LocatedMorph {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.geom.LocatedMorph");

	protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	public void addProperyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	public void removeProperyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
	public void firePropertyChange (String propertyName, Object oldValue, Object newValue) {
		pcs.firePropertyChange(propertyName, oldValue, newValue);
	}
	protected Morph morph;
	protected Point position;
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

	public void setPosition(Point position) { this.position = position; }
	public Point getPosition() { return position; }

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
