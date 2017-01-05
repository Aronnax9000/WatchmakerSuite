package net.richarddawkins.watchmaker.morph.common;

import java.awt.Component;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.gui.BreedingWatchmakerPanel;
import net.richarddawkins.watchmaker.gui.MorphViewsTabbedPane;
import net.richarddawkins.watchmaker.gui.WatchmakerTabbedPane;
import net.richarddawkins.watchmaker.gui.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.gui.menu.MenuBuilder;
import net.richarddawkins.watchmaker.resourceloader.ClassicImageLoader;

public abstract class SimpleMorphConfig implements MorphConfig {

	protected void setIconFromFilename(String filename) {
		icon = new ImageIcon(ClassicImageLoader.getPicture(filename).getImage());
	}

	public int getDefaultBreedingRows() {
		return defaultBreedingRows;
	}

	public void setDefaultBreedingRows(int defaultBreedingRows) {
		this.defaultBreedingRows = defaultBreedingRows;
	}

	public int getDefaultBreedingCols() {
		return defaultBreedingCols;
	}

	public void setDefaultBreedingCols(int defaultBreedingCols) {
		this.defaultBreedingCols = defaultBreedingCols;
	}

	protected int defaultBreedingRows;
	protected int defaultBreedingCols;
	protected Component container;
	private WatchmakerTabbedPane frame;

	public WatchmakerTabbedPane getFrame() {
		return frame;
	}

	public void setFrame(WatchmakerTabbedPane frame) {
		this.frame = frame;
	}

	protected JPanel pageStartPanel;

	public Component getContainer() {
		return container;
	}

	public void setContainer(Component container) {
		this.container = container;
	}

	protected int geneBoxCount = 0;

	public int getGeneBoxCount() {
		return geneBoxCount;
	}

	protected Icon icon;

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
	 * @see net.richarddawkins.watchmaker.watchmaker.morphs.impl.MorphConfig#
	 * getMenuBuilder()
	 */
	protected MenuBuilder menuBuilder;

	@Override
	public MenuBuilder getMenuBuilder() {
		return menuBuilder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.richarddawkins.watchmaker.watchmaker.morphs.impl.MorphConfig#
	 * createMorph(int)
	 */
	@Override
	public abstract Morph createMorph(int type);

	protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

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
	 * removePropertyChangeListener(java.beans. PropertyChangeListener)
	 */
	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	protected boolean recordingFossils;

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
	 * setRecordingFossils(boolean)
	 */
	@Override
	public void setRecordingFossils(boolean newValue) {
		boolean oldValue = recordingFossils;
		recordingFossils = newValue;
		if (newValue != oldValue)
			pcs.firePropertyChange("recordingFossils", oldValue, newValue);
	}

	protected String name;

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

	protected String toolTip;

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

	protected boolean showBoundingBoxes;

	public boolean isShowBoundingBoxes() {
		return showBoundingBoxes;
	}

	public void setShowBoundingBoxes(boolean showBoundingBoxes) {
		this.showBoundingBoxes = showBoundingBoxes;
	}

	protected MorphViewsTabbedPane morphViewsTabbedPane;
	public MorphViewsTabbedPane getMorphViewsTabbedPane() {
		return morphViewsTabbedPane;
	}
	public MorphViewsTabbedPane newMorphViewsTabbedPane() {
		return new MorphViewsTabbedPane(this);
	}
	public void setMorphViewsTabbedPane(MorphViewsTabbedPane morphViewsTabbedPane) {
		this.morphViewsTabbedPane = morphViewsTabbedPane;
	}
	@Override
    public void addDefaultMorphView(MorphViewsTabbedPane morphViews) {
	    morphViews.addMorphView(new BreedingWatchmakerPanel(this));
	}
	@Override
	public GeneBoxStrip newGeneBoxStrip() { return null;}

}
