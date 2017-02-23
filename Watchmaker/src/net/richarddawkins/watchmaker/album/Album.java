package net.richarddawkins.watchmaker.album;

import java.util.Vector;

import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;

public class Album {

    public static final int pageSize = 15;
    
    protected String name;

    protected final Vector<BoxedMorphCollection> pages = new Vector<BoxedMorphCollection>();
     
    public Album(String name) {
        this.name = name;
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


    public Vector<BoxedMorphCollection> getPages() {
        return pages;
    }
    
    public void addPage(BoxedMorphCollection page) {
        page.getBoxes().setAccentuateMidBox(false);
        
        pages.add(page);
    }
    
    public void removePage(BoxedMorphCollection page) {
        pages.remove(page);
    }
    
    public void clear() {
        pages.clear();
    }
    
}
