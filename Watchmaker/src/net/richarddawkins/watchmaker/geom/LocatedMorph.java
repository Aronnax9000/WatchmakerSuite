package net.richarddawkins.watchmaker.geom;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.morph.Morph;

public class LocatedMorph {
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.geom.LocatedMorph");

	protected Point destination;
	
	protected Morph morph;
	protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	protected Point position;
	protected double progress = 0.0d;
	protected boolean scaleWithProgress;
	public void addProperyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	public void firePropertyChange (String propertyName, Object oldValue, Object newValue) {
		pcs.firePropertyChange(propertyName, oldValue, newValue);
	}
	public Point getDestination() {
		return destination;
	}
	public Morph getMorph() {
		return morph;
	}

	public Point getPosition() { return position; }
	public double getProgress() {
		return progress;
	}

	
	public boolean isScaleWithProgress() {
		return scaleWithProgress;
	}

	
	public void nudge() {
		if(Math.abs(1.0d - progress) < 0.1d) {
    		progress = 1.0d;
    	} else {
    		progress += (1.0d - progress) * 0.1;
    	}
	}

	public void removeProperyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	public void setDestination(Point destination) {
		this.destination = destination;
	}
	public void setMorph(Morph morph) {
		this.morph = morph;
	}

	public void setPosition(Point position) { this.position = position; }
	public void setProgress(double progress) {
		this.progress = progress;
	}
	public void setScaleWithProgress(boolean scaleWithProgress) {
		this.scaleWithProgress = scaleWithProgress;
	}


}
