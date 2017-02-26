package net.richarddawkins.watchmaker.swing.morphview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridBagLayout;
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
import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morph.draw.MorphDrawer;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;
import net.richarddawkins.watchmaker.swing.components.SwingScaleSlider;
import net.richarddawkins.watchmaker.swing.components.SwingSpeedSlider;
import net.richarddawkins.watchmaker.swing.drawer.SwingMorphDrawer;

public abstract class SwingMorphView extends JPanel
        implements MorphView, PropertyChangeListener {

    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.morphview.SwingMorphView");

    protected Vector<Morph> seedMorphs = new Vector<Morph>();
    private static final long serialVersionUID = 5555392236002752598L;

    protected Album album;

    protected AppData appData;
    private boolean copyMorphsOnBackup;

    protected String icon;

    protected MorphDrawer morphDrawer;
    protected final Vector<MorphViewPanel> panels = new Vector<MorphViewPanel>();

    protected MorphViewPanel selectedPanel;

    protected boolean showBoxes = true;

    protected String toolTip;

    public SwingMorphView(AppData appData, String icon, String name,
            Album newAlbum, boolean engineeringMode) {

    }

    @Override
    public void addSeedMorph(Morph seedMorph) {
        seedMorphs.add(seedMorph);
        
    }


    @Override
    public void initBoxedMorphCollection(Album newAlbum, boolean engineeringMode) {
        if (newAlbum != null) {
            this.album = newAlbum;
        } else {
            this.album = new Album("backing");
        }
        BoxedMorphCollection boxedMorphCollection;
        if (album.size() == 0) {
            boxedMorphCollection = new BoxedMorphCollection("backing", newBoxManager());
            if (! engineeringMode) {
                album.addPage(boxedMorphCollection);
            }
        } else {
            boxedMorphCollection = album.firstElement();
        }

    }

    protected SwingScaleSlider scaleSlider;

    public SwingMorphView(SwingMorphViewConfig config) {
        logger.info("SwingMorphView(config): " + config);
        this.appData = config.appData;
        this.setIcon(config.icon);
        this.setName(config.name);
        // this.setPreferredSize(new Dimension(640, 480));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setCopyMorphsOnBackup(config.copyMorphsOnBackup);
        this.setLayout(new BorderLayout());

        this.setMorphDrawer(new SwingMorphDrawer(appData.getPhenotypeDrawer()));
        // Do we need this? Was probably just for scale
//        phenotypeDrawer.getDrawingPreferences().addPropertyChangeListener(this);

        
        initBoxedMorphCollection(config.album, config.engineeringMode);
        addSeedMorphs(config.seedMorphs);
        addGeneBoxStrip(config.engineeringMode, config.geneBoxToSide); 
        addPanels();
        addSliders();
        setSelectedPanel(panels.firstElement());
    }
    @Override
    public void seed() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addPanels() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addSeedMorphs(Vector<Morph> seedMorphs) {
        if (seedMorphs != null) {
            seedMorphs.addAll(seedMorphs);
            seedMorphs.clear();
        }
    }

    @Override
    public BoxManager newBoxManager() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addGeneBoxStrip(boolean engineeringMode, boolean geneBoxToSide) {
        GeneBoxStrip geneBoxStrip = appData
                .newGeneBoxStrip(engineeringMode);
        JPanel geneBoxStripPanel = (JPanel) geneBoxStrip.getPanel();
        geneBoxStripPanel.setLayout(new GridBagLayout());
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
    public void addSliders() {
        JPanel sliders = new JPanel(new GridLayout(1, 0));
        scaleSlider = new SwingScaleSlider(this);
        sliders.add(scaleSlider.getPanel());
        SwingSpeedSlider speedSlider = new SwingSpeedSlider(appData);
        sliders.add(speedSlider.getPanel());
    
        this.add(sliders, BorderLayout.PAGE_END);
        
    }
    
    @Override
    public void addPanel(MorphViewPanel panel) {
        panels.add(panel);
        ((Container) this).add((Component) panel);
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
    public MorphViewPanel getSelectedPanel() {
        return selectedPanel;
    }

    @Override
    public String getToolTip() {
        return toolTip;
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
            logger.info("SwingMorphViewPanel propertyChange:" + propertyName);

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
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public void setMorphDrawer(MorphDrawer morphDrawer) {
        this.morphDrawer = morphDrawer;
    }

    protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    @Override
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName, listener);
    }
    @Override
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(propertyName, listener);
    }
    @Override
    public void setSelectedPanel(MorphViewPanel newValue) {
        MorphViewPanel oldValue = selectedPanel;
        this.selectedPanel = newValue;
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
