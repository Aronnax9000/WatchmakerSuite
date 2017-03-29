package net.richarddawkins.watchmaker.swing.menu;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.app.AppDataFactory;
import net.richarddawkins.watchmaker.app.AppDataFactoryService;
import net.richarddawkins.watchmaker.cursor.WatchmakerCursor;
import net.richarddawkins.watchmaker.cursor.WatchmakerCursorFactory;
import net.richarddawkins.watchmaker.geom.WatchmakerColor;
import net.richarddawkins.watchmaker.menu.WatchmakerCheckBoxMenuItem;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBuilder;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.swing.zoo.SwingMultiMorphTypeTabbedPanel;

public class SwingWatchmakerMenuBuilder implements WatchmakerMenuBuilder {

    public SwingWatchmakerMenuBuilder() {
        this.createViewBoundingBoxes();
        this.createSpinBabyMorphs();
        this.createHighlightCheckbox();
    }

    protected WatchmakerMenu buildWatchmakerMenu() {
        WatchmakerMenu menu = new SwingWatchmakerMenu("Watchmaker");
        AppDataFactory appDataFactory = AppDataFactoryService.getInstance()
                .getFactory();
        for (String morphType : appDataFactory.getMorphTypes()) {
            appDataFactory.setMorphType(morphType);

            NewMorphTypeAction morphTypeAction = new NewMorphTypeAction(
                    morphType, (Icon) appDataFactory.getIcon());
            menu.add(morphTypeAction);
        }

        addFileQuitAction(menu);
        return menu;
    }

    protected void addFileQuitAction(WatchmakerMenu menu) {
        menu.add(new SwingWatchmakerMenuItem(new ActionQuit((Component) menu)));
    }

    protected WatchmakerMenu buildFileMenu() {
        WatchmakerMenu menu = new SwingWatchmakerMenu("File");
        return menu;
    }

    protected WatchmakerMenu buildEditMenu() {
        WatchmakerMenu menu = new SwingWatchmakerMenu("Edit");
        menu.add(new ActionCopy());
        menu.add(this.highlightBiomorph);
        return menu;
    }

    protected WatchmakerMenu buildOperationMenu() {
        WatchmakerMenu menu = new SwingWatchmakerMenu("Operation");
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



    protected WatchmakerMenu buildViewMenu() {
        WatchmakerMenu menu = new SwingWatchmakerMenu("View");
        menu.add(buildPaletteMenu());
        menu.add(viewBoundingBoxes);
        menu.add(spinBabyMorphs);
        return menu;
    }

    protected WatchmakerCheckBoxMenuItem viewBoundingBoxes;
    protected WatchmakerCheckBoxMenuItem spinBabyMorphs;
    protected WatchmakerCheckBoxMenuItem highlightBiomorph;

    public void createHighlightCheckbox() {
        highlightBiomorph = new SwingWatchmakerCheckBoxMenuItem(
                new SwingWatchmakerAction("Highlight biomorph") {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        AppData appData = SwingMultiMorphTypeTabbedPanel
                                .getInstance().getSelectedAppData();
                        MorphView selectedMorphView = appData
                                .getSelectedMorphView();
                        JPanel centrePanel = (JPanel) selectedMorphView
                                .getPanels().firstElement().getPanel();
                        if (((JCheckBoxMenuItem) highlightBiomorph)
                                .isSelected()) {
                            centrePanel.setCursor((Cursor) appData
                                    .getWatchmakerCursorFactory()
                                    .getCursor(WatchmakerCursor.highlight));
                        } else {
                            centrePanel.setCursor(null);
                            BoxedMorphCollection boxedMorphs = selectedMorphView
                                    .getSelectedPanel()
                                    .getBoxedMorphCollection();
                            if (boxedMorphs.getSelectedBoxedMorph() != null) {
                                boxedMorphs.setSelectedBoxedMorph(null);
                                centrePanel.repaint();
                            }
                        }
                    }
                });

    }

    public void createSpinBabyMorphs() {
        spinBabyMorphs = new SwingWatchmakerCheckBoxMenuItem(
                new SwingWatchmakerAction("Spin baby morphs") {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        getAppData().getPhenotypeDrawer().getDrawingPreferences()
                                .setSpinBabyMorphs(spinBabyMorphs.isSelected());
                    }
                });
    }

    public void createViewBoundingBoxes() {
        viewBoundingBoxes = new SwingWatchmakerCheckBoxMenuItem(
                new SwingWatchmakerAction("View Bounding Boxes") {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        getAppData().getPhenotypeDrawer().getDrawingPreferences()
                                .setShowBoundingBoxes(
                                        viewBoundingBoxes.isSelected());
                    }
                });

    }

    @Override
    public void buildMenu(WatchmakerMenuBar menuBar) {
        menuBar.removeAll();
        menuBar.add(buildWatchmakerMenu());
        menuBar.add(buildFileMenu());
        menuBar.add(buildEditMenu());
        menuBar.add(buildOperationMenu());
        menuBar.add(buildViewMenu());
        menuBar.add(buildHelpMenu());

    }

    @Override
    public void cleanMenu(WatchmakerMenuBar menuBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateMenu(WatchmakerMenuBar menuBar) {
        AppData appData = SwingMultiMorphTypeTabbedPanel.getInstance()
                .getSelectedAppData();
        WatchmakerCursorFactory cursors = appData.getWatchmakerCursorFactory();
        boolean isHighlight = cursors.isCursorType(WatchmakerCursor.highlight,
                appData.getSelectedMorphView().getSelectedPanel().getCursor());
        highlightBiomorph.setSelected(isHighlight);

    }

    protected WatchmakerMenu buildHelpMenu() {
        WatchmakerMenu menu = new SwingWatchmakerMenu("Help");
        menu.add(new SwingWatchmakerMenuItem("Help with current operation"));
        menu.add(new SwingWatchmakerMenuItem("Miscellaneous Help"));
        return menu;
    }

}
