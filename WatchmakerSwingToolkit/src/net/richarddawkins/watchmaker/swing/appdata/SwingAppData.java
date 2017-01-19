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
	public void addBreedingMorphView(Morph morph);
	public void addDefaultMorphView();
	public void addEngineeringMorphView(Morph morph);
	public void addPropertyChangeListener(PropertyChangeListener listener);
	public WatchmakerTabbedPane getFrame();
	public void setFrame(WatchmakerTabbedPane frame);
	public MenuBuilder getMenuBuilder();
	public MorphConfig getMorphConfig();
	public Morph getMorphOfTheHour();
	public MorphViewsTabbedPane getMorphViewsTabbedPane();
	public String getName();

	public SwingPicDrawer getSwingPicDrawer();
	public void setSwingPicDrawer(SwingPicDrawer swingPicDrawer);
	
	public boolean isShowBoundingBoxes();
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode);
	public void setMenuBuilder(MenuBuilder menuBuilder);
	public void setMorphConfig(MorphConfig config);
	public void setMorphViewsTabbedPane(MorphViewsTabbedPane morphViewsTabbedPane);
	public void setName(String name);
	public void setShowBoundingBoxes(boolean newValue);


	public void setIconFromFilename(String filename);
	public Icon getIcon();
	public String getToolTip();
	public void setIcon(Icon icon);
	public void setToolTip(String toolTip);
	
}
