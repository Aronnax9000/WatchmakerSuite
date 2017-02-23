package net.richarddawkins.watchmaker.morph;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.album.Album;
import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.embryo.Embryology;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GenomeFactory;
import net.richarddawkins.watchmaker.genome.Triangler;
import net.richarddawkins.watchmaker.genome.mutation.AllowedMutations;
import net.richarddawkins.watchmaker.genome.mutation.Mutagen;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.GridBoxManager;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.phenotype.PhenotypeDrawer;

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
    @Override
    public Morph copyMorph(Morph morph) {
        Morph copy = newMorph();
        Genome genome = newGenome();
        morph.getGenome().copy(genome);
        copy.setGenome(genome);
        return copy;
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
    public Morph newMorph(int type) {
        Genome genome = genomeFactory.getBasicType(type);
        Morph morph = newMorph();
        morph.setPhenotype(newPhenotype());
        morph.setGenome(genome);
        wireMorphEvents(morph);

        PhenotypeDrawer phenotypeDrawer = appData.getPhenotypeDrawer();
        BufferedImage bufferedImage = (BufferedImage) phenotypeDrawer
                .getImage(morph.getPhenotype(), 1.0);
        morph.setImage(bufferedImage);
        return morph;
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    @Override
    public Morph reproduce(Morph parentMorph) {
        Genome childGenome = newGenome();
        parentMorph.getGenome().copy(childGenome);
        getMutagen().mutate(childGenome);
        Morph childMorph = newMorph();
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

    protected void wireMorphEvents(Morph morph) {
        morph.addPropertyChangeListener(getEmbryology());
    }

    @Override
    public String[] getSavedAnimals() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector<Album> getAlbums() {
        Vector<BoxedMorphCollection> rawAlbums = new Vector<BoxedMorphCollection>();
        for (String name : getSavedAnimals()) {
            logger.info("Loading:" + name);
            InputStream is = this.getClass().getResourceAsStream(name);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while (true) {
                int readByte = -1;
                try {
                    readByte = is.read();
                } catch (IOException e) {
                    logger.severe("While reading from saved animal file " + name
                            + ":" + e.getMessage());
                }
                if (readByte == -1) {
                    break;
                }
                baos.write(readByte);
            }
            ByteBuffer byteBuffer = ByteBuffer.wrap(baos.toByteArray());
            // byteBuffer.order(ByteOrder.BIG_ENDIAN);

            String rawName = name.substring(name.lastIndexOf('/') + 1);
            GridBoxManager boxManager = new GridBoxManager(5);
            BoxedMorphCollection rawAlbum = new BoxedMorphCollection(rawName, boxManager);
            int boxNo = 0;
            while (byteBuffer.hasRemaining() && boxNo < 60) {
                Genome genome = newGenome();
                genome.readFromByteBuffer(byteBuffer);
                Morph morph = newMorph();
                morph.setGenome(genome);
                logger.info(name + " box:" + boxNo + " Genome:"
                        + genome.toString());
                
                BoxedMorph boxedMorph = new BoxedMorph(boxManager, morph,
                        boxManager.getBox(boxNo++));
                rawAlbum.add(boxedMorph);
            }
//            boxManager.setRows((boxNo + 1) / 5);
            logger.info("Album " + name + " contained " + boxNo + " genomes.");
            rawAlbum.setSelectedBoxedMorph(rawAlbum.getBoxedMorphs().firstElement());
            rawAlbums.add(rawAlbum);
        }
        Vector<BoxedMorphCollection> singletonCollections = new Vector<BoxedMorphCollection>();

        for (BoxedMorphCollection rawAlbum : rawAlbums) {
            if (rawAlbum.size() == 1) {
                for (BoxedMorph boxedMorph : rawAlbum.getBoxedMorphs()) {
                    String name = rawAlbum.getName();
                    boxedMorph.getMorph()
                            .setName(name.substring(name.lastIndexOf('/') + 1));
                }
                singletonCollections.add(rawAlbum);
            }
        }

        if (singletonCollections.size() != 0) {
            GridBoxManager boxManager = new GridBoxManager(5);
            BoxedMorphCollection singletonCollection = new BoxedMorphCollection("Singleton", boxManager);
            for (BoxedMorphCollection rawAlbum : singletonCollections) {
                for (BoxedMorph boxedMorph : rawAlbum.getBoxedMorphs()) {
                    boxedMorph.setBoxes(boxManager);
                    boxManager.addBox(boxedMorph.getBox());
                    singletonCollection.add(boxedMorph);
                }
                rawAlbums.remove(rawAlbum);
            }
            rawAlbums.add(singletonCollection);
        }
        
        Vector<Album> albums = new Vector<Album>();
        
        for(BoxedMorphCollection rawAlbum: rawAlbums) {
            Album album = new Album(rawAlbum.getName());
            albums.add(album);
            Iterator<BoxedMorph> boxedMorphIter = rawAlbum.iterator();
            BoxedMorphCollection page = null;
            GridBoxManager boxes = null;
            int size = 0;
            while(boxedMorphIter.hasNext()) {
                if(page == null) {
                    boxes = new GridBoxManager(5);
                    boxes.setAccentuateMidBox(false);
                    page = new BoxedMorphCollection("Page " + (size / 15 + 1), boxes);
                    
                    album.addPage(page);
                }
                BoxedMorph boxedMorph = boxedMorphIter.next();
                boxedMorph.setBoxes(boxes);
                boxes.addBox(boxedMorph.getBox());
                page.add(boxedMorph);
                if(++size % 15 == 0) {
                    page = null;
                }
            }
            rawAlbum.clear();
            
        }
        return albums;
    }

}
