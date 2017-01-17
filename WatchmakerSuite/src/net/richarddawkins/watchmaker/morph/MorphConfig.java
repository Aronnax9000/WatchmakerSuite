package net.richarddawkins.watchmaker.morph;

import java.beans.PropertyChangeListener;

import javax.swing.Icon;

public interface MorphConfig {
    

	int getGeneBoxCount();
	Icon getIcon();
	void setIcon(Icon icon);
	
	Object getAppData();
	void setAppData(Object object);




	Morph createMorph(int type);

	void addPropertyChangeListener(PropertyChangeListener listener);

	void removePropertyChangeListener(PropertyChangeListener listener);

	boolean isRecordingFossils();

	void setRecordingFossils(boolean newValue);

	String getName();

	String getToolTip();
	void setToolTip(String toolTip);
    void setMutagen(Mutagen mutagen);
	Mutagen getMutagen();

	boolean[] getMut();

	int getDefaultBreedingRows();

	int getDefaultBreedingCols();

	void setDefaultBreedingRows(int defaultBreedingRows);

	void setDefaultBreedingCols(int defaultBreedingCols);

	void setName(String uniquify);

	void setIconFromFilename(String filename);


}