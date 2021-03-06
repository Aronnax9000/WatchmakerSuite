package net.richarddawkins.watchmaker.phenotype;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Logger;

public class SimpleDrawingPreferences implements DrawingPreferences, PropertyChangeListener {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.phenotype.SimpleDrawingPreferences");

	protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	protected boolean showBoundingBoxes = true;

	protected boolean spinBabyMorphs;
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}




	@Override
	public boolean isShowBoundingBoxes() {
		return showBoundingBoxes;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("showBoundingBoxes")) {
			setShowBoundingBoxes((Boolean) evt.getNewValue());
		}
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}



	@Override
	public void setShowBoundingBoxes(boolean newValue) {
		boolean oldValue = this.showBoundingBoxes;
		this.showBoundingBoxes = newValue;
		logger.info("New DrawingPreferences showBoundingBoxes " + this.showBoundingBoxes);
		pcs.firePropertyChange(new PropertyChangeEvent(this, "showBoundingBoxes", oldValue, newValue));
	}


	@Override
	public boolean isSpinBabyMorphs() {
		
		return spinBabyMorphs;
	}


	@Override
	public void setSpinBabyMorphs(boolean newValue) {
		boolean oldValue = this.spinBabyMorphs;
		this.spinBabyMorphs = newValue;
		logger.info("New DrawingPreferences spin " + this.spinBabyMorphs);
		pcs.firePropertyChange(new PropertyChangeEvent(this, "spinBabyMorphs", oldValue, newValue));
		
	}


    @Override
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName, listener);
        
    }
}
