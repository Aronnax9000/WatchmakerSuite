package net.richarddawkins.watchmaker.phenotype;

import java.beans.PropertyChangeListener;

public interface DrawingPreferences {
	public boolean isShowBoundingBoxes();
	public void setShowBoundingBoxes(boolean selected);
	public void addPropertyChangeListener(PropertyChangeListener listener);
	public void removePropertyChangeListener(PropertyChangeListener listener);
	public boolean isSpinBabyMorphs();
	public void setSpinBabyMorphs(boolean spinOrNot);
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);
}
