package net.richarddawkins.watchmaker.phenotype;

import java.beans.PropertyChangeListener;
import java.beans.VetoableChangeListener;

public interface DrawingPreferences {
	public int getScale();
	public void setScale(int scale);
	public boolean isShowBoundingBoxes();
	public void setShowBoundingBoxes(boolean selected);
	public void addPropertyChangeListener(PropertyChangeListener listener);

	public void removePropertyChangeListener(PropertyChangeListener listener);

	public void addVetoableChangeListener(VetoableChangeListener listener);

	public void removeVetoableChangeListener(VetoableChangeListener listener);
}
