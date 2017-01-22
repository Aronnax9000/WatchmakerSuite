package net.richarddawkins.watchmaker.morph;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.genome.mutation.Mutagen;

public abstract class SimpleMorphConfig implements MorphConfig {
    

    protected Object appData;
	protected int defaultBreedingCols;
	protected Mutagen mutagen;
    public Mutagen getMutagen() {
		return mutagen;
	}

	public void setMutagen(Mutagen mutagen) {
		this.mutagen = mutagen;
	}





	protected int defaultBreedingRows;
    protected int geneBoxCount = 0;
	protected JPanel pageStartPanel;
	protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	protected boolean recordingFossils;

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	@Override
	public abstract Morph createMorph(int type);
	

	public Object getAppData() {
        return appData;
    }


	public int getDefaultBreedingCols() {
		return defaultBreedingCols;
	}




	public int getDefaultBreedingRows() {
		return defaultBreedingRows;
	}


	public int getGeneBoxCount() {
		return geneBoxCount;
	}




	@Override
	public boolean isRecordingFossils() {
		return recordingFossils;
	}



	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}


	public void setAppData(Object appData) {
        this.appData = appData;
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
