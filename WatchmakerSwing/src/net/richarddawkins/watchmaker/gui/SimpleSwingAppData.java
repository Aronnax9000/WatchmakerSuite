package net.richarddawkins.watchmaker.gui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.richarddawkins.watchmaker.geom.swing.SwingPicDrawer;
import net.richarddawkins.watchmaker.gui.breed.BreedingWatchmakerPanel;
import net.richarddawkins.watchmaker.gui.engineer.EngineeringWatchmakerPanel;
import net.richarddawkins.watchmaker.gui.menu.MenuBuilder;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.resourceloader.ClassicImageLoader;

abstract public class SimpleSwingAppData implements SwingAppData {
	protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	protected MorphConfig config;
	protected WatchmakerTabbedPane frame;
	protected MenuBuilder menuBuilder;
	protected MorphViewsTabbedPane morphViewsTabbedPane;
	protected Icon icon;
    public Icon getIcon() {
		return icon;
	}
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	public String getToolTip() {
		return toolTip;
	}
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}
	protected String name;
    protected String toolTip;

	protected boolean showBoundingBoxes = true;
	protected SwingPicDrawer swingPicDrawer;
	@Override
	public void setIconFromFilename(String filename) {
		icon = new ImageIcon(ClassicImageLoader.getPicture(filename).getImage());
	}
	
	@Override
	public void addBreedingMorphView(Morph morph) {
	    morphViewsTabbedPane.addMorphView(new BreedingWatchmakerPanel(this, morph));
	}
	@Override
    public void addDefaultMorphView() {
		addBreedingMorphView(null);
	}

	@Override
	public void addEngineeringMorphView(Morph morph) {
	    morphViewsTabbedPane.addMorphView(new EngineeringWatchmakerPanel(this, morph));
	}
	


	@Override
	public WatchmakerTabbedPane getFrame() {
		return frame;
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
        
        MorphViewsTabbedPane pane = this.getMorphViewsTabbedPane();
        MorphView morphView = (MorphView) pane.getSelectedComponent();
        
        return morphView.getMorphOfTheHour();
    }
	@Override
	public MorphViewsTabbedPane getMorphViewsTabbedPane() {
		return this.morphViewsTabbedPane;
	}
	@Override
	public String getName() {
		return name;
	}


	@Override
	public SwingPicDrawer getSwingPicDrawer() {
		return swingPicDrawer;
	}

	@Override
	public boolean isShowBoundingBoxes() {
		return showBoundingBoxes;
	}


	@Override
	public void setFrame(WatchmakerTabbedPane frame) {
		this.frame = frame;

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
	public void setMorphViewsTabbedPane(MorphViewsTabbedPane morphViewsTabbedPane) {
		this.morphViewsTabbedPane = morphViewsTabbedPane;

	}
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
    public void setShowBoundingBoxes(boolean newValue) {
        boolean oldValue = this.showBoundingBoxes;
        this.showBoundingBoxes = newValue;
        
        pcs.firePropertyChange("showBoundingBoxes", oldValue, newValue);
        morphViewsTabbedPane.getSelectedComponent().repaint();
    }

	@Override
	public void setSwingPicDrawer(SwingPicDrawer swingPicDrawer) {
		this.swingPicDrawer = swingPicDrawer;
	}
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
		
	}

}
