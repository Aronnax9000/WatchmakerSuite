package net.richarddawkins.watchmaker.swing.menu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.app.AppDataFactory;
import net.richarddawkins.watchmaker.app.AppDataFactoryService;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.menu.MenuBuilder;
import net.richarddawkins.watchmaker.menu.WatchmakerCheckBoxMenuItem;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.pedigree.MirrorType;
import net.richarddawkins.watchmaker.pedigree.PedigreeMorphView;
import net.richarddawkins.watchmaker.swing.album.ActionShowAlbum;
import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;

public abstract class SwingMenuBuilder implements MenuBuilder {

    public SwingMenuBuilder(AppData appData) {
        this.appData = appData;
        ButtonGroup group = new ButtonGroup();
        noMirrors = new SwingWatchmakerCheckBoxMenuItem(
                new SwingWatchmakerAction(appData, "No Mirrors") {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MorphView morphView = appData.getMorphViewsTabbedPane().getSelectedMorphView();
                        if(morphView instanceof PedigreeMorphView) {
                            ((PedigreeMorphView) morphView).setMirrorType(MirrorType.NONE);
                        }

                    }

                });

        singleMirror = new SwingWatchmakerCheckBoxMenuItem(
                new SwingWatchmakerAction(appData, "Single Mirror") {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MorphView morphView = appData.getMorphViewsTabbedPane().getSelectedMorphView();
                        if(morphView instanceof PedigreeMorphView) {
                            ((PedigreeMorphView) morphView).setMirrorType(MirrorType.SINGLE);
                        }
                    }

                }

        );

        doubleMirrors = new SwingWatchmakerCheckBoxMenuItem(
                new SwingWatchmakerAction(appData, "Double Mirrors") {

                    /**
                     * 
                     */
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MorphView morphView = appData.getMorphViewsTabbedPane().getSelectedMorphView();
                        if(morphView instanceof PedigreeMorphView) {
                            ((PedigreeMorphView) morphView).setMirrorType(MirrorType.DOUBLE);
                        }

                    }

                }

        );
        
        group.add(noMirrors);
        group.add(singleMirror);
        group.add(doubleMirrors);
        noMirrors.setSelected(true);
        
        
        viewBoundingBoxes = new SwingWatchmakerCheckBoxMenuItem(
                new SwingWatchmakerAction(appData, "View Bounding Boxes") {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        appData.getPhenotypeDrawer().getDrawingPreferences()
                                .setShowBoundingBoxes(
                                        viewBoundingBoxes.isSelected());
                    }
                });
        spinBabyMorphs = new SwingWatchmakerCheckBoxMenuItem(
                new SwingWatchmakerAction(appData, "Spin baby morphs") {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        appData.getPhenotypeDrawer().getDrawingPreferences()
                                .setSpinBabyMorphs(spinBabyMorphs.isSelected());
                    }
                });
        highlightBiomorph = new SwingWatchmakerCheckBoxMenuItem(
                new SwingWatchmakerAction(appData, "Highlight biomorph") {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MorphView selectedMorphView = appData
                                .getMorphViewsTabbedPane()
                                .getSelectedMorphView();
                        JPanel centrePanel = selectedMorphView.getCentrePanel();
                        if (((JCheckBoxMenuItem) highlightBiomorph)
                                .isSelected()) {
                            centrePanel.setCursor(WatchmakerCursors.highlight);
                        } else {
                            centrePanel.setCursor(null);
                            BoxedMorphCollection boxedMorphs = selectedMorphView
                                    .getBoxedMorphVector();
                            if (boxedMorphs.getSelectedBoxedMorph() != null) {
                                boxedMorphs.setSelectedBoxedMorph(null);
                                centrePanel.repaint();
                            }
                        }
                    }
                });
        appData.addPropertyChangeListener("highlighting",
                (PropertyChangeListener) highlightBiomorph);

    };

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;
    protected AppData appData;

    public AppData getAppData() {
        return appData;
    }

    public void setAppData(AppData appData) {
        this.appData = appData;
    }

    protected void addFileQuitAction(WatchmakerMenu menu) {
        menu.add(new SwingWatchmakerMenuItem(new ActionQuit((Component) menu)));
    }

    /**
     * Subclasses should call menuBar.repaint() after adding their own menu
     * items.
     */
    public void buildMenu(WatchmakerMenuBar menuBar) {
        menuBar.removeAll();
        menuBar.add(buildWatchmakerMenu());
        menuBar.add(buildFileMenu());
        menuBar.add(buildEditMenu());
        menuBar.add(buildOperationMenu());
        menuBar.add(buildViewMenu());
        menuBar.add(buildPedigreeMenu());
        menuBar.add(buildHelpMenu());
        menuBar.repaint();

    }

    protected WatchmakerMenu buildHelpMenu() {
        WatchmakerMenu menu = new SwingWatchmakerMenu("Help");
        menu.add(new SwingWatchmakerMenuItem("Help with current operation"));
        menu.add(new SwingWatchmakerMenuItem("Miscellaneous Help"));
        return menu;
    }

    protected WatchmakerMenu buildWatchmakerMenu() {
        WatchmakerMenu watchMakerMenu = new SwingWatchmakerMenu("Watchmaker");
        AppDataFactory factory = AppDataFactoryService.getInstance()
                .getFactory();
        for (String morphType : factory.getMorphTypes()) {
            factory.setMorphType(morphType);
            NewMorphTypeAction morphTypeAction = new NewMorphTypeAction(appData,
                    morphType, (Icon) factory.getIcon());
            // if (morphType.equals("Snails"))
            // morphTypeAction.setEnabled(false);
            watchMakerMenu.add(morphTypeAction);
        }
        addFileQuitAction(watchMakerMenu);
        return watchMakerMenu;
    }

    protected WatchmakerCheckBoxMenuItem viewBoundingBoxes;
    protected WatchmakerCheckBoxMenuItem highlightBiomorph;
    protected WatchmakerCheckBoxMenuItem spinBabyMorphs;

    /**
     * Edit (Undo | Cut, Copy, Paste, Clear | Highlight Biomorph, Add Biomorph
     * to Album, Show Album)
     * 
     * @return
     */
    protected WatchmakerMenu buildEditMenu() {
        WatchmakerMenu menu = new SwingWatchmakerMenu("Edit");
        menu.add(new ActionUndo(appData));
        menu.add(new ActionRedo(appData));
        menu.addSeparator();
        menu.add(new SwingWatchmakerMenuItem("Cut"));
        menu.add(new ActionCopy(appData));
        menu.add(new SwingWatchmakerMenuItem("Paste"));
        menu.add(new SwingWatchmakerMenuItem("Clear"));
        menu.add(new SwingWatchmakerMenuItem("Select All"));
        menu.add(new SwingWatchmakerMenuItem("Show Clipboard"));
        menu.addSeparator();
        menu.add(highlightBiomorph);

        menu.add(new SwingWatchmakerMenuItem("Add Biomorph to Album"));
        menu.add(new ActionShowAlbum(appData));
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
        menu.add(new SwingWatchmakerMenuItem("Load to Album..."));
        menu.add(new SwingWatchmakerMenuItem("Load as Fossils"));
        menu.add(new SwingWatchmakerMenuItem("Save Biomoprh..."));
        menu.add(new SwingWatchmakerMenuItem("Save Fossils..."));
        menu.add(new SwingWatchmakerMenuItem("Save Album..."));
        menu.add(new SwingWatchmakerMenuItem("Close Album"));

        return menu;
    }

    protected WatchmakerMenu buildOperationMenu() {
        WatchmakerMenu menu = new SwingWatchmakerMenu("Operation");
        menu.add(new SwingActionBreed(appData));
        menu.add(new SwingWatchmakerMenuItem("Drift"));
        menu.add(new ActionEngineering(appData));
        menu.add(new ActionTriangle(appData));

        Icon newRandomStartIcon = new ImageIcon(ClassicImageLoader
                .getPicture("SixSidedDieShowsFiveIcon_ICON_00257_32x32")
                .getImage());

        menu.add(new SwingWatchmakerMenuItem(new AbstractAction(
                "Hopeful Monster (New Random Start)", newRandomStartIcon) {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                MorphConfig config = appData.getMorphConfig();
                Morph morph = appData.getMorphOfTheHour();
                Genome genome = config.getGenomeFactory().deliverSaltation();
                morph.setGenome(genome);
                MorphView morphView = appData.getMorphViewsTabbedPane()
                        .getSelectedMorphView();
                morphView.seed(morph);
            }
        }));
        menu.add(new SwingWatchmakerMenuItem("Initialize Fossil Record"));
        menu.add(new SwingWatchmakerMenuItem("Play Back Fossils"));
        menu.add(new SwingWatchmakerMenuItem("Recording Fossils"));

        return menu;
    }

    /**
     * Pedigree (Display pedigree | Draw Out Offspring , No mirrors, Single
     * Mirror , Double Mirrors | Move, Detach, Kill)
     * 
     * @return the Pedigree menu
     */
    public WatchmakerMenu buildPedigreeMenu() {
        WatchmakerMenu menu = new SwingWatchmakerMenu("Pedigree");
        menu.add(new ActionPedigree(appData));
        menu.addSeparator();
        menu.add(new ActionPedigreeDrawOutOffspring(appData));
        menu.add(noMirrors);
        menu.add(singleMirror);
        menu.add(doubleMirrors);
        menu.addSeparator();
        menu.add(new ActionPedigreeMove(appData));
        menu.add(new ActionPedigreeDetach(appData));
        menu.add(new ActionPedigreeKill(appData));
        return menu;
    }

    SwingWatchmakerCheckBoxMenuItem noMirrors;
    SwingWatchmakerCheckBoxMenuItem singleMirror;
    SwingWatchmakerCheckBoxMenuItem doubleMirrors; 
    SwingWatchmakerMenuItem recordingFossils = new SwingWatchmakerMenuItem(
            "Recording Fossils");
    SwingWatchmakerMenuItem driftSweep = new SwingWatchmakerMenuItem(
            "Drift Sweep");

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
        menu.add(new SwingWatchmakerMenuItem("Drift Sweep"));
        menu.add(new ActionTriangleMakeTop(appData));
        menu.add(new ActionTriangleMakeLeft(appData));
        menu.add(new ActionTriangleMakeRight(appData));
        menu.add(viewBoundingBoxes);
        menu.add(spinBabyMorphs);

        return menu;
    }

}
