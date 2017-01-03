package net.richarddawkins.wm.morphs;

import java.awt.Component;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;

import net.richarddawkins.wm.BreedingPanel;
import net.richarddawkins.wm.MenuBuilder;

public interface MorphConfig {

  
  int getGeneBoxCount();
  
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

	Mutagen getMutagen();
	void setMutagen(Mutagen mutagen);

	boolean[] getMut();
	boolean isShowBoundingBoxes();
	void setShowBoundingBoxes(boolean showBoundingBoxes);
  BreedingPanel getBreedingPanel();
//  GeneBoxStrip getGeneBoxStrip();
//  BreedingPanel getBreedingPanel();
  int getDefaultBreedingRows();
  int getDefaultBreedingCols();
  void setDefaultBreedingRows(int defaultBreedingRows);
  void setDefaultBreedingCols(int defaultBreedingCols);
}