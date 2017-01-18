package net.richarddawkins.watchmaker.swing.appdata;

import java.beans.PropertyChangeListener;

import javax.swing.Icon;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.swing.geneboxstrip.GeneBoxStrip;
import net.richarddawkins.watchmaker.swing.geom.SwingPicDrawer;
import net.richarddawkins.watchmaker.swing.menubuilder.MenuBuilder;
import net.richarddawkins.watchmaker.swing.morphview.MorphViewsTabbedPane;
import net.richarddawkins.watchmaker.swing.wtp.WatchmakerTabbedPane;

public interface SwingAppData {
	void addBreedingMorphView(Morph morph);
	void addDefaultMorphView();
	void addEngineeringMorphView(Morph morph);
	void addPropertyChangeListener(PropertyChangeListener listener);
    WatchmakerTabbedPane getFrame();
	void setFrame(WatchmakerTabbedPane frame);
    MenuBuilder getMenuBuilder();
    MorphConfig getMorphConfig();
	Morph getMorphOfTheHour();
	MorphViewsTabbedPane getMorphViewsTabbedPane();
	String getName();

	SwingPicDrawer getSwingPicDrawer();
	void setSwingPicDrawer(SwingPicDrawer swingPicDrawer);
	
	boolean isShowBoundingBoxes();
	GeneBoxStrip newGeneBoxStrip(boolean engineeringMode);
	void setMenuBuilder(MenuBuilder menuBuilder);
	void setMorphConfig(MorphConfig config);
	void setMorphViewsTabbedPane(MorphViewsTabbedPane morphViewsTabbedPane);
	void setName(String name);
	void setShowBoundingBoxes(boolean newValue);


	void setIconFromFilename(String filename);
	Icon getIcon();
	String getToolTip();
	
}
