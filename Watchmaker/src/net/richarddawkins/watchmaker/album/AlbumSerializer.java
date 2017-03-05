package net.richarddawkins.watchmaker.album;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.GridBoxManager;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;

public class AlbumSerializer {
    private static Logger logger = Logger
            .getLogger("net.richarddawkins.watchmaker.album.AlbumSerializer");
    protected MorphConfig config;

    /**
     * Constructs a new AlbumSerializer for the given MorphConfig.
     * 
     * @param config
     *            the MorphConfig for the morph species for which Albums are to
     *            be serialized.
     */
    public AlbumSerializer(MorphConfig config) {
        this.config = config;
    }

    /**
     * Fetch albums from resource files bundled with the morph species.
     * 
     * @return a new collection of Albums from the built-in resource files for
     *         this morph species.
     */
    public Collection<Album> getBuiltInAlbums() {
        Collection<BoxedMorphCollection> rawAlbums = getBuiltInBoxedMorphCollections();
        Collection<BoxedMorphCollection> singletonCollections = getSingletonCollections(
                rawAlbums);

        if (singletonCollections.size() != 0) {
            condenseSingletons(rawAlbums, singletonCollections);
        }

        Collection<Album> albums = paginateBoxedMorphCollectionsIntoAlbums(
                rawAlbums);
        return albums;
    }

    /**
     * Given the list of saved animal file names from the MorphConfig, load each
     * BoxedMorphCollection from the named resource files.
     * 
     * @return a collection of collections of saved animals from resources
     *         files.
     */
    protected Collection<BoxedMorphCollection> getBuiltInBoxedMorphCollections() {
        Collection<BoxedMorphCollection> rawAlbums = new Vector<BoxedMorphCollection>();
        for (String name : config.getSavedAnimals()) {
            logger.info("Loading:" + name);
            InputStream is = this.getClass().getResourceAsStream(name);
            BoxedMorphCollection rawAlbum = getBoxedMorphCollectionFromStream(
                    is, name);
            rawAlbums.add(rawAlbum);
        }
        return rawAlbums;

    }
    
    public void putAlbumToFile(Album album, File file) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            logger.warning("IOException while opening output stream to "
                    + file.getAbsolutePath() + ": " + e.getMessage());
        }
        if(os != null) {
            for(BoxedMorphCollection boxedMorphs: album.getPages()) {
                putBoxedMorphCollectionToOutputStream(boxedMorphs, os);
            }
        }
        try {
            os.close();
        } catch (IOException e) {
            logger.warning("IOException while closing output stream to "
                    + file.getAbsolutePath() + ": " + e.getMessage());
        }
        
    }

    public void putBoxedMorphCollectionToFile(BoxedMorphCollection boxedMorphs, File file) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            logger.warning("IOException while opening output stream to "
                    + file.getAbsolutePath() + ": " + e.getMessage());
        }
        if(os != null) {
            putBoxedMorphCollectionToOutputStream(boxedMorphs, os);
        }
        try {
            os.close();
        } catch (IOException e) {
            logger.warning("IOException while closing output stream to "
                    + file.getAbsolutePath() + ": " + e.getMessage());
        }
    }

    protected void putBoxedMorphCollectionToOutputStream(
            BoxedMorphCollection boxedMorphs, OutputStream os) {
        try {
            os.write(putBoxedMorphCollectionToByteArray(boxedMorphs));
        } catch (IOException e) {
            logger.warning(
                    "IOException while writing boxed morph collection byte array to output stream: "
                            + e.getMessage());
        }
    }

    protected byte[] putBoxedMorphCollectionToByteArray(
            BoxedMorphCollection boxedMorphs) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (BoxedMorph boxedMorph : boxedMorphs.getBoxedMorphs()) {
            Genome genome = boxedMorph.getMorph().getGenome();
            ByteBuffer byteBuffer = ByteBuffer
                    .allocate(genome.getSizeInBytes());
            genome.writeToByteBuffer(byteBuffer);
            try {
                baos.write(byteBuffer.array());
            } catch (IOException e) {
                logger.warning(
                        "IOException while writing genome to byte buffer: "
                                + e.getMessage());
            }
        }
        return baos.toByteArray();

    }

    protected BoxedMorphCollection getBoxedMorphCollectionFromByteBuffer(
            ByteBuffer byteBuffer, String name) {
        // byteBuffer.order(ByteOrder.BIG_ENDIAN);

        String rawName = name.substring(name.lastIndexOf('/') + 1);
        GridBoxManager boxManager = new GridBoxManager(5);
        BoxedMorphCollection boxedMorphs = new BoxedMorphCollection(rawName,
                boxManager);
        int boxNo = 0;
        while (byteBuffer.hasRemaining() && boxNo < 60) {
            Genome genome = config.newGenome();
            genome.readFromByteBuffer(byteBuffer);
            Morph morph = config.newMorph();
            morph.setGenome(genome);
            logger.info(
                    name + " box:" + boxNo + " Genome:" + genome.toString());

            BoxedMorph boxedMorph = new BoxedMorph(boxManager, morph,
                    boxManager.getBox(boxNo++));
            boxedMorphs.add(boxedMorph);
        }
        // boxManager.setRows((boxNo + 1) / 5);
        logger.info("Album " + name + " contained " + boxNo + " genomes.");
        boxedMorphs.setSelectedBoxedMorph(
                boxedMorphs.getBoxedMorphs().firstElement());
        return boxedMorphs;
    }

    protected BoxedMorphCollection getBoxedMorphCollectionFromStream(
            InputStream is, String name) {
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
        BoxedMorphCollection boxedMorphs = getBoxedMorphCollectionFromByteBuffer(
                byteBuffer, name);
        return boxedMorphs;

    }

    /**
     * Given a collection of BoxedMorphCollections, return those collections
     * which only contain a single morph.
     * 
     * @param collections
     *            a collection of BoxedMorphCollections
     * @return a collection of BoxedMorphCollections, each of which contains
     *         only a single morph.
     */
    protected Collection<BoxedMorphCollection> getSingletonCollections(
            Collection<BoxedMorphCollection> collections) {
        Vector<BoxedMorphCollection> singletonCollections = new Vector<BoxedMorphCollection>();

        for (BoxedMorphCollection rawAlbum : collections) {
            if (rawAlbum.size() == 1) {
                for (BoxedMorph boxedMorph : rawAlbum.getBoxedMorphs()) {
                    String name = rawAlbum.getName();
                    boxedMorph.getMorph()
                            .setName(name.substring(name.lastIndexOf('/') + 1));
                }
                singletonCollections.add(rawAlbum);
            }
        }
        return singletonCollections;
    }

    /**
     * Given a raw collection of boxed morphs, paginate the morphs into an album
     * of boxed morph collections of no more than 15 morphs each.
     * 
     * @param rawCollection
     *            a collection of boxed morphs.
     * @return a new Album, paginated with the morphs from the raw collection.
     */
    protected Album paginateBoxedMorphCollectionIntoAlbum(
            BoxedMorphCollection rawCollection) {
        Album album = new Album(rawCollection.getName());
        Iterator<BoxedMorph> boxedMorphIter = rawCollection.iterator();
        BoxedMorphCollection page = null;
        int size = 0;
        GridBoxManager boxes = null;
        while (boxedMorphIter.hasNext()) {
            if (page == null) {
                boxes = new GridBoxManager(5);
                boxes.setAccentuateMidBox(false);
                page = new BoxedMorphCollection("Page " + (size / 15 + 1),
                        boxes);
                album.addPage(page);
            }
            BoxedMorph boxedMorph = boxedMorphIter.next();
            boxedMorph.setBoxes(boxes);
            boxes.addBox(boxedMorph.getBox());
            page.add(boxedMorph);
            if (++size % 15 == 0) {
                page = null;
            }
        }
        album.setWritable(false);
        rawCollection.clear();
        return album;
    }

    /**
     * Given a collection of BoxedMorphCollections, forms a collection of albums
     * paginated into individual BoxedMorphCollections of no more than 15 morphs
     * each.
     * 
     * @param rawCollections
     * @return a new collection of albums, containing pages with the morphs from
     *         the raw boxed morph collections.
     */
    protected Collection<Album> paginateBoxedMorphCollectionsIntoAlbums(
            Collection<BoxedMorphCollection> rawCollections) {
        Collection<Album> albums = new Vector<Album>();

        for (BoxedMorphCollection rawAlbum : rawCollections) {

            albums.add(paginateBoxedMorphCollectionIntoAlbum(rawAlbum));

        }
        return albums;
    }

    /**
     * Given a Collection of BoxedMorphCollections, and a Collection of those
     * BoxedMorphCollections that are identified as containing only one
     * ("singleton") morph, constructs a new BoxedMorphCollection called
     * "Singleton", containing all the singleton morphs, adds the combined
     * collection of singletons to the master collection of
     * BoxedMorphCollections, and deletes the individual singleton collections.
     * 
     * @param masterCollections
     * @param singletonCollections
     */
    protected void condenseSingletons(
            Collection<BoxedMorphCollection> masterCollections,
            Collection<BoxedMorphCollection> singletonCollections) {
        GridBoxManager boxManager = new GridBoxManager(5);
        BoxedMorphCollection singletonCollection = new BoxedMorphCollection(
                "Singleton", boxManager);
        for (BoxedMorphCollection rawAlbum : singletonCollections) {
            for (BoxedMorph boxedMorph : rawAlbum.getBoxedMorphs()) {
                boxedMorph.setBoxes(boxManager);
                boxManager.addBox(boxedMorph.getBox());
                singletonCollection.add(boxedMorph);
            }
            masterCollections.remove(rawAlbum);
        }
        masterCollections.add(singletonCollection);
    }
}
