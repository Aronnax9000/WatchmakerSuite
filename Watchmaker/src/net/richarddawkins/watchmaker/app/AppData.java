package net.richarddawkins.watchmaker.app;

import java.beans.PropertyChangeListener;

import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.geom.BoxesDrawer;
import net.richarddawkins.watchmaker.menu.MenuBuilder;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morphview.MorphViewsTabbedPanel;
import net.richarddawkins.watchmaker.phenotype.PhenotypeDrawer;

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
	public PhenotypeDrawer getPhenotypeDrawer();
	public String getToolTip();
    public boolean isShowBoundingBoxes();
    public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode);

    public void setFrame(MultiMorphTypeTabbedPanel frame);
	public void setIcon(String icon);
	public void setMenuBuilder(MenuBuilder menuBuilder);
	public void setMorphConfig(MorphConfig config);
	public void setMorphViewsTabbedPane(MorphViewsTabbedPanel morphViewsTabbedPane);
	public void setName(String name);
	public void setPicDrawer(PhenotypeDrawer newValue);
	public void setShowBoundingBoxes(boolean newValue);
	public void setToolTip(String toolTip);
	public BoxesDrawer getBoxesDrawer();
}
