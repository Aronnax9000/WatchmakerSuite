package net.richarddawkins.watchmaker.swing.appdata;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.resourceloader.ClassicImageLoader;
import net.richarddawkins.watchmaker.swing.breed.BreedingWatchmakerPanel;
import net.richarddawkins.watchmaker.swing.engineer.EngineeringWatchmakerPanel;
import net.richarddawkins.watchmaker.swing.geom.SwingPicDrawer;
import net.richarddawkins.watchmaker.swing.menubuilder.MenuBuilder;
import net.richarddawkins.watchmaker.swing.morphview.MorphView;
import net.richarddawkins.watchmaker.swing.morphview.MorphViewsTabbedPane;
import net.richarddawkins.watchmaker.swing.wtp.WatchmakerTabbedPane;
abstract public class SimpleSwingAppData implements SwingAppData {
	protected MorphConfig config;
	protected WatchmakerTabbedPane frame;
	protected Icon icon;
	protected MenuBuilder menuBuilder;
	protected MorphViewsTabbedPane morphViewsTabbedPane;
	protected String name;
    protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	protected boolean showBoundingBoxes = true;
	protected SwingPicDrawer swingPicDrawer;
	protected String toolTip;
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
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
		
	}
	@Override
	public WatchmakerTabbedPane getFrame() {
		return frame;
	}
	
	@Override
	public Icon getIcon() {
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
	public String getToolTip() {
		return toolTip;
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
	public void setIcon(Icon icon) {
		this.icon = icon;
	}


	@Override
	public void setIconFromFilename(String filename) {
		icon = new ImageIcon(ClassicImageLoader.getPicture(filename).getImage());
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
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}
	
}
