package net.richarddawkins.watchmaker.morphview;

import java.util.Vector;

import net.richarddawkins.watchmaker.album.Album;
import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.menu.MenuBuilder;
import net.richarddawkins.watchmaker.morph.Morph;

public interface MorphViewFactory {
    Vector<String> getMorphViewTypes();

    MorphView getMorphView(AppData appData, String typeName,
            Vector<Morph> seedMorphs, Album album);
    
    MenuBuilder getMorphViewMenuBuilder(String typeName);

    MenuBuilder getMenuBuilder(String typeName);
}
