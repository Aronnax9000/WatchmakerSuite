package net.richarddawkins.watchmaker.swing.array;

import java.beans.PropertyChangeListener;
import java.util.Vector;

import net.richarddawkins.watchmaker.album.Album;
import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.MorphDrawer;
import net.richarddawkins.watchmaker.morphview.MorphViewConfig;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;
import net.richarddawkins.watchmaker.morphview.ScaleSlider;
import net.richarddawkins.watchmaker.morphview.array.ArrayMorphView;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphView;

public class SwingArrayMorphView extends SwingMorphView
        implements ArrayMorphView {


    public SwingArrayMorphView(MorphViewConfig config) {
        super(config);
    }

    @Override
    public String getIcon() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setIcon(String icon) {
        // TODO Auto-generated method stub

    }

    @Override
    public AppData getAppData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getToolTip() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setToolTip(String toolTip) {
        // TODO Auto-generated method stub

    }

    @Override
    public void seed() {
        // TODO Auto-generated method stub

    }

    @Override
    public Morph getMorphOfTheHour() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setName(String newName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setShowBoxes(boolean showBoxes) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setMorphDrawer(MorphDrawer morphDrawer) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addSeedMorph(Morph morph) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setAppData(AppData appData) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isShowBoxes() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public MorphDrawer getMorphDrawer() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector<MorphViewPanel> getPanels() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub

    }

    @Override
    public void redo() {
        // TODO Auto-generated method stub

    }

    @Override
    public void backup(boolean copyMorph) {
        // TODO Auto-generated method stub

    }

    @Override
    public MorphViewPanel getSelectedPanel() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setSelectedPanel(MorphViewPanel selectedPanel) {
        // TODO Auto-generated method stub

    }

    @Override
    public Album getAlbum() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAlbum(Album album) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addPanel(MorphViewPanel panel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removePanel(MorphViewPanel panel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setCopyMorphsOnBackup(boolean copyMorphsOnBackup) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addPanels() {
        // TODO Auto-generated method stub

    }

    @Override
    public void addSliders() {
        // TODO Auto-generated method stub

    }

    @Override
    public BoxManager newBoxManager() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addGeneBoxStrip(boolean engineeringMode,
            boolean geneBoxToSide) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addSeedMorphs(Vector<Morph> seedMorphs) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addPropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removePropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        // TODO Auto-generated method stub

    }

    @Override
    public void repaint() {
        // TODO Auto-generated method stub

    }

    @Override
    public void initAlbum(Album newAlbum, boolean copyMorphsOnBackup) {
        // TODO Auto-generated method stub

    }

    @Override
    public GeneBoxStrip getGeneBoxStrip() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setGeneBoxStrip(GeneBoxStrip geneBoxStrip) {
        // TODO Auto-generated method stub

    }

    @Override
    public ScaleSlider getScaleSlider() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void buildMenu(WatchmakerMenuBar menuBar) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void cleanMenu(WatchmakerMenuBar menuBar) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateMenu(WatchmakerMenuBar menuBar) {
        // TODO Auto-generated method stub
        
    }

}
