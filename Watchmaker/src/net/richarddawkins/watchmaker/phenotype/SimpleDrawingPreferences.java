package net.richarddawkins.watchmaker.phenotype;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.util.logging.Logger;

public class SimpleDrawingPreferences implements DrawingPreferences, PropertyChangeListener {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.phenotype.SimpleDrawingPreferences");

	protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	protected int scale = 0;
	protected boolean showBoundingBoxes = false;
	protected VetoableChangeSupport vcs = new VetoableChangeSupport(this);
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	@Override
	public void addVetoableChangeListener(VetoableChangeListener listener) {
		vcs.addVetoableChangeListener(listener);
	}

	@Override
	public int getScale() {
		return scale;
	}

	@Override
	public boolean isShowBoundingBoxes() {
		return showBoundingBoxes;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("showBoundingBoxes")) {
			setShowBoundingBoxes((boolean) evt.getNewValue());
		}
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

    @Override
	public void removeVetoableChangeListener(VetoableChangeListener listener) {
		vcs.removeVetoableChangeListener(listener);
	}

    @Override
	public void setScale(int newValue) {
		int oldValue = this.scale;
		this.scale = newValue;
		logger.info("New DrawingPreferences scale " + this.scale);
		pcs.firePropertyChange(new PropertyChangeEvent(this, "scale", oldValue, newValue));
	}

	@Override
	public void setShowBoundingBoxes(boolean newValue) {
		boolean oldValue = this.showBoundingBoxes;
		this.showBoundingBoxes = newValue;
		logger.info("New DrawingPreferences showBoundingBoxes " + this.showBoundingBoxes);
		pcs.firePropertyChange(new PropertyChangeEvent(this, "showBoundingBoxes", oldValue, newValue));
	}
}
