package net.richarddawkins.watchmaker.app;

import java.beans.PropertyChangeListener;

import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.geom.PicDrawer;
import net.richarddawkins.watchmaker.menu.MenuBuilder;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morphview.MorphViewsTabbedPanel;

public interface AppData {
	public void addBreedingMorphView(Morph morph);
	public void addDefaultMorphView();
	public void addEngineeringMorphView(Morph morph);
	public void addPropertyChangeListener(PropertyChangeListener listener);
	public MultiMorphTypeTabbedPanel getFrame();
	public String getIcon();
	public MenuBuilder getMenuBuilder();
	public MorphConfig getMorphConfig();
	public Morph getMorphOfTheHour();
	public MorphViewsTabbedPanel getMorphViewsTabbedPane();
	public String getName();
	public PicDrawer getPicDrawer();
	public String getToolTip();
    public boolean isShowBoundingBoxes();
    public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode);

    public void setFrame(MultiMorphTypeTabbedPanel frame);
	public void setIcon(String icon);
	public void setMenuBuilder(MenuBuilder menuBuilder);
	public void setMorphConfig(MorphConfig config);
	public void setMorphViewsTabbedPane(MorphViewsTabbedPanel morphViewsTabbedPane);
	public void setName(String name);
	public void setPicDrawer(PicDrawer newValue);
	public void setShowBoundingBoxes(boolean newValue);
	public void setToolTip(String toolTip);
}
