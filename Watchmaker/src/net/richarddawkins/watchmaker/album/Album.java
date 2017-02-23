package net.richarddawkins.watchmaker.album;

import java.util.Vector;

import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;

public class Album {

    public static final int pageSize = 15;

    public static int MAX_PAGES = 1024;
    
    protected String name;

    protected final Vector<BoxedMorphCollection> pages = new Vector<BoxedMorphCollection>();
     
    public Album(String name) {
        this.name = name;
    }
    
    public void removePage(int index) {
        pages.remove(index);
    }
    
    public Album(String name, Vector<BoxedMorphCollection> seedPages) {
        this(name);
        for(BoxedMorphCollection boxedMorphs: seedPages) {
            addPage(boxedMorphs);
        }
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public BoxedMorphCollection getPage(int index) {
        return pages.elementAt(index);
    }
    public Vector<BoxedMorphCollection> getPages() {
        return pages;
    }
    
    public void addPage(BoxedMorphCollection page) {
        
        
        pages.add(page);
    }
    
    public int size() {
        return pages.size();
    }
    public void removePage(BoxedMorphCollection page) {
        pages.remove(page);
    }
    
    public void clear() {
        pages.clear();
    }

    public int indexOfPage(BoxedMorphCollection boxedMorphVector) {
        return pages.indexOf(boxedMorphVector);
    }

    public BoxedMorphCollection getLastPage() {
        return pages.lastElement();
    }
    
    public void removePagesAfter(BoxedMorphCollection page) {
        int firstVictimIndex = pages.indexOf(page) + 1;
        int nToRemove = pages.size() - firstVictimIndex;
        for(int i = 0; i < nToRemove; i++) {
           pages.remove(pages.lastElement());
        }
    }

    public void addPage(int i, BoxedMorphCollection backupBoxedMorphs) {
        pages.add(i, backupBoxedMorphs);
        
    }
}
