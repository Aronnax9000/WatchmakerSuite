package net.richarddawkins.watchmaker.phenotype;

import java.beans.PropertyChangeListener;

public interface DrawingPreferences {
	public int getScale();
	public void setScale(int scale);
	public boolean isShowBoundingBoxes();
	public void setShowBoundingBoxes(boolean selected);
	public void addPropertyChangeListener(PropertyChangeListener listener);
	public void removePropertyChangeListener(PropertyChangeListener listener);
	public boolean isSpinBabyMorphs();
	public void setSpinBabyMorphs(boolean spinOrNot);
}
