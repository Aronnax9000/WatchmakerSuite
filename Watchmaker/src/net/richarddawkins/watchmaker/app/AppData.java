package net.richarddawkins.watchmaker.app;

import java.beans.PropertyChangeListener;
import java.util.Vector;

import net.richarddawkins.watchmaker.album.Album;
import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.geom.BoxesDrawer;
import net.richarddawkins.watchmaker.menu.MenuBuilder;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.MorphViewsTabbedPanel;
import net.richarddawkins.watchmaker.phenotype.PhenotypeDrawer;

/**
 * An instance of AppData provides a facade representing an instance
 * of a Watchmaker session which deals with one particular subclass of Morph.
 * 
 * An AppData instance is associated with an instance of MorphConfig: this association
 * bridges the gap between Watchmaker application logic and presentation logic.
 * 
 * @author Alan Canon
 *
 */
public interface AppData  {

    
    public void startTimedBreed();
    public void stopTimedBreed();
    /**
     * Returns a list of AlbumMorphViews, in reverse order of tab order (most recently opened first)
     * @return a list of AlbumMorphViews, in reverse order of tab order (most recently opened first)
     */
    public Vector<MorphView> getAlbumMorphViews();
	public boolean isBreedRightAway();
	public void setBreedRightAway(boolean newValue);
	public void actionBreedFromSelector();
	
	public boolean isSaltOnEmptyBreedingBoxClick();
	public void setSaltOnEmptyBreedingBoxClick(boolean newValue);
	
	public MorphConfig getMorphConfig();
	public void setMorphConfig(MorphConfig config);
	

	public void setDefaultBreedingCols(int defaultBreedingCols);
	public void setDefaultBreedingRows(int defaultBreedingRows);

	public int getDefaultBreedingCols();
	public int getDefaultBreedingRows();

	
	public void addBreedingMorphView(Morph morph);
	public void addDefaultMorphView();
	public void addEngineeringMorphView(Morph morph);
	public void addPropertyChangeListener(PropertyChangeListener listener);
	public String getIcon();
	public MenuBuilder getMenuBuilder();
	public Morph getMorphOfTheHour();
	public MorphViewsTabbedPanel getMorphViewsTabbedPane();
	public String getName();
	public PhenotypeDrawer getPhenotypeDrawer();
	public String getToolTip();
    public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode);


	public void setIcon(String icon);
	public void setMenuBuilder(MenuBuilder menuBuilder);
	public void setMorphViewsTabbedPane(MorphViewsTabbedPanel morphViewsTabbedPane);
	public void setName(String name);
	public void setToolTip(String toolTip);
	public BoxesDrawer getBoxesDrawer();
	public void setPhenotypeDrawer(PhenotypeDrawer newValue);
	public void addTriangleMorphView();
	public boolean isGeneBoxToSide();
	public void setGeneBoxToSide(boolean newValue);
	public void setHighlighting(boolean b);
	public boolean isHighlighting();
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);
	public long getTickDelay();
	void setTickDelay(long tickDelay);
    public void addPedigreeMorphView();
    void addAlbumMorphView(Album album);
    public void newRandomStart();
    public void addClassicAlbums();
    public Album getCurrentAlbum();
    public void setCurrentAlbum(Album album);
    void albumDelete();
    void albumSaveAs();
    void albumSave();
    void albumOpen();
    void albumNew();
    public void albumExport();
    void addMorphToAlbum();
    Vector<MorphView> getBreedingMorphViews();
    public boolean isShowBoundingBox();
    public void setShowBoundingBox(boolean newValue); 
    

}
