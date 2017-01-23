package net.richarddawkins.watchmaker.phenotype;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

public class SimpleDrawingPreferences implements DrawingPreferences, PropertyChangeListener {
	
	protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	protected VetoableChangeSupport vcs = new VetoableChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
	public void addVetoableChangeListener(VetoableChangeListener listener) {
		vcs.addVetoableChangeListener(listener);
	}
	public void removeVetoableChangeListener(VetoableChangeListener listener) {
		vcs.removeVetoableChangeListener(listener);
	}
	
	protected boolean showBoundingBoxes = false;

	public boolean isShowBoundingBoxes() {
		return showBoundingBoxes;
	}

	public void setShowBoundingBoxes(boolean newValue) {
		boolean oldValue = this.showBoundingBoxes;
		this.showBoundingBoxes = newValue;
		pcs.firePropertyChange("showBoundingBoxes", oldValue, newValue);
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("showBoundingBoxes")) {
			setShowBoundingBoxes((boolean) evt.getNewValue());
		}
	}
}
