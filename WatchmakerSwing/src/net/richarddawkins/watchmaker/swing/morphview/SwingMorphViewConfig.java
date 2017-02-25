package net.richarddawkins.watchmaker.swing.morphview;

import java.util.Vector;

import net.richarddawkins.watchmaker.album.Album;
import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphview.MorphViewType;

public class SwingMorphViewConfig {
    public MorphViewType type;
    public AppData appData = null;
    public String icon = null;
    public String name = null;
    public boolean engineeringMode = false; 
    public boolean geneBoxToSide = false;
    public boolean copyMorphsOnBackup = false;
    public Album album = null;
    public Vector<Morph> seedMorphs = new Vector<Morph>();
    public SwingMorphViewConfig(MorphViewType type) {
        this.type = type;
    }
}
