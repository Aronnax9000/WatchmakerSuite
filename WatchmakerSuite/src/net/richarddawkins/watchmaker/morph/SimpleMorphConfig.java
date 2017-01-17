package net.richarddawkins.watchmaker.morph;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.resourceloader.ClassicImageLoader;

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
	protected Icon icon;
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	protected String name;
	protected JPanel pageStartPanel;
	protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	protected boolean recordingFossils;
	protected String toolTip;

	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}

	public SimpleMorphConfig(MorphType morphType) {
		setIcon(morphType.getIcon());
		setName(morphType.getName());
		setToolTip(morphType.getToolTip());
		
	}


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
	 * @see
	 * net.richarddawkins.watchmaker.watchmaker.morphs.impl.MorphConfig#getIcon(
	 * )
	 */
	@Override
	public Icon getIcon() {
		return icon;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.richarddawkins.watchmaker.watchmaker.morphs.impl.MorphConfig#getName(
	 * )
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.richarddawkins.watchmaker.watchmaker.morphs.impl.MorphConfig#
	 * getToolTip()
	 */
	@Override
	public String getToolTip() {
		return toolTip;
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


	@Override
	public void setIconFromFilename(String filename) {
		icon = new ImageIcon(ClassicImageLoader.getPicture(filename).getImage());
	}

	public void setName(String name) {
		this.name = name;
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
