package net.richarddawkins.watchmaker.morph;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.album.Album;
import net.richarddawkins.watchmaker.album.AlbumSerializer;
import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.embryo.Embryology;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GenomeFactory;
import net.richarddawkins.watchmaker.genome.Triangler;
import net.richarddawkins.watchmaker.genome.mutation.AllowedMutations;
import net.richarddawkins.watchmaker.genome.mutation.Mutagen;
import net.richarddawkins.watchmaker.morph.selector.MorphSelector;
import net.richarddawkins.watchmaker.morph.selector.MorphZillaSelector;

public abstract class SimpleMorphConfig implements MorphConfig {
     private static Logger logger = Logger
     .getLogger("net.richarddawkins.watchmaker.morph.SimpleMorphConfig");
    protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    protected AllowedMutations allowedMutations;
    protected AppData appData;
    protected Embryology embryology;
    protected GenomeFactory genomeFactory;
    protected Mutagen mutagen;
    protected boolean recordingFossils;

    
    public void setTriangleMorph(int index, Morph morph) {
        getTriangleMorphs()[index] = morph;    
    }
    

    
    protected MorphSelector selector = new MorphZillaSelector();

    public MorphSelector getSelector() {
        return selector;
    }

    public void setSelector(MorphSelector selector) {
        this.selector = selector;
    }

    @Override
    public Morph copyMorph(Morph morph) {
        Morph copy = newMorph();
        Genome genome = newGenome();
        morph.getGenome().copy(genome);
        copy.setGenome(genome);
        return copy;
    }

    @Override
    public Collection<Album> getAlbums() {
        Collection<Album> albums = new AlbumSerializer(this).getBuiltInAlbums();
        for(Album album: albums) {
            logger.info("SimpleMorphConfig.getAlbums: " + album);
        }
        return albums;
    }

    int startingMorphBasicType;
    protected Morph[] triangleMorphs = null;

    public synchronized Morph[] getTriangleMorphs() {
        if (triangleMorphs == null) {
            triangleMorphs = new Morph[3];
            for (int i = 0; i < 3; i++) {
                this.triangleMorphs[i] = this.newMorph(i + 1);
            }
        }

        return triangleMorphs;
    }

    @Override
    public Triangler getTriangler() {
        return null;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    @Override
    public AllowedMutations getAllowedMutations() {
        return allowedMutations;
    }

    @Override
    public void setAllowedMutations(AllowedMutations muts) {
        this.allowedMutations = muts;
    }

    public AppData getAppData() {
        return appData;
    }

    public Embryology getEmbryology() {
        return embryology;
    }

    public GenomeFactory getGenomeFactory() {
        return genomeFactory;
    }

    @Override
    public Morph getLitter(Morph parentMorph, int litterSize) {
        Vector<Morph> litter = new Vector<Morph>();
        for (int i = 0; i < litterSize; i++) {
            litter.add(this.reproduce(parentMorph));
        }
        return litter.elementAt(0);
    }

    public Mutagen getMutagen() {
        return mutagen;
    }

    public int getStartingMorphBasicType() {
        return startingMorphBasicType;
    }

    @Override
    public boolean isRecordingFossils() {
        return recordingFossils;
    }

    @Override
    public void initMorph(Morph morph) {
        morph.setEmbryology(getEmbryology());
        morph.setPhenotypeDrawer(appData.getPhenotypeDrawer());
    }

    @Override
    public Morph newMorph(int type) {
        Morph morph = newMorph();
        Genome genome = genomeFactory.getBasicType(type);
        morph.setGenome(genome);
        return morph;
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    @Override
    public Morph reproduce(Morph parentMorph) {
        Morph childMorph = newMorph();

        Genome childGenome = newGenome();
        parentMorph.getGenome().copy(childGenome);
        getMutagen().mutate(childGenome);
        childMorph.setGenome(childGenome);

        parentMorph.getPedigree().addOffspring(childMorph);

        return childMorph;
    }

    @Override
    public void setAppData(AppData appData) {
        this.appData = appData;
    }

    @Override
    public void setEmbryology(Embryology embryology) {
        this.embryology = embryology;
    }

    public void setGenomeFactory(GenomeFactory genomeFactory) {
        this.genomeFactory = genomeFactory;
    }

    public void setMutagen(Mutagen mutagen) {
        this.mutagen = mutagen;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.richarddawkins.watchmaker.watchmaker.morphs.impl.MorphConfig#
     * setRecordingFossils(boolean)
     */
    @Override
    public void setRecordingFossils(boolean newValue) {
        boolean oldValue = recordingFossils;
        recordingFossils = newValue;
        if (newValue != oldValue)
            pcs.firePropertyChange("recordingFossils", oldValue, newValue);
    }

    @Override
    public void setStartingMorphBasicType(int startingMorphBasicType) {
        this.startingMorphBasicType = startingMorphBasicType;
    }

}
