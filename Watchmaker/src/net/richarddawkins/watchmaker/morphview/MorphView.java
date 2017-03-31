package net.richarddawkins.watchmaker.morphview;

import java.beans.PropertyChangeListener;
import java.util.Vector;

import net.richarddawkins.watchmaker.album.Album;
import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.cursor.WatchmakerCursorFactory;
import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.menu.MenuBuilder;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.MorphDrawer;

public interface MorphView extends PropertyChangeListener, MenuBuilder {

    
    
	String getIcon();
	void setIcon(String icon);

	AppData getAppData();

	String getToolTip();

	void setToolTip(String toolTip);
	void seed();
	Morph getMorphOfTheHour();
	String getName();
	void setName(String newName);
	void setShowBoxes(boolean showBoxes);
	void setMorphDrawer(MorphDrawer morphDrawer);
	void addSeedMorph(Morph morph);

	void setAppData(AppData appData);
	boolean isShowBoxes();
	MorphDrawer getMorphDrawer();
	Vector<MorphViewPanel> getPanels();
    void undo();
    void redo();
    void backup(boolean copyMorph);
    MorphViewPanel getSelectedPanel();
    void setSelectedPanel(MorphViewPanel selectedPanel);
    Album getAlbum();
    void setAlbum(Album album);
    void addPanel(MorphViewPanel panel);
    void removePanel(MorphViewPanel panel);
    void setCopyMorphsOnBackup(boolean copyMorphsOnBackup);
    void addPanels();
    void addSliders();
    BoxManager newBoxManager();
    void addGeneBoxStrip(boolean engineeringMode, boolean geneBoxToSide);
    void addSeedMorphs(Vector<Morph> seedMorphs);
    void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);
    void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);
;
    void repaint();
    void initAlbum(Album newAlbum, boolean copyMorphsOnBackup);
    GeneBoxStrip getGeneBoxStrip();
    void setGeneBoxStrip(GeneBoxStrip geneBoxStrip);
    ScaleSlider getScaleSlider();
    WatchmakerCursorFactory getCursors();
    Object getPanel();
    void createPanel();
    void gainFocus();
    void setIndexed(boolean newIndexMode);
    boolean isIndexed();
}