package net.richarddawkins.watchmaker.swing.morphview;

import java.util.Vector;

import net.richarddawkins.watchmaker.album.Album;
import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.menu.MenuBuilder;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.MorphViewConfig;
import net.richarddawkins.watchmaker.morphview.MorphViewFactory;
import net.richarddawkins.watchmaker.morphview.MorphViewType;
import net.richarddawkins.watchmaker.swing.album.SwingAlbumMorphView;
import net.richarddawkins.watchmaker.swing.album.menu.SwingAlbumMenuBuilder;
import net.richarddawkins.watchmaker.swing.album.menu.SwingAlbumMorphViewMenuBuilder;
import net.richarddawkins.watchmaker.swing.array.SwingArrayMorphView;
import net.richarddawkins.watchmaker.swing.array.menu.SwingArrayMenuBuilder;
import net.richarddawkins.watchmaker.swing.array.menu.SwingArrayMorphViewMenuBuilder;
import net.richarddawkins.watchmaker.swing.breed.SwingBreedingMorphView;
import net.richarddawkins.watchmaker.swing.breed.menu.SwingBreedingMenuBuilder;
import net.richarddawkins.watchmaker.swing.breed.menu.SwingBreedingMorphViewMenuBuilder;
import net.richarddawkins.watchmaker.swing.drift.SwingDriftMorphView;
import net.richarddawkins.watchmaker.swing.drift.menu.SwingDriftMenuBuilder;
import net.richarddawkins.watchmaker.swing.drift.menu.SwingDriftMorphViewMenuBuilder;
import net.richarddawkins.watchmaker.swing.driftsweep.SwingDriftSweepMorphView;
import net.richarddawkins.watchmaker.swing.driftsweep.menu.SwingDriftSweepMenuBuilder;
import net.richarddawkins.watchmaker.swing.driftsweep.menu.SwingDriftSweepMorphViewMenuBuilder;
import net.richarddawkins.watchmaker.swing.engineer.SwingEngineeringMorphView;
import net.richarddawkins.watchmaker.swing.engineer.menu.SwingEngineeringMenuBuilder;
import net.richarddawkins.watchmaker.swing.engineer.menu.SwingEngineeringMorphViewMenuBuilder;
import net.richarddawkins.watchmaker.swing.fossil.SwingFossilRecordMorphView;
import net.richarddawkins.watchmaker.swing.fossil.menu.SwingFossilRecordMenuBuilder;
import net.richarddawkins.watchmaker.swing.fossil.menu.SwingFossilRecordMorphViewMenuBuilder;
import net.richarddawkins.watchmaker.swing.pedigree.SwingPedigreeMorphView;
import net.richarddawkins.watchmaker.swing.pedigree.menu.SwingPedigreeMenuBuilder;
import net.richarddawkins.watchmaker.swing.pedigree.menu.SwingPedigreeMorphViewMenuBuilder;
import net.richarddawkins.watchmaker.swing.triangle.SwingTriangleMorphView;
import net.richarddawkins.watchmaker.swing.triangle.menu.SwingTriangleMenuBuilder;

public class SwingMorphViewFactory implements MorphViewFactory {

    @Override
    public MorphView getMorphView(AppData appData, String typeName,
            Vector<Morph> seedMorphs, Album album) {
        MorphViewType type = MorphViewType.getByName(typeName);
        MorphViewConfig config = new MorphViewConfig(type);
        config.appData = appData;
        config.geneBoxToSide = appData.isGeneBoxToSide();
        config.seedMorphs = seedMorphs;
        config.album = album;
        MorphView morphView = null;
        switch (type) {
        case breeding:
            config.name = "Breeding";
            config.icon = "IconFlipBirdToBreedingGrid_ICON_00261_32x32";
            config.album = null;
            morphView = new SwingBreedingMorphView(config);
            break;
        case engineering:
            config.name = "Engineering";
            config.icon = "IconEngineering_ALAN_32x32";
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
            // config.seedMorphs.addAll(Arrays.asList(appData.getMorphConfig().getTriangleMorphs()));
            config.icon = "IconTriangle_ALAN_32x32";
            morphView = new SwingTriangleMorphView(config);
            break;
        case album:
            config.name = (album == null ? "" : album.getName() + " ")
                    + "Album";
            config.icon = "IconAlbum_ALAN_32x32";
            config.seedMorphs = null;
            config.indexed = true;
            morphView = new SwingAlbumMorphView(config);
            break;
        case fossil:
            config.name = "Fossil Record";
            config.icon = "IconFossilRecord_ALAN_32x32";
            morphView = new SwingFossilRecordMorphView(config);
            break;
        case array:
            morphView = new SwingArrayMorphView(config);
            break;
        case drift:
            morphView = new SwingDriftMorphView(config);
            break;
        case drift_sweep:
            morphView = new SwingDriftSweepMorphView(config);
            break;
        }

        return morphView;
    }

    @Override
    public Vector<String> getMorphViewTypes() {
        Vector<String> morphViewTypes = new Vector<String>();
        for (MorphViewType type : MorphViewType.values()) {
            morphViewTypes.add(type.getName());
        }
        return morphViewTypes;
    }

    @Override
    public MenuBuilder getMenuBuilder(String typeName) {
        MorphViewType type = MorphViewType.getByName(typeName);
        MenuBuilder menuBuilder =  null;
        switch (type) {
        case breeding:
            menuBuilder = new SwingBreedingMenuBuilder();
            break;
        case engineering:
            menuBuilder = new SwingEngineeringMenuBuilder();
            break;
        case pedigree:
            menuBuilder = new SwingPedigreeMenuBuilder();
            break;
        case triangle:
            menuBuilder = new SwingTriangleMenuBuilder();
            break;
        case album:
            menuBuilder = new SwingAlbumMenuBuilder();
            break;
        case fossil:
            menuBuilder = new SwingFossilRecordMenuBuilder();
            break;
        case array:
            menuBuilder = new SwingArrayMenuBuilder();
            break;
        case drift:
            menuBuilder = new SwingDriftMenuBuilder();
            break;
        case drift_sweep:
            menuBuilder = new SwingDriftSweepMenuBuilder();
            break;      
        }
        return menuBuilder;
    }

    @Override
    public MenuBuilder getMorphViewMenuBuilder(String typeName) {
        MorphViewType type = MorphViewType.getByName(typeName);
        MenuBuilder menuBuilder =  null;
        switch (type) {
        case breeding:
            menuBuilder = new SwingBreedingMorphViewMenuBuilder();
            break;
        case engineering:
            menuBuilder = new SwingEngineeringMorphViewMenuBuilder();
            break;
        case pedigree:
            menuBuilder = new SwingPedigreeMorphViewMenuBuilder();
            break;
        case triangle:
            menuBuilder = new SwingFossilRecordMorphViewMenuBuilder();
            break;
        case album:
            menuBuilder = new SwingAlbumMorphViewMenuBuilder();
            break;
        case fossil:
            menuBuilder = new SwingFossilRecordMorphViewMenuBuilder();
            break;
        case array:
            menuBuilder = new SwingArrayMorphViewMenuBuilder();
            break;
        case drift:
            menuBuilder = new SwingDriftMorphViewMenuBuilder();
            break;
        case drift_sweep:
            menuBuilder = new SwingDriftSweepMorphViewMenuBuilder();
            break;      
        }
        return menuBuilder;
    }

}
