package net.richarddawkins.watchmaker.morphs.concho.swing;

import java.awt.Component;

import javax.swing.ButtonGroup;

import net.richarddawkins.watchmaker.menu.WatchmakerCheckBoxMenuItem;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.swing.app.SwingAppData;
import net.richarddawkins.watchmaker.swing.menu.SwingActionBreed;
import net.richarddawkins.watchmaker.swing.menu.SwingMenuBuilder;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerCheckBoxMenuItem;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenu;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenuItem;

/**
 * Application (About Blind Snailmaker) File (Load to Album..., Load as
 * Fossils..., Save Biomorph..., Save Fossils..., Save Album..., Close Album,
 * Quit) Edit (Undo | Cut, COpy, Paste, Clear | Highlight Biomorph, Add Biomorph
 * to Album, Show Album) Operation (Breed, Drift, Engineering, Hopeful MOnster,
 * Initialize Fossil Record, Play Back Fossils, Recording Fossils, Triangle,
 * Array) (Note: Array is not in Mono) View (More Rows, Fewer Rows, More
 * Columns, Fewer Columns, Change View, Drift Sweep, Make top of triangle) Make
 * left of triangle, Make right of triangle, View Pedigree) Animal (Customise,
 * Snail, Turritella, Bivalve, Ammonite, Nautilus, Brachiopod, Cone, Whelk,
 * Scallop, Eloise, Gallagher's, Rapa, Lightning, Sundial, Fig, Tun, Razor
 * Shell, Japanese Wonder) Pedigree (Start pedigree | Draw Out Offspring , No
 * mirrors, Single Mirror , Double Mirrors | Move, Detach, Kill) Help (Help with
 * current operation , Miscellaneous Help)
 * 
 * @author alan
 *
 */
public class SnailMenuBuilder extends SwingMenuBuilder {

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	private WatchmakerCheckBoxMenuItem recordingFossils 
	= new SwingWatchmakerCheckBoxMenuItem("Recording Fossils");


	public SnailMenuBuilder(SwingAppData swingAppData) {
		super(swingAppData);
	}

	public void buildMenu(WatchmakerMenuBar menuBar) {
		super.buildMenu(menuBar);
		menuBar.add(buildFileMenu());
		menuBar.add(buildEditMenu());
		menuBar.add(buildOperationMenu());
		menuBar.add(buildViewMenu());
		menuBar.add(buildAnimalMenu());
		menuBar.add(buildPedigreeMenu());
		menuBar.add(buildHelpMenu());
		menuBar.repaint();

	}

	/**
	 * File (Load to Album..., Load as Fossils..., Save Biomorph..., Save
	 * Fossils..., Save Album..., Close Album, Quit)
	 * 
	 * @return the new File menu.
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
	 * * Edit (Undo | Cut, Copy, Paste, Clear | Highlight Biomorph, Add Biomorph
	 * to Album, Show Album)
	 * 
	 * @return the new Edit menu
	 */
	public WatchmakerMenu buildEditMenu() {
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
	 * * Operation (Breed, Drift, Engineering, Hopeful Monster, Initialize
	 * Fossil Record, Play Back Fossils, Recording Fossils, Triangle, Array)
	 * (Note: Array is not in Mono)
	 * 
	 * @return the new Operation menu
	 */
	public WatchmakerMenu buildOperationMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("Operation");
		menu.add(new SwingWatchmakerMenuItem(new SwingActionBreed(appData)));
		menu.add(new SwingWatchmakerMenuItem("Drift"));
		menu.add(new SwingWatchmakerMenuItem("Engineering"));
		menu.add(new SwingWatchmakerMenuItem("Hopeful Monster"));
		menu.add(new SwingWatchmakerMenuItem("Initialize Fossil Record"));
		menu.add(new SwingWatchmakerMenuItem("Play Back Fossils"));
		menu.add(recordingFossils);
		menu.add(new SwingWatchmakerMenuItem("Triangle"));
		menu.add(new SwingWatchmakerMenuItem("Array"));
		return menu;
	}

	/**
	 * View (More Rows, Fewer Rows, More Columns, Fewer Columns, Change View,
	 * Drift Sweep, Make top of triangle) Make left of triangle, Make right of
	 * triangle, View Pedigree)
	 * @return the new View menu
	 */
	public WatchmakerMenu buildViewMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("Edit");
		menu.add(new SwingWatchmakerMenuItem("More Rows"));
		menu.add(new SwingWatchmakerMenuItem("Fewer Rows"));
		menu.add(new SwingWatchmakerMenuItem("More Columns"));
		menu.add(new SwingWatchmakerMenuItem("Fewer Columns"));
		menu.add(new SwingWatchmakerCheckBoxMenuItem("Change View"));
		menu.add(new SwingWatchmakerCheckBoxMenuItem("Drift Sweep"));
		menu.add(new SwingWatchmakerMenuItem("Make top of triangle"));
		menu.add(new SwingWatchmakerMenuItem("Make left of triangle"));
		menu.add(new SwingWatchmakerMenuItem("Make right of triangle"));
		menu.add(new SwingWatchmakerMenuItem("View Pedigree"));
		return menu;
	}

	/**
	 * Animal (Customise, Snail, Turritella, Bivalve, Ammonite, Nautilus,
	 * Brachiopod, Cone, Whelk, Scallop, Eloise, Gallagher's, Rapa, Lightning,
	 * Sundial, Fig, Tun, Razor Shell, Japanese Wonder)
	 * 
	 * @return the new Animal menu
	 */
	public WatchmakerMenu buildAnimalMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("Animal");
		menu.add(new SwingWatchmakerMenuItem("Customise"));
		menu.add(new SwingWatchmakerMenuItem("Snail"));
		menu.add(new SwingWatchmakerMenuItem("Turritella"));
		menu.add(new SwingWatchmakerMenuItem("Bivalve"));
		menu.add(new SwingWatchmakerMenuItem("Ammonite"));
		menu.add(new SwingWatchmakerMenuItem("Nautilus"));
		menu.add(new SwingWatchmakerMenuItem("Brachiopod"));
		menu.add(new SwingWatchmakerMenuItem("Cone"));
		menu.add(new SwingWatchmakerMenuItem("Whelk"));
		menu.add(new SwingWatchmakerMenuItem("Scallop"));
		menu.add(new SwingWatchmakerMenuItem("Eloise"));
		menu.add(new SwingWatchmakerMenuItem("Gallagher's"));
		menu.add(new SwingWatchmakerMenuItem("Rapa"));
		menu.add(new SwingWatchmakerMenuItem("Lightning"));
		menu.add(new SwingWatchmakerMenuItem("Sundial"));
		menu.add(new SwingWatchmakerMenuItem("Fig"));
		menu.add(new SwingWatchmakerMenuItem("Tun"));
		menu.add(new SwingWatchmakerMenuItem("Razor Shell"));
		menu.add(new SwingWatchmakerMenuItem("Japanese Wonder"));
		return menu;
	}

	/**
	 * Pedigree (Start Pedigree | Draw Out Offspring , No Mirrors, Single Mirror
	 * , Double Mirrors | Move, Detach, Kill)
	 * 
	 * @return the new Pedigree menu
	 */
	public WatchmakerMenu buildPedigreeMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("Pedigree");
		menu.add(new SwingWatchmakerMenuItem("Start Pedigree"));
		menu.addSeparator();
		menu.add(new SwingWatchmakerMenuItem("Draw Out Offspring"));
		ButtonGroup group = new ButtonGroup();
		SwingWatchmakerCheckBoxMenuItem item;
		item = new SwingWatchmakerCheckBoxMenuItem("No Mirrors");
		menu.add(item);
		group.add(item);
		item = new SwingWatchmakerCheckBoxMenuItem("Single Mirror");
		menu.add(item);
		group.add(item);
		item = new SwingWatchmakerCheckBoxMenuItem("Double Mirrors");
		menu.add(item);
		group.add(item);
		
		menu.addSeparator();
		menu.add(new SwingWatchmakerMenuItem("Move"));
		menu.add(new SwingWatchmakerMenuItem("Detach"));
		menu.add(new SwingWatchmakerMenuItem("Kill"));
		return menu;
	}

	/**
	 * Help (Help with current operation , Miscellaneous Help)
	 * 
	 * @return the new Help menu.
	 */
	public WatchmakerMenu buildHelpMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("Help");
		menu.add(new SwingWatchmakerMenuItem("Help with current operation"));
		menu.add(new SwingWatchmakerMenuItem("Miscellaneous Help"));
		menu.add(new SwingWatchmakerMenuItem(new AboutSnailsAction((Component)menu)));
		return menu;
	}

//	@Override
//	public void propertyChange(PropertyChangeEvent evt) {
//		if (evt.getPropertyName().equals("recordingFossils"))
//			recordingFossils.setSelected((Boolean) evt.getNewValue());
//
//	}

	
}
