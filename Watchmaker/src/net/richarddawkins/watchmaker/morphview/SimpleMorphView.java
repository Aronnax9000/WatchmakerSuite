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
import net.richarddawkins.watchmaker.menu.MenuBuilder;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morph.draw.MorphDrawer;

public abstract class SimpleMorphView implements MorphView {
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.morphview.SimpleMorphView");
    protected Album album;

    protected AppData appData;

    protected boolean copyMorphsOnBackup;

    protected WatchmakerCursorFactory cursors;
    protected GeneBoxStrip geneBoxStrip;
    protected String icon;
    protected boolean indexed;
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

    public boolean isIndexed() { return indexed; }
    
    public SimpleMorphView(MorphViewConfig config) {
        logger.fine("SwingMorphView(config): " + config);
        this.appData = config.appData;
        this.setIcon(config.icon);
        this.setName(config.name);
        this.setCopyMorphsOnBackup(config.copyMorphsOnBackup);
        this.createPanel();
        this.setIndexed(config.indexed);
        this.cursors = appData.getWatchmakerCursorFactory();
        this.setMorphDrawer(appData.newMorphDrawer());
        initAlbum(config.album, this.copyMorphsOnBackup);
        addGeneBoxStrip(config.engineeringMode, config.geneBoxToSide);
        addSeedMorphs(config.seedMorphs);
        addSliders();
        addPanels();
        setSelectedPanel(panels.firstElement());
        this.menuBuilder = MorphViewFactoryService.getInstance().getFactory()
                .getMorphViewMenuBuilder(config.type.getName());
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
            logger.info("SimpleMorphView.initAlbum " + album);
        } else {
            this.album = new Album("backing");
        }
        logger.info("SimpleMorphView.initAlbum " + album);

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

    @Override
    public void removePropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(propertyName, listener);
    }

    @Override
    public synchronized void seed() {
        if (!seedMorphs.isEmpty()) {
            synchronized (seedMorphs) {

                logger.fine("Seeding");

                Morph seedMorphOriginal = seedMorphs.firstElement();

                Morph seedMorph = appData.getMorphConfig()
                        .copyMorph(seedMorphOriginal);
                MorphViewPanel panel = (MorphViewPanel) panels.firstElement();

                seedMorph.addPropertyChangeListener(panel);

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

                panel.setSpecial(midBox);

                seedMorph.setImage(null);

                Dim boxDim = boxes.getBox(0, panel.getDim()).getDim();
                Dim parentMorphDim = seedMorph.getPhenotype().getMargin()
                        .getDim();
                logger.fine(
                        "SwingMorphView.seed():" + panel.getDim() + " BoxDim:"
                                + boxDim + " ParentMorphDim:" + parentMorphDim);

                seedMorphs.remove(seedMorphOriginal);

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
    public void backup(boolean copyMorph) {

        // logger.fine("Backup " + copyMorph + " started " + album.size());
        // BoxedMorphCollection boxedMorphCollection =
        // this.getSelectedPanel().getBoxedMorphCollection();
        // GridBoxManager oldBoxManager = (GridBoxManager) boxedMorphCollection
        // .getBoxManager();
        // GridBoxManager backupBoxManager = new
        // GridBoxManager(oldBoxManager.cols,
        // oldBoxManager.rows);
        // BoxedMorphCollection backupBoxedMorphs = new BoxedMorphCollection(
        // "backing", backupBoxManager);
        // Iterator<Rect> boxIterator = backupBoxManager.getBoxes().iterator();
        // for (BoxedMorph boxedMorph : boxedMorphCollection.getBoxedMorphs()) {
        // Rect box;
        // if (boxedMorphCollection.size() == 1) {
        // box = backupBoxManager.getMidBox();
        // } else {
        // box = boxIterator.next();
        // }
        //
        // Morph newMorph;
        // if (copyMorph) {
        // newMorph = appData.getMorphConfig()
        // .copyMorph(boxedMorph.getMorph());
        //
        // } else {
        // newMorph = boxedMorph.getMorph();
        // }
        // BoxedMorph newBoxedMorph = new BoxedMorph(backupBoxManager,
        // newMorph, box);
        //
        // backupBoxedMorphs.add(newBoxedMorph);
        // }
        // // Add the backup just behind the one on the end (so we still work on
        // // the original)
        // album.addPage(album.size() - 1, backupBoxedMorphs);
        // if (album.size() > Album.MAX_PAGES) {
        // album.removePage(0);
        // }
        // logger.fine("Backup " + copyMorph + " finished " + album.size());
    }

    @Override
    public void undo() {
        BoxedMorphCollection boxedMorphCollection = getSelectedPanel()
                .getBoxedMorphCollection();
        int level = album.indexOfPage(boxedMorphCollection);
        if (level > 0) {
            logger.info("Undo level: " + level + " size " + album.size());
            BoxedMorphCollection oldValue = boxedMorphCollection;
            boxedMorphCollection = album.getPage(level - 1);
            repaint();
        } else {
            // In Engineering, current boxedMorphCollection isn't part of
            // album.
            logger.info("Undo Engineering Mode album size " + album.size());
            backup(true);
            BoxedMorphCollection oldValue = boxedMorphCollection;
            boxedMorphCollection = album.getPreviousPage(album.getLastPage());
            repaint();
        }
    }

    @Override
    public void redo() {
        BoxedMorphCollection boxedMorphCollection = album.firstElement();

        int level = album.indexOfPage(boxedMorphCollection);
        if (level < album.size() - 1) {
            logger.info("Redo level: " + level + " size " + album.size());
            BoxedMorphCollection oldValue = boxedMorphCollection;
            boxedMorphCollection = album.getPage(level + 1);
            repaint();
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

    protected MenuBuilder menuBuilder;

    public MenuBuilder getMenuBuilder() {
        return menuBuilder;
    }

    public void setMenuBuilder(MenuBuilder menuBuilder) {
        this.menuBuilder = menuBuilder;
    }

    @Override
    public void buildMenu(WatchmakerMenuBar menuBar) {
        menuBuilder.buildMenu(menuBar);

    }

    @Override
    public void cleanMenu(WatchmakerMenuBar menuBar) {
        menuBuilder.cleanMenu(menuBar);

    }

    @Override
    public void updateMenu(WatchmakerMenuBar menuBar) {
        menuBuilder.updateMenu(menuBar);

    }
}
