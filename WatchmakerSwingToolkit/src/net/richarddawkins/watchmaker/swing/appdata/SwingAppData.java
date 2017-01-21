package net.richarddawkins.watchmaker.swing.appdata;

import java.beans.PropertyChangeListener;

import net.richarddawkins.watchmaker.app.MultiMorphTypeTabbedPanel;
import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morphview.MorphViewsTabbedPanel;
import net.richarddawkins.watchmaker.swing.geom.SwingPicDrawer;
import net.richarddawkins.watchmaker.swing.menubuilder.MenuBuilder;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewsTabbedPanel;

public interface SwingAppData {
	public void addBreedingMorphView(Morph morph);
	public void addDefaultMorphView();
	public void addEngineeringMorphView(Morph morph);
	public void addPropertyChangeListener(PropertyChangeListener listener);
	public MultiMorphTypeTabbedPanel getFrame();
	public void setFrame(MultiMorphTypeTabbedPanel frame);
	public MenuBuilder getMenuBuilder();
	public MorphConfig getMorphConfig();
	public Morph getMorphOfTheHour();
	public MorphViewsTabbedPanel getMorphViewsTabbedPane();
	public String getName();

	public SwingPicDrawer getSwingPicDrawer();
	public void setSwingPicDrawer(SwingPicDrawer swingPicDrawer);
	
	public boolean isShowBoundingBoxes();
	public void setMenuBuilder(MenuBuilder menuBuilder);
	public void setMorphConfig(MorphConfig config);
	public void setMorphViewsTabbedPane(SwingMorphViewsTabbedPanel morphViewsTabbedPane);
	public void setName(String name);
	public void setShowBoundingBoxes(boolean newValue);


	public String getIcon();
	public String getToolTip();
	public void setIcon(String icon);
	public void setToolTip(String toolTip);
	GeneBoxStrip newGeneBoxStrip(boolean engineeringMode);
}
