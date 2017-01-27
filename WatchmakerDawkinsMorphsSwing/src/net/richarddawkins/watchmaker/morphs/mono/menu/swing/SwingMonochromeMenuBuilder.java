package net.richarddawkins.watchmaker.morphs.mono.menu.swing;

import javax.swing.ButtonGroup;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.swing.menu.SwingActionBreed;
import net.richarddawkins.watchmaker.swing.menu.ActionEngineering;
import net.richarddawkins.watchmaker.swing.menu.SwingMenuBuilder;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenu;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenuItem;

/**
 * Application (About Blind Watchmaker) File (Load to Album..., Load as
 * Fossils..., Save Biomorph..., Save Fossils..., Save Album..., Close Album,
 * Quit) Edit (Undo | Cut, Copy, Paste, Clear | Highlight Biomorph, Add Biomorph
 * to Album, Show Album) Operation (Breed, Drift, Engineering, Hopeful Monster,
 * Initialize Fossil Record, Play Back Fossils, Recording Fossils, Triangle)
 * View (More Rows, Fewer Rows, More Columns, Fewer Columns, Thicker Pen,
 * Thinner Pen, Drift Sweep, Make top of triangle, Make left of triangle, Make
 * right of triangle) Mutations (Segmentation, Gradient, Asymmetry, Radial Sym
 * Scaling Factor, Mutation Size, Mutation Rate, Tapering Twigs) Pedigree
 * (Display Pedigree | Draw Out Offspring | No Mirrors, Single Mirror , Double
 * Mirrors | Move, Detach, Kill) Help (Help with current operation ,
 * Miscellaneous Help)
 * 
 * @author alan
 *
 */
public class SwingMonochromeMenuBuilder extends SwingMenuBuilder {

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	SwingWatchmakerMenuItem segmentation = new SwingWatchmakerMenuItem("Segmentation");
	SwingWatchmakerMenuItem gradient = new SwingWatchmakerMenuItem("Gradient");
	SwingWatchmakerMenuItem asymmetry = new SwingWatchmakerMenuItem("Asymmetry");
	SwingWatchmakerMenuItem radialSym = new SwingWatchmakerMenuItem("Radial Sym");
	SwingWatchmakerMenuItem scalingFactor = new SwingWatchmakerMenuItem("Scaling Factor");
	SwingWatchmakerMenuItem mutationSize = new SwingWatchmakerMenuItem("Mutation Size");
	SwingWatchmakerMenuItem mutationRate = new SwingWatchmakerMenuItem("Mutation Rate");
	SwingWatchmakerMenuItem taperingTwigs = new SwingWatchmakerMenuItem("Tapering Twigs");
	SwingWatchmakerMenuItem noMirrors = new SwingWatchmakerMenuItem("No Mirrors");
	SwingWatchmakerMenuItem singleMirror = new SwingWatchmakerMenuItem("Single Mirror");
	SwingWatchmakerMenuItem doubleMirrors = new SwingWatchmakerMenuItem("Double Mirrors");
	SwingWatchmakerMenuItem recordingFossils = new SwingWatchmakerMenuItem("Recording Fossils");
	SwingWatchmakerMenuItem driftSweep = new SwingWatchmakerMenuItem("Drift Sweep");

	public SwingMonochromeMenuBuilder(AppData appData) {
		super(appData);
		ButtonGroup group = new ButtonGroup();
		group.add(noMirrors);
		group.add(singleMirror);
		group.add(doubleMirrors);
	}

	public void buildMenu(WatchmakerMenuBar menuBar) {
		super.buildMenu(menuBar);
		menuBar.add(buildFileMenu());
		menuBar.add(buildEditMenu());
		menuBar.add(buildOperationMenu());
		menuBar.add(buildViewMenu());
		menuBar.add(buildMutationsMenu());
		menuBar.add(buildPedigreeMenu());
		menuBar.add(buildHelpMenu());
		menuBar.repaint();

	}

	/**
	 * Help (Help with current operation , Miscellaneous Help)
	 * 
	 * @return
	 */
	private WatchmakerMenu buildHelpMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("Help");
		menu.add(new SwingWatchmakerMenuItem("Help with current operation"));
		menu.add(new SwingWatchmakerMenuItem("Miscellaneous Help"));
		menu.add(new SwingWatchmakerMenuItem(new AboutMonochromeAction(appData.getFrame())));
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
		menu.add(new SwingWatchmakerMenuItem("Display Pedigree"));
		menu.addSeparator();
		menu.add(new SwingWatchmakerMenuItem("Draw Out Offspring"));
		menu.add(noMirrors);
		menu.add(singleMirror);
		menu.add(doubleMirrors);
		menu.addSeparator();
		menu.add(new SwingWatchmakerMenuItem("Move"));
		menu.add(new SwingWatchmakerMenuItem("Detach"));
		menu.add(new SwingWatchmakerMenuItem("Kill"));
		return menu;
	}

	/**
	 * * View (More Rows, Fewer Rows, More Columns, Fewer Columns, Thicker Pen,
	 * Thinner Pen, Drift Sweep, Make top of triangle, Make left of triangle,
	 * Make right of triangle)
	 * 
	 * @return
	 */
	private WatchmakerMenu buildViewMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("View");
		menu.add(new SwingWatchmakerMenuItem("More Rows"));
		menu.add(new SwingWatchmakerMenuItem("Fewer Rows"));
		menu.add(new SwingWatchmakerMenuItem("More Columns"));
		menu.add(new SwingWatchmakerMenuItem("Fewer Columns"));
		menu.add(new SwingWatchmakerMenuItem("Thicker Pen"));
		menu.add(new SwingWatchmakerMenuItem("Thinner Pen"));
		menu.add(new SwingWatchmakerMenuItem("Drift Sweep"));
		menu.add(new SwingWatchmakerMenuItem("Make top of triangle"));
		menu.add(new SwingWatchmakerMenuItem("Make left of triangle"));
		menu.add(new SwingWatchmakerMenuItem("Make right of triangle"));
		menu.add(viewBoundingBoxes);
		menu.add(spinBabyMorphs);

		return menu;
	}

	/**
	 * Operation (Breed, Drift, Engineering, Hopeful Monster, Initialize Fossil
	 * Record, Play Back Fossils, Recording Fossils, Triangle)
	 * 
	 * @return
	 */
	private WatchmakerMenu buildOperationMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("Operation");
		menu.add(new SwingActionBreed(appData));
		menu.add(new SwingWatchmakerMenuItem("Drift"));
		menu.add(new ActionEngineering(appData));

		menu.add(new SwingWatchmakerMenuItem("Hopeful Monster"));
		menu.add(new SwingWatchmakerMenuItem("Initialize Fossil Record"));
		menu.add(new SwingWatchmakerMenuItem("Play Back Fossils"));
		menu.add(new SwingWatchmakerMenuItem("Recording Fossils"));
		menu.add(new ActionTriangle(appData));
		return menu;
	}

	/**
	 * Edit (Undo | Cut, Copy, Paste, Clear | Highlight Biomorph, Add Biomorph
	 * to Album, Show Album)
	 * 
	 * @return
	 */
	private WatchmakerMenu buildEditMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("Edit");
		menu.add(new SwingWatchmakerMenuItem("Undo"));
		menu.addSeparator();
		menu.add(new SwingWatchmakerMenuItem("Cut"));
		menu.add(new SwingWatchmakerMenuItem("Copy"));
		menu.add(new SwingWatchmakerMenuItem("Paste"));
		menu.add(new SwingWatchmakerMenuItem("Clear"));
		menu.addSeparator();
		menu.add(new SwingWatchmakerMenuItem("Highlight Biomorph"));
		menu.add(new SwingWatchmakerMenuItem("Add Biomorph to Album"));
		menu.add(new SwingWatchmakerMenuItem("Show Album"));
		return menu;
	}

	/**
	 * File (Load to Album..., Load as Fossils..., Save Biomorph..., Save
	 * Fossils..., Save Album..., Close Album, Quit)
	 * 
	 * @return the new File Menu
	 */
	public WatchmakerMenu buildFileMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("File");
		menu.add(new SwingWatchmakerMenuItem("Load to Album..."));
		menu.add(new SwingWatchmakerMenuItem("Load as Fossils"));
		menu.add(new SwingWatchmakerMenuItem("Save Biomoprh..."));
		menu.add(new SwingWatchmakerMenuItem("Save Fossils..."));
		menu.add(new SwingWatchmakerMenuItem("Save Album..."));
		menu.add(new SwingWatchmakerMenuItem("Close Album"));

		return menu;
	}

	/**
	 * Build mutations menu
	 * 
	 * @return the new Mutations menu
	 */
	public WatchmakerMenu buildMutationsMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("Mutations");
		menu.add(segmentation);
		menu.add(gradient);
		menu.add(asymmetry);
		menu.add(radialSym);
		menu.add(scalingFactor);
		menu.add(mutationSize);
		menu.add(mutationRate);
		menu.add(taperingTwigs);
		return menu;

	}

}
