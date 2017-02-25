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
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.richarddawkins.watchmaker.album.Album;
import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morph.draw.MorphDrawer;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;
import net.richarddawkins.watchmaker.phenotype.PhenotypeDrawer;
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

    public SwingMorphView(AppData appData, String icon, String name, Album newAlbum,
            boolean engineeringMode) {

    }

    public void addSeedMorph(Morph seedMorph) {
        seedMorphs.add(seedMorph);
    }
    public SwingMorphView(SwingMorphViewConfig config) {
        logger.info("SwingMorphView(config): " + config);
        this.appData = config.appData;
        this.setIcon(config.icon);
        this.setName(config.name);
        seedMorphs.addAll(config.seedMorphs);
        this.setCopyMorphsOnBackup(config.copyMorphsOnBackup);
        this.setLayout(new BorderLayout());
        if (config.album != null) {
            this.album = config.album;
        } else {
            this.album = new Album("backing");
        }
        BoxedMorphCollection boxedMorphCollection;
        if (album.size() == 0) {
            boxedMorphCollection = new BoxedMorphCollection();
            if (!config.engineeringMode) {
                album.addPage(boxedMorphCollection);
            }
        } else {
            boxedMorphCollection = album.getPage(0);
        }

        // this.setPreferredSize(new Dimension(640, 480));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        PhenotypeDrawer phenotypeDrawer = appData.getPhenotypeDrawer();
        morphDrawer = new SwingMorphDrawer(phenotypeDrawer);
        phenotypeDrawer.getDrawingPreferences().addPropertyChangeListener(this);

        JPanel sliders = new JPanel(new GridLayout(1,0));
        SwingScaleSlider scaleSlider = new SwingScaleSlider(
                appData.getPhenotypeDrawer().getDrawingPreferences());
        sliders.add(scaleSlider.getPanel());
        SwingSpeedSlider speedSlider = new SwingSpeedSlider(appData);
        sliders.add(speedSlider.getPanel());
        
        this.add(sliders, BorderLayout.PAGE_END);
        GeneBoxStrip geneBoxStrip = appData.newGeneBoxStrip(config.engineeringMode);
        
        JPanel geneBoxStripPanel = (JPanel) geneBoxStrip.getPanel();
        geneBoxStripPanel.setLayout(new GridBagLayout());
        if (config.geneBoxToSide) {
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
        ((Container) this).add((Component) panel);
        setSelectedPanel(panel);
    }
    public void backup(boolean copyMorph) {
    }



    @Override
    public Album getAlbum() {
        return album;
    }

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
        return null;
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
            
            for (Morph morph : selectedPanel.getBoxedMorphCollection().getMorphs()) {
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
        if (selectedPanel == panel)
            selectedPanel = null;
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

    public void setCopyMorphsOnBackup(boolean copyMorphsOnBackup) {
        this.copyMorphsOnBackup = copyMorphsOnBackup;
        
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public void setMorphDrawer(MorphDrawer morphDrawer) {
        this.morphDrawer = morphDrawer;
    }

    @Override
    public void setSelectedPanel(MorphViewPanel selectedPanel) {
        this.selectedPanel = selectedPanel;
    }

    @Override
    public void setShowBoxes(boolean showBoxes) {
        this.showBoxes = showBoxes;
    }

    @Override
    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

    public void undo() {
    }

}
