package net.richarddawkins.watchmaker.swing.pedigree.menu;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.cursor.WatchmakerCursor;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;
import net.richarddawkins.watchmaker.morphview.pedigree.MirrorType;
import net.richarddawkins.watchmaker.morphview.pedigree.PedigreeMorphView;
import net.richarddawkins.watchmaker.morphview.pedigree.PedigreeMorphViewMenuBuilder;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerCheckBoxMenuItem;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenu;
import net.richarddawkins.watchmaker.swing.pedigree.SwingPedigreeMorphView;
import net.richarddawkins.watchmaker.swing.zoo.SwingMultiMorphTypeTabbedPanel;

public class SwingPedigreeMorphViewMenuBuilder extends PedigreeMorphViewMenuBuilder {

    public SwingPedigreeMorphViewMenuBuilder() {

    }

    @Override
    public void buildMenu(WatchmakerMenuBar menuBar) {
        WatchmakerMenu menu = new SwingWatchmakerMenu("Pedigree");
        menu.add(drawOutOffspring);
        menu.add(noMirrors);
        menu.add(singleMirror);
        menu.add(doubleMirrors);
        menu.addSeparator();
        menu.add(move);
        menu.add(detach);
        menu.add(kill);
        menuBar.add(menu);
    }

    @Override
    public void groupMirrors() {
        ButtonGroup group = new ButtonGroup();
        group.add((AbstractButton) noMirrors);
        group.add((AbstractButton) singleMirror);
        group.add((AbstractButton) doubleMirrors);
    }

    
    @Override
    public void createDrawOutOffspring() {
        // WatchmakerAction drawOutOffspring = new
        // ActionPedigreeDrawOutOffspring(appData);
        this.drawOutOffspring = new SwingWatchmakerCheckBoxMenuItem(
                new SwingWatchmakerAction("Draw Out Offspring") {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        appData = SwingMultiMorphTypeTabbedPanel.getInstance()
                                .getSelectedAppData();
                        MorphView morphView = appData.getSelectedMorphView();
                        if (morphView instanceof PedigreeMorphView) {
                            ((Component) morphView.getPanels().firstElement()
                                    .getPanel()).setCursor((Cursor) appData
                                            .getWatchmakerCursorFactory()
                                            .getCursor(
                                                    WatchmakerCursor.pedigree));
                        }
                    }

                });
    }

    @Override
    public void createMove() {
        // WatchmakerAction move = new ActionPedigreeMove(appData);
        this.move = new SwingWatchmakerCheckBoxMenuItem(
                new SwingWatchmakerAction("Move") {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        appData = SwingMultiMorphTypeTabbedPanel.getInstance()
                                .getSelectedAppData();
                        MorphView morphView = appData.getSelectedMorphView();
                        if (morphView instanceof SwingPedigreeMorphView) {
                            MorphViewPanel mvp = morphView.getPanels()
                                    .firstElement();
                            JPanel wp = (JPanel) mvp.getPanel();
                            wp.setCursor((Cursor) appData
                                    .getWatchmakerCursorFactory()
                                    .getCursor(WatchmakerCursor.move));
                        }
                    }

                });

    }

    @Override
    public void createDetach() {
        // WatchmakerAction detach = new ActionPedigreeDetach(appData);
        this.detach = new SwingWatchmakerCheckBoxMenuItem(
                new SwingWatchmakerAction("Detach") {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        appData = SwingMultiMorphTypeTabbedPanel.getInstance()
                                .getSelectedAppData();
                        MorphView morphView = appData.getSelectedMorphView();
                        if (morphView instanceof SwingPedigreeMorphView) {
                            ((Component) morphView.getPanels().firstElement()
                                    .getPanel()).setCursor((Cursor) appData
                                            .getWatchmakerCursorFactory()
                                            .getCursor(
                                                    WatchmakerCursor.detach));
                        }
                    }
                });

    }

    @Override
    public void createKill() {
        // WatchmakerAction kill = new ActionPedigreeKill(appData);
        this.kill = new SwingWatchmakerCheckBoxMenuItem(
                new SwingWatchmakerAction("Kill") {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        appData = SwingMultiMorphTypeTabbedPanel.getInstance()
                                .getSelectedAppData();
                        MorphView morphView = appData.getSelectedMorphView();
                        if (morphView instanceof SwingPedigreeMorphView) {
                            ((Component) morphView.getPanels().firstElement()
                                    .getPanel()).setCursor((Cursor) appData
                                            .getWatchmakerCursorFactory()
                                            .getCursor(WatchmakerCursor.kill));
                        }
                    }

                });

    }


    @Override
    public void createNoMirrors() {
        noMirrors = new SwingWatchmakerCheckBoxMenuItem(
                new SwingWatchmakerAction("No Mirrors") {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        appData = SwingMultiMorphTypeTabbedPanel.getInstance().getSelectedAppData();
                        MorphView morphView = appData.getSelectedMorphView();
                        if (morphView instanceof PedigreeMorphView) {
                            ((PedigreeMorphView) morphView)
                                    .setMirrorType(MirrorType.NONE);
                        }

                    }

                });

    }

    @Override
    public void createSingleMirrors() {
        singleMirror = new SwingWatchmakerCheckBoxMenuItem(
                new SwingWatchmakerAction("Single Mirror") {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        appData = SwingMultiMorphTypeTabbedPanel.getInstance().getSelectedAppData();
                        MorphView morphView = appData.getSelectedMorphView();
                        if (morphView instanceof PedigreeMorphView) {
                            ((PedigreeMorphView) morphView)
                                    .setMirrorType(MirrorType.SINGLE);
                        }
                    }

                }

        );

    }

    @Override
    public void createDoubleMirrors() {
        doubleMirrors = new SwingWatchmakerCheckBoxMenuItem(
                new SwingWatchmakerAction("Double Mirrors") {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        appData = SwingMultiMorphTypeTabbedPanel.getInstance().getSelectedAppData();
                        MorphView morphView = appData.getSelectedMorphView();
                        if (morphView instanceof PedigreeMorphView) {
                            ((PedigreeMorphView) morphView)
                                    .setMirrorType(MirrorType.DOUBLE);
                        }

                    }

                }

        );
    }

    @Override
    public void groupActions() {
        ButtonGroup group = new ButtonGroup();
        group.add((AbstractButton) drawOutOffspring);
        group.add((AbstractButton) move);
        group.add((AbstractButton) detach);
        group.add((AbstractButton) kill);

    }
}
