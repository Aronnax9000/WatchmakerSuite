package net.richarddawkins.watchmaker.morph;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JPanel;

public abstract class SimpleMorphConfig implements MorphConfig {
    

    protected Object appData;
	public Object getAppData() {
        return appData;
    }

    public void setAppData(Object appData) {
        this.appData = appData;
    }
    protected int defaultBreedingCols;
	protected int defaultBreedingRows;
	protected int geneBoxCount = 0;
	protected JPanel pageStartPanel;
	protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	protected boolean recordingFossils;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.richarddawkins.watchmaker.watchmaker.morphs.impl.MorphConfig#
	 * addPropertyChangeListener(java.beans. PropertyChangeListener)
	 */
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see net.richarddawkins.watchmaker.watchmaker.morphs.impl.MorphConfig#
	 * createMorph(int)
	 */
	@Override
	public abstract Morph createMorph(int type);




	public int getDefaultBreedingCols() {
		return defaultBreedingCols;
	}


	public int getDefaultBreedingRows() {
		return defaultBreedingRows;
	}



	public int getGeneBoxCount() {
		return geneBoxCount;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see net.richarddawkins.watchmaker.watchmaker.morphs.impl.MorphConfig#
	 * isRecordingFossils()
	 */
	@Override
	public boolean isRecordingFossils() {
		return recordingFossils;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see net.richarddawkins.watchmaker.watchmaker.morphs.impl.MorphConfig#
	 * removePropertyChangeListener(java.beans. PropertyChangeListener)
	 */
	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}


	public void setDefaultBreedingCols(int defaultBreedingCols) {
		this.defaultBreedingCols = defaultBreedingCols;
	}

	public void setDefaultBreedingRows(int defaultBreedingRows) {
		this.defaultBreedingRows = defaultBreedingRows;
	}





	/*
	 * (non-Javadoc)
	 * 
	 * @see net.richarddawkins.watchmaker.watchmaker.morphs.impl.MorphConfig#
	 * setRecordingFossils(boolean)
	 */
	@Override
	public void setRecordingFossils(boolean newValue) {
		boolean oldValue = recordingFossils;
		recordingFossils = newValue;
		if (newValue != oldValue)
			pcs.firePropertyChange("recordingFossils", oldValue, newValue);
	}
	

}
