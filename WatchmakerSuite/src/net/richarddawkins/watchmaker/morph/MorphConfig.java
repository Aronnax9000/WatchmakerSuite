package net.richarddawkins.watchmaker.morph;

import java.beans.PropertyChangeListener;

public interface MorphConfig {
    

	int getGeneBoxCount();
	
	Object getAppData();
	void setAppData(Object object);

	Morph createMorph(int type);

	void addPropertyChangeListener(PropertyChangeListener listener);

	void removePropertyChangeListener(PropertyChangeListener listener);

	boolean isRecordingFossils();

	void setRecordingFossils(boolean newValue);

    void setMutagen(Mutagen mutagen);
	Mutagen getMutagen();

	boolean[] getMut();

	int getDefaultBreedingRows();

	int getDefaultBreedingCols();

	void setDefaultBreedingRows(int defaultBreedingRows);

	void setDefaultBreedingCols(int defaultBreedingCols);


}