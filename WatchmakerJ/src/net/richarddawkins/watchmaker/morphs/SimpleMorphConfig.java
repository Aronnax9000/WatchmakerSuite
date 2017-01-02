package net.richarddawkins.watchmaker.morphs;

import java.awt.Component;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.richarddawkins.watchmaker.BreedingAndGeneBoxPanel;
import net.richarddawkins.watchmaker.ClassicImageLoader;
import net.richarddawkins.watchmaker.MenuBuilder;
import net.richarddawkins.watchmaker.WatchmakerGUI;

public abstract class SimpleMorphConfig implements MorphConfig {

  public int getDefaultBreedingRows() {
    return defaultBreedingRows;
  }

  public void setDefaultBreedingRows(int defaultBreedingRows) {
    this.defaultBreedingRows = defaultBreedingRows;
  }

  public int getDefaultBreedingCols() {
    return defaultBreedingCols;
  }

  public void setDefaultBreedingCols(int defaultBreedingCols) {
    this.defaultBreedingCols = defaultBreedingCols;
  }
  protected int defaultBreedingRows;
  protected int defaultBreedingCols;
  protected WatchmakerGUI gui;
  protected Component container;

  public Component getContainer() {
    return container;
  }

  public void setContainer(Component container) {
    this.container = container;
  }
  protected BreedingAndGeneBoxPanel breedingAndGeneBoxPanel;
  
  @Override
  public BreedingAndGeneBoxPanel getBreedingAndGeneBoxPanel() {
    if (breedingAndGeneBoxPanel == null)
      breedingAndGeneBoxPanel = new BreedingAndGeneBoxPanel(this);
    return breedingAndGeneBoxPanel;
  }
  protected int geneBoxCount = 0;
  public int getGeneBoxCount() {return geneBoxCount;}
  protected Icon icon;

  /*
   * (non-Javadoc)
   * 
   * @see net.richarddawkins.watchmaker.morphs.impl.MorphConfig#getIcon()
   */
  @Override
  public Icon getIcon() {
    return icon;
  }

  protected void setIconFromFilename(String filename) {
    icon = new ImageIcon(ClassicImageLoader.getPicture(filename).getImage());
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.richarddawkins.watchmaker.morphs.impl.MorphConfig#getMenuBuilder()
   */
  protected MenuBuilder menuBuilder;

  @Override
  public MenuBuilder getMenuBuilder() {
    return menuBuilder;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.richarddawkins.watchmaker.morphs.impl.MorphConfig#createMorph(int)
   */
  @Override
  public abstract Morph createMorph(int type);

  protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

  /*
   * (non-Javadoc)
   * 
   * @see
   * net.richarddawkins.watchmaker.morphs.impl.MorphConfig#addPropertyChangeListener(java.beans.
   * PropertyChangeListener)
   */
  @Override
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    this.pcs.addPropertyChangeListener(listener);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * net.richarddawkins.watchmaker.morphs.impl.MorphConfig#removePropertyChangeListener(java.beans.
   * PropertyChangeListener)
   */
  @Override
  public void removePropertyChangeListener(PropertyChangeListener listener) {
    this.pcs.removePropertyChangeListener(listener);
  }

  protected boolean recordingFossils;

  /*
   * (non-Javadoc)
   * 
   * @see net.richarddawkins.watchmaker.morphs.impl.MorphConfig#isRecordingFossils()
   */
  @Override
  public boolean isRecordingFossils() {
    return recordingFossils;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.richarddawkins.watchmaker.morphs.impl.MorphConfig#setRecordingFossils(boolean)
   */
  @Override
  public void setRecordingFossils(boolean newValue) {
    boolean oldValue = recordingFossils;
    recordingFossils = newValue;
    if (newValue != oldValue)
      pcs.firePropertyChange("recordingFossils", oldValue, newValue);
  }

  protected String name;

  /*
   * (non-Javadoc)
   * 
   * @see net.richarddawkins.watchmaker.morphs.impl.MorphConfig#getName()
   */
  @Override
  public String getName() {
    return name;
  }

  protected String toolTip;

  /*
   * (non-Javadoc)
   * 
   * @see net.richarddawkins.watchmaker.morphs.impl.MorphConfig#getToolTip()
   */
  @Override
  public String getToolTip() {
    return toolTip;
  }
  
  protected boolean showBoundingBoxes;

  public boolean isShowBoundingBoxes() {
    return showBoundingBoxes;
  }

  public void setShowBoundingBoxes(boolean showBoundingBoxes) {
    this.showBoundingBoxes = showBoundingBoxes;
  }

}
