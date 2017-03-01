package net.richarddawkins.watchmaker.swing.morphview;

import java.util.Vector;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.MorphViewType;
import net.richarddawkins.watchmaker.swing.album.SwingAlbumMorphView;
import net.richarddawkins.watchmaker.swing.breed.SwingBreedingMorphView;
import net.richarddawkins.watchmaker.swing.engineer.SwingEngineeringMorphView;
import net.richarddawkins.watchmaker.swing.fossil.SwingFossilMorphView;
import net.richarddawkins.watchmaker.swing.pedigree.SwingPedigreeMorphView;
import net.richarddawkins.watchmaker.swing.triangle.SwingTriangleMorphView;

public class SwingMorphViewFactory {
    public static MorphView getMorphView(AppData appData, MorphViewType type, Vector<Morph> seedMorphs) {
        
        SwingMorphViewConfig config = new SwingMorphViewConfig(type);
        config.appData = appData;
        config.geneBoxToSide = appData.isGeneBoxToSide();
        config.seedMorphs = seedMorphs;

        MorphView morphView = null;
        switch(type) {
        case breeding:
            config.name = "Breeding";
            config.icon = "IconFlipBirdToBreedingGrid_ICON_00261_32x32";
            config.album = null;
            morphView = new SwingBreedingMorphView(config);
            break;
        case engineering:
            config.name = "Engineering";
            config.icon = "Hypodermic_PICT_03937_16x16";
            config.engineeringMode = true;
            morphView = new SwingEngineeringMorphView(config);
            break;
        case pedigree:
            config.name = "Pedigree";
            config.icon = "IconPedigree_ALAN_CURS_00147_32x32";
            morphView = new SwingPedigreeMorphView(config);
            break;
        case triangle:
            config.name = "Triangle";
//            config.seedMorphs.addAll(Arrays.asList(appData.getMorphConfig().getTriangleMorphs()));
            morphView = new SwingTriangleMorphView(config);
            break;
        case album:
            config.name = "Album";
            config.icon = "IconAlbum_ALAN_32x32";
            morphView = new SwingAlbumMorphView(config);
            break;
        case fossil:
            config.name = "Fossil Record";
            morphView = new SwingFossilMorphView(config);
            break;
        }
        
        
        return morphView;
    }

}
