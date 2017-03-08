package net.richarddawkins.watchmaker.swing.morphview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;
import net.richarddawkins.watchmaker.morphview.ScaleSlider;
import net.richarddawkins.watchmaker.swing.components.SwingScaleSlider;
import net.richarddawkins.watchmaker.swing.components.SwingSpeedSlider;
import net.richarddawkins.watchmaker.swing.drawer.SwingMorphDrawer;

public abstract class SwingMorphView extends JPanel
        implements MorphView, PropertyChangeListener {
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.morphview.SwingMorphView");

    private static final long serialVersionUID = 5555392236002752598L;

    protected WatchmakerCursorFactory cursors;
    @Override
    public WatchmakerCursorFactory getCursors() {
        return cursors;
    }

    protected Album album;
    protected AppData appData;
    protected boolean copyMorphsOnBackup;
    protected GeneBoxStrip geneBoxStrip;
    protected String icon;
    protected MorphDrawer morphDrawer;
    protected final Vector<MorphViewPanel> panels = new Vector<MorphViewPanel>();
    protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    protected SwingScaleSlider scaleSlider;
    protected Vector<Morph> seedMorphs = new Vector<Morph>();
    protected MorphViewPanel selectedPanel;
    protected boolean showBoxes = true;
    protected String toolTip;

    public SwingMorphView(SwingMorphViewConfig config) {
        logger.fine("SwingMorphView(config): " + config);
        this.appData = config.appData;
        this.setIcon(config.icon);
        this.setName(config.name);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setCopyMorphsOnBackup(config.copyMorphsOnBackup);
        this.setLayout(new BorderLayout());
        this.cursors = appData.getWatchmakerCursorFactory();
        this.setMorphDrawer(new SwingMorphDrawer(appData));
        initAlbum(config.album, this.copyMorphsOnBackup);
        addGeneBoxStrip(config.engineeringMode, config.geneBoxToSide); 
        addSeedMorphs(config.seedMorphs);
        addSliders();
        addPanels();
        setSelectedPanel(panels.firstElement());
    }

    @Override
    public void addGeneBoxStrip(boolean engineeringMode, boolean geneBoxToSide) {
        geneBoxStrip = appData
                .newGeneBoxStrip(engineeringMode);
        JPanel geneBoxStripPanel = (JPanel) geneBoxStrip.getPanel();
        if (geneBoxToSide) {
            // Nassty nassty JScrollPane will center our content otherwise
            JPanel dummy = new JPanel();
            dummy.add(geneBoxStripPanel);
            JScrollPane scrollPane = new JScrollPane(dummy);
            this.add(scrollPane, BorderLayout.LINE_END);
        } else {
            this.add(geneBoxStripPanel, BorderLayout.PAGE_START);
        }
    }

    @Override
    public void addPanel(MorphViewPanel panel) {
        panels.add(panel);
        ((Container) this).add((Component) panel.getPanel());
        this.setSelectedPanel(panel);
    }    
    
    @Override
    public void addPanels() {

    }


    @Override
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
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
    public void addSliders() {
        JPanel sliders = new JPanel(new GridLayout(1, 0));
        scaleSlider = new SwingScaleSlider(this);
        sliders.add(scaleSlider.getPanel());
        SwingSpeedSlider speedSlider = new SwingSpeedSlider(appData);
        sliders.add(speedSlider.getPanel());
        this.add(sliders, BorderLayout.PAGE_END);
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
        
        if (! copyMorphsOnBackup && album.size() == 0) {
            BoxedMorphCollection page = new BoxedMorphCollection("backing",
                    newBoxManager());
            album.addPage(page);
        }
    }

    @Override
    public boolean isShowBoxes() {
        return showBoxes;
    }

    @Override
    public void paintComponent(Graphics g) {
        logger.fine("SwingMorphView.paintComponent()");
        super.paintComponent(g);
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
    public void removePanel(MorphViewPanel panel) {
        ((Container) this).remove((Component) panel);
        if (selectedPanel == panel) {
            setSelectedPanel(null);
        }
        panels.remove(panel);
        panel.removePropertyChangeListener(geneBoxStrip);
    }

    @Override
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(propertyName, listener);
    }

    @Override
    public void seed() {

        if (!seedMorphs.isEmpty()) {
            synchronized (seedMorphs) {
                logger.fine("Seeding");

                Morph seedMorph = seedMorphs.firstElement();
                SwingMorphViewPanel panel = (SwingMorphViewPanel) panels
                        .firstElement();
                BoxedMorphCollection boxedMorphCollection = panel
                        .getBoxedMorphCollection();
                BoxManager boxes = boxedMorphCollection.getBoxManager();

                boxedMorphCollection.clear();
                panel.setSelectedBox(null);
                Rect midBox = boxes.getMidBox();
                BoxedMorph boxedMorph = new BoxedMorph(boxes, seedMorph,
                        midBox);
                boxedMorphCollection.removeAllElements();
                boxedMorphCollection.add(boxedMorph);
                logger.fine("SwingMorphView.seed() Added boxedMorph: " + boxedMorph);
                // Trigger first breeding

                panel.setSpecial(midBox);
//                if (appData.isBreedRightAway()) {
//                    logger.fine("Setting panel special to " + midBox);
//                    
//                    panel.setSpecial(midBox);
//                    ((SwingBreedingMorphViewPanel) panel)
//                            .breedFromSpecial();
//                }
                seedMorph.setImage(null);

                Dim boxDim = boxes.getBox(0, panel.getDim()).getDim();
                Dim parentMorphDim = seedMorph.getPhenotype().getMargin()
                        .getDim();
                logger.fine("SwingMorphView.seed():" + panel.getDim() + " BoxDim:" + boxDim
                        + " ParentMorphDim:" + parentMorphDim);
//                int scale = boxDim.getScale(parentMorphDim, Globals.zoomBase);
//
//                if (scale != boxes.getScale()) {
//                    boxes.setScale(scale);
//                }
//                panel.setSelectedBox(midBox);
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

    @Override
    public void setSelectedPanel(MorphViewPanel newValue) {
        MorphViewPanel oldValue = selectedPanel;
        if(oldValue != null) {
            oldValue.loseFocus();
        }
        this.selectedPanel = newValue;
        if(newValue != null) {
            newValue.gainFocus();
        }
        BoxManager boxes = newValue.getBoxedMorphCollection().getBoxManager();
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
