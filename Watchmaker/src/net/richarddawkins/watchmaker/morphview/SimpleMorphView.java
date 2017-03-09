package net.richarddawkins.watchmaker.morphview;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.album.Album;
import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.cursor.WatchmakerCursorFactory;
import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morph.draw.MorphDrawer;

public abstract class SimpleMorphView implements MorphView {
    private static Logger logger = Logger
            .getLogger("net.richarddawkins.watchmaker.morphview.SimpleMorphView");
    protected Album album;

    protected AppData appData;

    protected boolean copyMorphsOnBackup;

    protected WatchmakerCursorFactory cursors;
    protected GeneBoxStrip geneBoxStrip;
    protected String icon;
    protected MorphDrawer morphDrawer;
    protected String name;
    protected final Vector<MorphViewPanel> panels = new Vector<MorphViewPanel>();
    protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    protected ScaleSlider scaleSlider;
    protected Vector<Morph> seedMorphs = new Vector<Morph>();
    protected MorphViewPanel selectedPanel;
    protected boolean showBoxes = true;
    protected String toolTip;
    
    public void gainFocus() {
        this.appData.setSelectedMorphView(this);
    }
    
    public SimpleMorphView(MorphViewConfig config) {
        logger.fine("SwingMorphView(config): " + config);
        this.appData = config.appData;
        this.setIcon(config.icon);
        this.setName(config.name);
        this.setCopyMorphsOnBackup(config.copyMorphsOnBackup);
        this.createPanel();
        this.cursors = appData.getWatchmakerCursorFactory();
        this.setMorphDrawer(appData.newMorphDrawer());
        initAlbum(config.album, this.copyMorphsOnBackup);
        addGeneBoxStrip(config.engineeringMode, config.geneBoxToSide);
        addSeedMorphs(config.seedMorphs);
        addSliders();
        addPanels();
        setSelectedPanel(panels.firstElement());
    }
    @Override
    public void addPropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName, listener);
    }
    @Override
    public void addSeedMorph(Morph seedMorph) {
        seedMorphs.add(seedMorph);
        panels.firstElement().repaint();
    }
    @Override
    public void addSeedMorphs(Vector<Morph> seedMorphsToAdd) {
        if (seedMorphsToAdd != null) {
            this.seedMorphs.addAll(seedMorphsToAdd);
            seedMorphsToAdd.clear();
        }
    }

    public void backup(boolean copyMorph) {
    }

    @Override
    public Album getAlbum() {
        return album;
    }

    @Override
    public AppData getAppData() {
        return appData;
    }

    @Override
    public WatchmakerCursorFactory getCursors() {
        return cursors;
    }

    @Override
    public GeneBoxStrip getGeneBoxStrip() {
        return geneBoxStrip;
    }

    @Override
    public String getIcon() {
        return icon;
    }

    @Override
    public MorphDrawer getMorphDrawer() {
        return morphDrawer;
    }

    @Override
    public Morph getMorphOfTheHour() {
        return this.getSelectedPanel().getMorphOfTheHour();
    }

    public String getName() {
        return name;
    }

    @Override
    public Vector<MorphViewPanel> getPanels() {
        return panels;
    }

    @Override
    public ScaleSlider getScaleSlider() {
        return scaleSlider;
    }

    @Override
    public MorphViewPanel getSelectedPanel() {
        return selectedPanel;
    }

    @Override
    public String getToolTip() {
        return toolTip;
    }

    @Override
    public void initAlbum(Album newAlbum, boolean copyMorphsOnBackup) {
        if (newAlbum != null) {
            this.album = newAlbum;
        } else {
            this.album = new Album("backing");
        }

        if (!copyMorphsOnBackup && album.size() == 0) {
            BoxedMorphCollection page = new BoxedMorphCollection("backing",
                    newBoxManager());
            album.addPage(page);
        }
    }

    @Override
    public boolean isShowBoxes() {
        return showBoxes;
    }

    public void propertyChange(PropertyChangeEvent event) {
        String propertyName = event.getPropertyName();
        if (propertyName.equals("showBoundingBoxes")
                || propertyName.equals("scale")
                || propertyName.equals("phenotype")) {
            logger.fine("SwingMorphViewPanel propertyChange:" + propertyName);

            for (Morph morph : selectedPanel.getBoxedMorphCollection()
                    .getMorphs()) {
                morph.setImage(null);
            }
            selectedPanel.repaint();
        }
    }

    public void redo() {
    }
    
    @Override
    public void removePropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(propertyName, listener);
    }

    @Override
    public void seed() {

        if (!seedMorphs.isEmpty()) {
            synchronized (seedMorphs) {
                logger.fine("Seeding");

                Morph seedMorph = seedMorphs.firstElement();
                MorphViewPanel panel = (MorphViewPanel) panels
                        .firstElement();
                BoxedMorphCollection boxedMorphCollection = panel
                        .getBoxedMorphCollection();
                BoxManager boxes = boxedMorphCollection.getBoxManager();

                boxedMorphCollection.clear();
                panel.setSelectedBoxedMorph(null);
                Rect midBox = boxes.getMidBox();
                BoxedMorph boxedMorph = new BoxedMorph(boxes, seedMorph,
                        midBox);
                boxedMorphCollection.removeAllElements();
                boxedMorphCollection.add(boxedMorph);
                logger.fine("SwingMorphView.seed() Added boxedMorph: "
                        + boxedMorph);
                // Trigger first breeding

                panel.setSpecial(midBox);
                // if (appData.isBreedRightAway()) {
                // logger.fine("Setting panel special to " + midBox);
                //
                // panel.setSpecial(midBox);
                // ((SwingBreedingMorphViewPanel) panel)
                // .breedFromSpecial();
                // }
                seedMorph.setImage(null);

                Dim boxDim = boxes.getBox(0, panel.getDim()).getDim();
                Dim parentMorphDim = seedMorph.getPhenotype().getMargin()
                        .getDim();
                logger.fine(
                        "SwingMorphView.seed():" + panel.getDim() + " BoxDim:"
                                + boxDim + " ParentMorphDim:" + parentMorphDim);

                seedMorphs.remove(seedMorph);

            }
            repaint();
        }
    }

    @Override
    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public void setAppData(AppData appData) {
        this.appData = appData;
    }

    @Override
    public void setCopyMorphsOnBackup(boolean copyMorphsOnBackup) {
        this.copyMorphsOnBackup = copyMorphsOnBackup;

    }

    @Override
    public void setGeneBoxStrip(GeneBoxStrip geneBoxStrip) {
        this.geneBoxStrip = geneBoxStrip;
    }
    @Override
    public void setIcon(String icon) {
        this.icon = icon;
    }



    @Override
    public void setMorphDrawer(MorphDrawer morphDrawer) {
        this.morphDrawer = morphDrawer;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setSelectedPanel(MorphViewPanel newValue) {
        MorphViewPanel oldValue = selectedPanel;
        if (oldValue != null) {
            oldValue.loseFocus();
        }
        this.selectedPanel = newValue;
        if (newValue != null) {
            newValue.gainFocus();
        }
        pcs.firePropertyChange("selectedPanel", oldValue, newValue);
    }

    @Override
    public void setShowBoxes(boolean showBoxes) {
        this.showBoxes = showBoxes;
    }

    @Override
    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }
    @Override
    public void undo() {
    }


}
