package net.richarddawkins.watchmaker.morph;

import java.awt.Component;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.genome.gui.GeneBoxStrip;
import net.richarddawkins.watchmaker.gui.MorphView;
import net.richarddawkins.watchmaker.gui.MorphViewsTabbedPane;
import net.richarddawkins.watchmaker.gui.WatchmakerTabbedPane;
import net.richarddawkins.watchmaker.gui.breed.BreedingWatchmakerPanel;
import net.richarddawkins.watchmaker.gui.engineer.EngineeringWatchmakerPanel;
import net.richarddawkins.watchmaker.gui.menu.MenuBuilder;
import net.richarddawkins.watchmaker.resourceloader.ClassicImageLoader;

public abstract class SimpleMorphConfig implements MorphConfig {
	protected Component container;
	protected int defaultBreedingCols;
	protected int defaultBreedingRows;
	private WatchmakerTabbedPane frame;
	protected int geneBoxCount = 0;
	protected Icon icon;
	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	protected MenuBuilder menuBuilder;
	protected MorphViewsTabbedPane morphViewsTabbedPane = new MorphViewsTabbedPane(this);
	protected String name;
	protected JPanel pageStartPanel;
	protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	protected boolean recordingFossils;
	protected boolean showBoundingBoxes;
	protected String toolTip;

	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}

	public SimpleMorphConfig(MorphType morphType) {
		setIcon(morphType.getIcon());
		setName(morphType.getName());
		setToolTip(morphType.getToolTip());
		
	}

	@Override
	public void addBreedingMorphView(Morph morph) {
	    morphViewsTabbedPane.addMorphView(new BreedingWatchmakerPanel(this, morph));
	}

	@Override
	public void addEngineeringMorphView(Morph morph) {
	    morphViewsTabbedPane.addMorphView(new EngineeringWatchmakerPanel(this, morph));
	}

	@Override
    public void addDefaultMorphView() {
		addBreedingMorphView(null);
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

	public Component getContainer() {
		return container;
	}


	public int getDefaultBreedingCols() {
		return defaultBreedingCols;
	}


	public int getDefaultBreedingRows() {
		return defaultBreedingRows;
	}


	public WatchmakerTabbedPane getFrame() {
		return frame;
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

	@Override
	public MenuBuilder getMenuBuilder() {
		return menuBuilder;
	}

	@Override
	public MorphViewsTabbedPane getMorphViewsTabbedPane() {
		return morphViewsTabbedPane;
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

	public boolean isShowBoundingBoxes() {
		return showBoundingBoxes;
	}

	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) {
		return null;
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

	public void setContainer(Component container) {
		this.container = container;
	}

	public void setDefaultBreedingCols(int defaultBreedingCols) {
		this.defaultBreedingCols = defaultBreedingCols;
	}

	public void setDefaultBreedingRows(int defaultBreedingRows) {
		this.defaultBreedingRows = defaultBreedingRows;
	}

	public void setFrame(WatchmakerTabbedPane frame) {
		this.frame = frame;
	}

	@Override
	public void setIconFromFilename(String filename) {
		icon = new ImageIcon(ClassicImageLoader.getPicture(filename).getImage());
	}
	@Override
	public void setMorphViewsTabbedPane(MorphViewsTabbedPane morphViewsTabbedPane) {
		this.morphViewsTabbedPane = morphViewsTabbedPane;
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
	
	public void setShowBoundingBoxes(boolean showBoundingBoxes) {
		this.showBoundingBoxes = showBoundingBoxes;
	}
	@Override
	public Morph getMorphOfTheHour() {
		
		MorphViewsTabbedPane pane = this.getMorphViewsTabbedPane();
		MorphView morphView = (MorphView) pane.getSelectedComponent();
		
		return morphView.getMorphOfTheHour();
	}
}
