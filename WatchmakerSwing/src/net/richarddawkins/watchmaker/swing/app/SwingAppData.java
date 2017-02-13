package net.richarddawkins.watchmaker.swing.app;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JTabbedPane;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.app.MultiMorphTypeTabbedPanel;
import net.richarddawkins.watchmaker.geom.BoxesDrawer;
import net.richarddawkins.watchmaker.menu.MenuBuilder;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.MorphViewsTabbedPanel;
import net.richarddawkins.watchmaker.phenotype.PhenotypeDrawer;
import net.richarddawkins.watchmaker.swing.breed.SwingBreedingMorphView;
import net.richarddawkins.watchmaker.swing.drawer.SwingBoxesDrawer;
import net.richarddawkins.watchmaker.swing.engineer.SwingEngineeringMorphView;
import net.richarddawkins.watchmaker.swing.triangle.SwingTriangleMorphView;

public abstract class SwingAppData implements AppData {
	protected BoxesDrawer boxesDrawer = new SwingBoxesDrawer();
	protected boolean saltOnEmptyBreedingBoxClick = false;
	
	protected long tickDelay = 5;
	
	@Override
	public long getTickDelay() {
		return tickDelay;
	}
	@Override
	public void setTickDelay(long tickDelay) {
		this.tickDelay = tickDelay;
	}
	public boolean isSaltOnEmptyBreedingBoxClick() {
		return saltOnEmptyBreedingBoxClick;
	}
	public void setSaltOnEmptyBreedingBoxClick(boolean saltOnEmptyBreedingBoxClick) {
		this.saltOnEmptyBreedingBoxClick = saltOnEmptyBreedingBoxClick;
	}
	protected boolean highlighting = false;
	public boolean isHighlighting() {
		return highlighting;
	}
	public void setHighlighting(boolean newValue) {
		boolean oldValue = highlighting;
		highlighting = newValue;
		pcs.firePropertyChange("highlighting", oldValue, newValue);
	}
	protected boolean geneBoxToSide;

	public boolean isGeneBoxToSide() {
		return geneBoxToSide;
	}
	public void setGeneBoxToSide(boolean geneBoxToSide) {
		this.geneBoxToSide = geneBoxToSide;
	}

	protected MorphConfig config;

	protected int defaultBreedingCols = 5;
	protected int defaultBreedingRows = 3;
	protected boolean breedRightAway = true;

	public boolean isBreedRightAway() {
		return breedRightAway;
	}
	public void setBreedRightAway(boolean breedRightAway) {
		this.breedRightAway = breedRightAway;
	}


	protected MultiMorphTypeTabbedPanel frame;
	protected String icon;
	protected MenuBuilder menuBuilder;
	protected MorphViewsTabbedPanel morphViewsTabbedPane;
	protected String name;
	protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	protected PhenotypeDrawer phenotypeDrawer;
	protected String toolTip;
	public SwingAppData() {
	}
	@Override
	public void addBreedingMorphView(Morph morph) {
		morphViewsTabbedPane.addMorphView(new SwingBreedingMorphView(this, morph));
	}
	@Override
	public void addDefaultMorphView() {
		addBreedingMorphView(null);
	}
	@Override
	public void addEngineeringMorphView(Morph morph) {
		morphViewsTabbedPane.addMorphView(new SwingEngineeringMorphView(this, morph));
	}

	@Override
	public void addTriangleMorphView() {
		morphViewsTabbedPane.addMorphView(new SwingTriangleMorphView(this));
		
	}
	
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	@Override
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(propertyName, listener);
	}

	@Override
	public BoxesDrawer getBoxesDrawer() {
		return boxesDrawer;
	}

	@Override
	public int getDefaultBreedingCols() {
		return defaultBreedingCols;
	}

	@Override
	public int getDefaultBreedingRows() {
		return defaultBreedingRows;
	}

	@Override
	public MultiMorphTypeTabbedPanel getFrame() {
		return frame;
	}

	@Override
	public String getIcon() {
		return icon;
	}

	@Override
	public MenuBuilder getMenuBuilder() {
		return menuBuilder;
	}

	@Override
	public MorphConfig getMorphConfig() {
		return config;
	}

	@Override
	public Morph getMorphOfTheHour() {
		MorphViewsTabbedPanel pane = this.getMorphViewsTabbedPane();
		MorphView morphView = (MorphView) ((JTabbedPane) pane).getSelectedComponent();
		if(morphView != null) {
			return morphView.getMorphOfTheHour();
		} else {
			return null;
		}
	}

	@Override
	public MorphViewsTabbedPanel getMorphViewsTabbedPane() {
		return this.morphViewsTabbedPane;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public PhenotypeDrawer getPhenotypeDrawer() {
		return phenotypeDrawer;
	}

	@Override
	public String getToolTip() {
		return toolTip;
	}


	@Override
	public void setDefaultBreedingCols(int defaultBreedingCols) {
		this.defaultBreedingCols = defaultBreedingCols;
	}

	@Override
	public void setDefaultBreedingRows(int defaultBreedingRows) {
		this.defaultBreedingRows = defaultBreedingRows;
	}

	@Override
	public void setFrame(MultiMorphTypeTabbedPanel frame) {
		this.frame = frame;

	}

	@Override
	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public void setMenuBuilder(MenuBuilder menuBuilder) {
		this.menuBuilder = menuBuilder;
	}

	@Override
	public void setMorphConfig(MorphConfig config) {
		this.config = config;

	}

	@Override
	public void setMorphViewsTabbedPane(MorphViewsTabbedPanel morphViewsTabbedPane) {
		this.morphViewsTabbedPane = morphViewsTabbedPane;

	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setPhenotypeDrawer(PhenotypeDrawer newValue) {
		this.phenotypeDrawer = newValue;
	}


	@Override
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}
}
