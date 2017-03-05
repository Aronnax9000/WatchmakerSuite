package net.richarddawkins.watchmaker.album;

import java.util.Vector;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;

public class Album {

    public static int MAX_PAGES = 1024;
    public static final int pageSize = 15;
    protected String fileName = null;

    boolean isDirty = false;

    protected String name;

    protected final Vector<BoxedMorphCollection> pages = new Vector<BoxedMorphCollection>();

    private BoxedMorphCollection selectedPage;

    private boolean writable;

    public Album(String name) {
        this.name = name;
    }

    public Album(String name, Vector<BoxedMorphCollection> seedPages) {
        this(name);
        for (BoxedMorphCollection boxedMorphs : seedPages) {
            addPage(boxedMorphs);
        }
    }

    public void addPage(BoxedMorphCollection page) {

        pages.add(page);
        page.setAlbum(this);
    }

    public void addPage(int i, BoxedMorphCollection backupBoxedMorphs) {
        if (i < 0) {
            pages.add(backupBoxedMorphs);
        } else {
            pages.add(i, backupBoxedMorphs);
        }
    }

    public void clear() {
        pages.clear();
    }

    public boolean contains(BoxedMorphCollection boxedMorphCollection) {

        return pages.contains(boxedMorphCollection);
    }

    public BoxedMorphCollection firstElement() {
        return pages.firstElement();
    }

    public String getFileName() {
        return fileName;
    }

    public Morph getFirstMorph() {
        if (!pages.isEmpty()) {
            BoxedMorphCollection page = pages.firstElement();
            if (!page.isEmpty()) {
                return page.firstElement().getMorph();
            }
        }
        return null;
    }

    public BoxedMorphCollection getLastPage() {
        return pages.lastElement();
    }

    public String getName() {
        return name;
    }

    public BoxedMorphCollection getPage(int index) {
        return pages.elementAt(index);
    }

    public Vector<BoxedMorphCollection> getPages() {
        return pages;
    }

    public BoxedMorphCollection getPreviousPage(
            BoxedMorphCollection boxedMorphCollection) {

        return pages.get(pages.indexOf(boxedMorphCollection) - 1);
    }

    public BoxedMorphCollection getSelectedPage() {
        return selectedPage;
    }

    public int indexOfPage(BoxedMorphCollection boxedMorphVector) {
        return pages.indexOf(boxedMorphVector);
    }

    public boolean isDirty() {
        return isDirty;
    }

    public boolean isWritable() {

        return writable;
    }

    public void removePage(BoxedMorphCollection page) {
        pages.remove(page);
    }

    public void removePage(int index) {
        pages.remove(index);
    }

    public void removePagesAfter(BoxedMorphCollection page) {
        int firstVictimIndex = pages.indexOf(page) + 1;
        int nToRemove = pages.size() - firstVictimIndex;
        for (int i = 0; i < nToRemove; i++) {
            pages.remove(pages.lastElement());
        }
    }

    public void setDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWritable(boolean writable) {
        this.writable = writable;
    }

    public int size() {
        return pages.size();
    }

    public void setSelectedPage(BoxedMorphCollection page) {
        this.selectedPage = page;
        
    }
}
