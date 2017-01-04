package net.richarddawkins.watchmaker.morph.common;

import java.awt.Component;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;

import net.richarddawkins.watchmaker.gui.WatchmakerPanel;
import net.richarddawkins.watchmaker.gui.WatchmakerTabbedPane;
import net.richarddawkins.watchmaker.gui.menu.MenuBuilder;
import net.richarddawkins.watchmaker.gui.old.BreedingPanelOld;

public interface MorphConfig {

	int getGeneBoxCount();

	WatchmakerPanel getWatchmakerPanel();
	void setWatchmakerPanel(WatchmakerPanel panel);

	Component getContainer();

	void setContainer(Component container);

	Icon getIcon();

	MenuBuilder getMenuBuilder();

	Morph createMorph(int type);

	void addPropertyChangeListener(PropertyChangeListener listener);

	void removePropertyChangeListener(PropertyChangeListener listener);

	boolean isRecordingFossils();

	void setRecordingFossils(boolean newValue);

	String getName();

	String getToolTip();
    void setMutagen(Mutagen mutagen);
	Mutagen getMutagen();

	boolean[] getMut();

	boolean isShowBoundingBoxes();

	void setShowBoundingBoxes(boolean showBoundingBoxes);

	// GeneBoxStrip getGeneBoxStrip();
	// BreedingPanel getBreedingPanel();
	int getDefaultBreedingRows();

	int getDefaultBreedingCols();

	void setDefaultBreedingRows(int defaultBreedingRows);

	void setDefaultBreedingCols(int defaultBreedingCols);

	void setFrame(WatchmakerTabbedPane frame);

	WatchmakerTabbedPane getFrame();
	BreedingPanelOld getBreedingPanel();
}