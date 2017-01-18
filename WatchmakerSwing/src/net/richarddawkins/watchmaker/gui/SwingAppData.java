package net.richarddawkins.watchmaker.gui;

import java.beans.PropertyChangeListener;

import javax.swing.Icon;

import net.richarddawkins.watchmaker.genome.gui.GeneBoxStrip;
import net.richarddawkins.watchmaker.geom.swing.SwingPicDrawer;
import net.richarddawkins.watchmaker.gui.menu.MenuBuilder;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;

public interface SwingAppData {
	void addBreedingMorphView(Morph morph);
	void addDefaultMorphView();
	void addEngineeringMorphView(Morph morph);
	void addPropertyChangeListener(PropertyChangeListener listener);
    WatchmakerTabbedPane getFrame();
    MenuBuilder getMenuBuilder();
    MorphConfig getMorphConfig();
	Morph getMorphOfTheHour();
	MorphViewsTabbedPane getMorphViewsTabbedPane();
	String getName();
	SwingPicDrawer getSwingPicDrawer();
	boolean isShowBoundingBoxes();
	GeneBoxStrip newGeneBoxStrip(boolean engineeringMode);
	void setFrame(WatchmakerTabbedPane frame);
	void setMenuBuilder(MenuBuilder menuBuilder);
	void setMorphConfig(MorphConfig config);
	void setMorphViewsTabbedPane(MorphViewsTabbedPane morphViewsTabbedPane);
	void setName(String name);
	void setShowBoundingBoxes(boolean newValue);
	void setSwingPicDrawer(SwingPicDrawer swingPicDrawer);


	void setIconFromFilename(String filename);
	Icon getIcon();
	String getToolTip();
	
}
