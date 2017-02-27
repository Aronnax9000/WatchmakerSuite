package net.richarddawkins.watchmaker.swing.app;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JTabbedPane;

import net.richarddawkins.watchmaker.album.Album;
import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.geom.BoxesDrawer;
import net.richarddawkins.watchmaker.menu.MenuBuilder;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.MorphViewType;
import net.richarddawkins.watchmaker.morphview.MorphViewsTabbedPanel;
import net.richarddawkins.watchmaker.phenotype.PhenotypeDrawer;
import net.richarddawkins.watchmaker.swing.drawer.SwingBoxesDrawer;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewFactory;

public abstract class SwingAppData implements AppData {

    @Override
    public void newRandomStart() {
        // TODO Auto-generated method stub        MorphConfig config = appData.getMorphConfig();
        Morph morph = getMorphOfTheHour();
        Genome genome = config.getGenomeFactory().deliverSaltation();
        morph.setGenome(genome);
        MorphView morphView = getMorphViewsTabbedPane()
                .getSelectedMorphView();
        morphView.addSeedMorph(morph);
        
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(name);
        if(breedRightAway) buffer.append(" breedRightAway");
        if(saltOnEmptyBreedingBoxClick) buffer.append(" saltOnEmptyBreedingBoxClick");
        if(geneBoxToSide) buffer.append(" geneBoxToSide");
        if(highlighting) buffer.append(" highlighting");
        buffer.append(" " + defaultBreedingCols + "x" + defaultBreedingRows);
        return buffer.toString();
    }
    
    protected BoxesDrawer boxesDrawer = new SwingBoxesDrawer();
    protected boolean breedRightAway = true;
    protected MorphConfig config;
    protected int defaultBreedingCols = 5;
    protected int defaultBreedingRows = 3;
    protected boolean geneBoxToSide;
    protected boolean highlighting = false;
    protected String icon;
    protected MenuBuilder menuBuilder;
    protected MorphViewsTabbedPanel morphViewsTabbedPane;
    protected String name;
    protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    protected PhenotypeDrawer phenotypeDrawer;
    protected boolean saltOnEmptyBreedingBoxClick = false;
    protected long tickDelay = 4;
    protected String toolTip;

    public SwingAppData() {
    }

    @Override
    public void addAlbumMorphView(Album album) {
        morphViewsTabbedPane.addMorphView(SwingMorphViewFactory.getMorphView(this, 
                MorphViewType.pedigree, null));        

    }

    @Override
    public void addBreedingMorphView(Morph morph) {
        Vector<Morph> seedMorphs = new Vector<Morph>();
        seedMorphs.add(morph);
        MorphView morphView = SwingMorphViewFactory.getMorphView(this,
                MorphViewType.breeding, null);
        morphViewsTabbedPane.addMorphView(morphView);
        if (morph != null) {
            Morph copy = config.newMorph();
            Genome genome = config.newGenome();
            morph.getGenome().copy(genome);
            copy.setGenome(genome);
            seedMorphs.add(copy);
        } else {
            
        }
    }

    @Override
    public void addDefaultMorphView() {
        addBreedingMorphView(null);
    }

    @Override
    public void addEngineeringMorphView(Morph morph) {
        Vector<Morph> seedMorphs = new Vector<Morph>();
        seedMorphs.add(morph);
        MorphView morphView = SwingMorphViewFactory.getMorphView(this,
                MorphViewType.engineering, seedMorphs);
        morphViewsTabbedPane.addMorphView(morphView);
    }

    @Override
    public void addPedigreeMorphView(Morph morph) {
        Vector<Morph> seedMorphs = new Vector<Morph>();
        seedMorphs.add(morph);
        morphViewsTabbedPane.addMorphView(SwingMorphViewFactory.getMorphView(this, 
                MorphViewType.pedigree, seedMorphs));        

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    @Override
    public void addPropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName, listener);
    }
    @Override
	public void addTriangleMorphView() {
        Vector<Morph> seedMorphs = new Vector<Morph>();
        seedMorphs.addAll(Arrays.asList( this.getMorphConfig().getTriangleMorphs()));
		morphViewsTabbedPane.addMorphView(SwingMorphViewFactory.getMorphView(this, 
		        MorphViewType.triangle, seedMorphs));
		
	}

    @Override
    public BoxesDrawer getBoxesDrawer() {
        return boxesDrawer;
    }

    @Override
    public int getDefaultBreedingCols() {
        return defaultBreedingCols;
    }

    @Override
    public int getDefaultBreedingRows() {
        return defaultBreedingRows;
    }

    @Override
    public String getIcon() {
        return icon;
    }

    @Override
    public MenuBuilder getMenuBuilder() {
        return menuBuilder;
    }

    @Override
    public MorphConfig getMorphConfig() {
        return config;
    }

    @Override
    public Morph getMorphOfTheHour() {
        MorphViewsTabbedPanel pane = this.getMorphViewsTabbedPane();
        MorphView morphView = (MorphView) ((JTabbedPane) pane)
                .getSelectedComponent();
        if (morphView != null) {
            return morphView.getMorphOfTheHour();
        } else {
            return null;
        }
    }

    @Override
    public MorphViewsTabbedPanel getMorphViewsTabbedPane() {
        return this.morphViewsTabbedPane;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PhenotypeDrawer getPhenotypeDrawer() {
        return phenotypeDrawer;
    }

    @Override
    public long getTickDelay() {
        return tickDelay;
    }

    @Override
    public String getToolTip() {
        return toolTip;
    }

    public boolean isBreedRightAway() {
        return breedRightAway;
    }

    public boolean isGeneBoxToSide() {
        return geneBoxToSide;
    }

    public boolean isHighlighting() {
        return highlighting;
    }

    public boolean isSaltOnEmptyBreedingBoxClick() {
        return saltOnEmptyBreedingBoxClick;
    }

    public void setBreedRightAway(boolean breedRightAway) {
        this.breedRightAway = breedRightAway;
    }

    @Override
    public void setDefaultBreedingCols(int defaultBreedingCols) {
        this.defaultBreedingCols = defaultBreedingCols;
    }

    @Override
    public void setDefaultBreedingRows(int defaultBreedingRows) {
        this.defaultBreedingRows = defaultBreedingRows;
    }

    public void setGeneBoxToSide(boolean geneBoxToSide) {
        this.geneBoxToSide = geneBoxToSide;
    }

    public void setHighlighting(boolean newValue) {
        boolean oldValue = highlighting;
        highlighting = newValue;
        pcs.firePropertyChange("highlighting", oldValue, newValue);
    }

    @Override
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public void setMenuBuilder(MenuBuilder menuBuilder) {
        this.menuBuilder = menuBuilder;
    }

    @Override
    public void setMorphConfig(MorphConfig config) {
        this.config = config;
        config.setAppData(this);

    }

    @Override
    public void setMorphViewsTabbedPane(
            MorphViewsTabbedPanel morphViewsTabbedPane) {
        this.morphViewsTabbedPane = morphViewsTabbedPane;

    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPhenotypeDrawer(PhenotypeDrawer newValue) {
        this.phenotypeDrawer = newValue;
    }

    public void setSaltOnEmptyBreedingBoxClick(
            boolean saltOnEmptyBreedingBoxClick) {
        this.saltOnEmptyBreedingBoxClick = saltOnEmptyBreedingBoxClick;
    }

    @Override
    public void setTickDelay(long tickDelay) {
        this.tickDelay = tickDelay;
    }

    @Override
    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }
}
