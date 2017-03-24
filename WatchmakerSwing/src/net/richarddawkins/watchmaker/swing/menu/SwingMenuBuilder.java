package net.richarddawkins.watchmaker.swing.menu;

import java.util.Set;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.geom.WatchmakerColor;
import net.richarddawkins.watchmaker.menu.MenuBuilder;
import net.richarddawkins.watchmaker.menu.SimpleMenuBuilder;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;

public abstract class SwingMenuBuilder extends SimpleMenuBuilder implements MenuBuilder {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;


    public SwingMenuBuilder(AppData appData) {
        
        super(appData);
    }



    /**
     * Edit (Undo | Cut, Copy, Paste, Clear | Highlight Biomorph, Add Biomorph
     * to Album, Show Album)
     * 
     * @return
     */
    protected WatchmakerMenu buildEditMenu() {
        WatchmakerMenu menu = new SwingWatchmakerMenu("Edit");
        menu.add(new ActionUndo());
        menu.add(new ActionRedo());
        menu.addSeparator();
        menu.add(new SwingWatchmakerMenuItem("Cut"));
       
        menu.add(new SwingWatchmakerMenuItem("Paste"));
        menu.add(new SwingWatchmakerMenuItem("Clear"));
        menu.add(new SwingWatchmakerMenuItem("Select All"));
        menu.add(new SwingWatchmakerMenuItem("Show Clipboard"));


        return menu;
    }

    /**
     * File (Load to Album..., Load as Fossils..., Save Biomorph..., Save
     * Fossils..., Save Album..., Close Album, Quit)
     * 
     * @return the new File Menu
     */
    protected WatchmakerMenu buildFileMenu() {
        WatchmakerMenu menu = new SwingWatchmakerMenu("File");
//
//        // menu.add(new SwingWatchmakerMenuItem("Load to Album..."));
//        // menu.add(new SwingWatchmakerMenuItem("Load as Fossils"));
//        // menu.add(new SwingWatchmakerMenuItem("Save Biomorph..."));
//        // menu.add(new SwingWatchmakerMenuItem("Save Fossils..."));
//        // menu.add(new SwingWatchmakerMenuItem("Save Album..."));
//        // menu.add(new SwingWatchmakerMenuItem("Close Album"));
//        menu.addSeparator();
//        menu.add(new ActionAlbumNew(appData));
//        menu.add(new ActionAlbumOpen(appData));
//        menu.add(new ActionAlbumSave(appData));
//        menu.add(new ActionAlbumSaveAs(appData));
//        menu.add(new ActionAlbumDelete(appData));
//        menu.add(new ActionAlbumExport(appData));
//        menu.addSeparator();
//        menu.add(new ActionStartTimer(appData));
//        menu.add(new ActionStopTimer(appData));

        return menu;
    }
    protected WatchmakerMenu buildPaletteMenu() {
        WatchmakerMenu paletteMenu = new SwingWatchmakerMenu("Palettes");
        Set<String> paletteNames = WatchmakerColor.getInstance().getPalettes()
                .keySet();
        for (String paletteName : paletteNames) {
            paletteMenu.add(new ActionSwitchPalette(paletteName));
        }
        return paletteMenu;

    }




    /**
     * * View (More Rows, Fewer Rows, More Columns, Fewer Columns, Thicker Pen,
     * Thinner Pen, Drift Sweep, Make top of triangle, Make left of triangle,
     * Make right of triangle)
     * 
     * @return the new View menu. Subclasses may add their own view menu items
     *         by a overriding this method and then calling it with
     *         super.buildViewMenu().
     */

    protected WatchmakerMenu buildViewMenu() {
        WatchmakerMenu menu = new SwingWatchmakerMenu("View");
        menu.add(new SwingWatchmakerMenuItem("More Rows"));
        menu.add(new SwingWatchmakerMenuItem("Fewer Rows"));
        menu.add(new SwingWatchmakerMenuItem("More Columns"));
        menu.add(new SwingWatchmakerMenuItem("Fewer Columns"));
        menu.add(new SwingWatchmakerMenuItem("Thicker Pen"));
        menu.add(new SwingWatchmakerMenuItem("Thinner Pen"));


        return menu;
    }



}
