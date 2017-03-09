package net.richarddawkins.watchmaker.morphview;

import java.util.Vector;

import net.richarddawkins.watchmaker.album.Album;
import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.morph.Morph;

public class MorphViewConfig {
    public MorphViewType type;
    public AppData appData = null;
    public String icon = null;
    public String name = null;
    public boolean engineeringMode = false; 
    public boolean geneBoxToSide = false;
    public boolean copyMorphsOnBackup = false;
    public Album album = null;
    public Vector<Morph> seedMorphs = new Vector<Morph>();
    public MorphViewConfig(MorphViewType type) {
        this.type = type;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(type);
        if(name != null) buffer.append(" " + name);
        if(icon != null) buffer.append(" " + icon);
        if(appData != null) buffer.append(" " + appData);
        if(engineeringMode) buffer.append(" engineering");
        if(geneBoxToSide) buffer.append(" geneBoxToSide");
        if(copyMorphsOnBackup) buffer.append(" copyMorphsOnBackup");
        if(album != null) buffer.append(" " + album);
        
        return buffer.toString();
    }
}
