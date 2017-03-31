package net.richarddawkins.watchmaker.morph.draw;

import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.album.Album;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.LocatedMorph;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.Pedigree;

public class BoxedMorphCollection {
    @SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.morph.draw.BoxedMorphVector");

    protected Album album;

    protected Vector<BoxedMorph> boxedMorphs = new Vector<BoxedMorph>();

    protected BoxManager boxes;
    protected String name;
    protected BoxedMorph selectedBoxedMorph = null;


    /**
     * 
     * Calls BoxManager.getBoxContainingPoint() to find the box at the given point
     * with the given dimensions for the region, then calls getBoxedMorph with
     * the box to return the BoxedMorph inside it (if any.)
     * @param p a point inside the region managed by the BoxedMorphCollection's BoxManager
     * @param d the dimensions of the region managed by the BoxedMorphCollection's BoxManager
     * @return the box containing the BoxedMorph
     */
    public BoxedMorph getBoxedMorph(Point p, Dim d) {
        BoxedMorph boxedMorph = null;
        Rect box = boxes.getBoxContainingPoint(p, d);
        if(box != null) {
            boxedMorph = this.getBoxedMorph(box);
        }
        return boxedMorph;
    }
    
    
    public BoxedMorphCollection() {

    }

    public BoxedMorphCollection(String name, BoxManager boxes) {
        this.name = name;
        this.boxes = boxes;
    }

    public void setAlbumDirty(boolean dirty) {
        if (album != null) {
            album.setDirty(true);
        }
    }

    public void add(BoxedMorph boxedMorph) {
        boxedMorphs.add(boxedMorph);
        setAlbumDirty(true);
    }

    public void add(int index, BoxedMorph boxedNewestOffspring) {
        boxedMorphs.add(index, boxedNewestOffspring);
        setAlbumDirty(true);
    }

    public void clear() {
        boxedMorphs.clear();
        setAlbumDirty(true);
    }

    public BoxedMorph findBoxedMorphForMorph(Morph morph) {
        for (BoxedMorph boxedMorph : boxedMorphs) {
            if (boxedMorph.getMorph() == morph) {
                return boxedMorph;
            }
        }
        return null;
    }

    public Vector<BoxedMorph> findBoxedMorphsForMorphAndDescendents(
            BoxedMorph boxedMorph) {

        Vector<BoxedMorph> boxedMorphAndDescendents = new Vector<BoxedMorph>();
        if (boxedMorph != null) {
            boxedMorphAndDescendents.add(boxedMorph);
            Morph parentMorph = boxedMorph.getMorph();
            Pedigree pedigree = parentMorph.getPedigree();
            Morph child = pedigree.firstBorn;
            while (child != null) {
                BoxedMorph childBoxedMorph = findBoxedMorphForMorph(child);
                if (childBoxedMorph != null) {
                    if (child.getPedigree().parent != null) {
                        boxedMorphAndDescendents
                                .addAll(findBoxedMorphsForMorphAndDescendents(
                                        childBoxedMorph));

                    }
                    child = childBoxedMorph.getMorph().getPedigree().youngerSib;
                } else {
                    child = null;
                }
            }
        }
        return boxedMorphAndDescendents;
    }

    public LocatedMorph firstElement() {

        return boxedMorphs.firstElement();
    }

    public boolean genomicallyEquals(BoxedMorphCollection those) {
        Vector<BoxedMorph> thoseMorphs = those.getBoxedMorphs();
        if (thoseMorphs.size() != boxedMorphs.size()) {
            return false;
        }
        Iterator<BoxedMorph> theseBoxedMorphs = boxedMorphs.iterator();
        Iterator<BoxedMorph> thoseBoxedMorphs = thoseMorphs.iterator();
        while (theseBoxedMorphs.hasNext()) {
            if (!theseBoxedMorphs.next()
                    .genomicallyEquals(thoseBoxedMorphs.next())) {
                return false;
            }
        }
        return true;
    }

    public Album getAlbum() {
        return album;
    }

    public BoxedMorph getBoxedMorph(Rect box) {
        for (BoxedMorph boxedMorph : boxedMorphs)
            if (boxedMorph.getBox() == box)
                return boxedMorph;
        return null;
    }

    public Vector<BoxedMorph> getBoxedMorphs() {
        return boxedMorphs;
    }

    public BoxManager getBoxManager() {
        return boxes;
    }

    public Vector<Morph> getMorphs() {
        Vector<Morph> morphs = new Vector<Morph>();
        for (BoxedMorph boxedMorph : boxedMorphs) {
            morphs.add(boxedMorph.getMorph());
        }
        return morphs;
    }

    public String getName() {
        return name;
    }

    public BoxedMorph getSelectedBoxedMorph() {
        return selectedBoxedMorph;
    }

    public boolean isEmpty() {
        return boxedMorphs.isEmpty();
    }

    public Iterator<BoxedMorph> iterator() {
        return boxedMorphs.iterator();
    }

    public BoxedMorph lastElement() {
        return boxedMorphs.lastElement();
    }

    public void moveToEnd(BoxedMorph boxedMorph) {
        synchronized (boxedMorphs) {
            boxedMorphs.remove(boxedMorph);
            boxedMorphs.add(boxedMorph);
            boxes.moveToEnd(boxedMorph.getBox());
        }
        setAlbumDirty(true);
    }

    public void remove(BoxedMorph boxedMorphVictim) {
        if (selectedBoxedMorph == boxedMorphVictim) {
            selectedBoxedMorph = null;
        }
//        boxes.removeBox(boxedMorphVictim.getBox());
        boxedMorphs.remove(boxedMorphVictim);
        setAlbumDirty(true);
    }

    public void removeAllElements() {
        selectedBoxedMorph = null;
        boxedMorphs.removeAllElements();

        setAlbumDirty(true);
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void setBoxedMorphs(Vector<BoxedMorph> boxedMorphs) {
        this.boxedMorphs = boxedMorphs;
    }

    public void setBoxManager(BoxManager boxes) {
        this.boxes = boxes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSelectedBoxedMorph(BoxedMorph selectedBoxedMorph) {
        this.selectedBoxedMorph = selectedBoxedMorph;
    }

    public int size() {
        return boxedMorphs.size();
    }

    public void removeAndKillAllBut(BoxedMorph boxedMorphParent) {
        Vector<BoxedMorph> theDamned = new Vector<BoxedMorph>();
        for (BoxedMorph boxedMorph : boxedMorphs) {
            if (boxedMorphParent != boxedMorph) {
                theDamned.add(boxedMorph);
            }
        }
        for (BoxedMorph upAgainstTheWall : theDamned) {
            this.remove(upAgainstTheWall);
            upAgainstTheWall.kill();
        }

    }

}
