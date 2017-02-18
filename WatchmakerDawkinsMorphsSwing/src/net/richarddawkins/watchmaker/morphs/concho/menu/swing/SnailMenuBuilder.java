package net.richarddawkins.watchmaker.morphs.concho.menu.swing;

import java.awt.event.ActionEvent;

import net.richarddawkins.watchmaker.menu.WatchmakerCheckBoxMenuItem;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.morphs.concho.embryo.SnailEmbryologyPreferences;
import net.richarddawkins.watchmaker.swing.app.SwingAppData;
import net.richarddawkins.watchmaker.swing.menu.SwingMenuBuilder;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;
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

	public SnailMenuBuilder(SwingAppData swingAppData) {
		super(swingAppData);
		changeView = new SwingWatchmakerCheckBoxMenuItem(new SwingWatchmakerAction(appData, "Change View") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				SnailEmbryologyPreferences prefs = (SnailEmbryologyPreferences)
				appData.getMorphConfig().getEmbryology().getEmbryologyPreferences();
				prefs.setSideView(changeView.isSelected());
			}
		});
	}

	@Override
	public void buildMenu(WatchmakerMenuBar menuBar) {
		super.buildMenu(menuBar);
		menuBar.add(buildAnimalMenu());
		menuBar.repaint();

	}


	/**
	 * * Operation (Breed, Drift, Engineering, Hopeful Monster, Initialize
	 * Fossil Record, Play Back Fossils, Recording Fossils, Triangle, Array)
	 * (Note: Array is not in Mono)
	 * 
	 * @return the new Operation menu
	 */
	public WatchmakerMenu buildOperationMenu() {
		WatchmakerMenu menu = super.buildOperationMenu();
		menu.add(new SwingWatchmakerMenuItem("Array"));
		return menu;
	}

	/**
	 * View (More Rows, Fewer Rows, More Columns, Fewer Columns, Change View,
	 * Drift Sweep, Make top of triangle) Make left of triangle, Make right of
	 * triangle, View Pedigree)
	 * 
	 * @return the new View menu
	 */
	public WatchmakerMenu buildViewMenu() {
		WatchmakerMenu menu = super.buildViewMenu();
		menu.add(changeView);
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
	
	protected WatchmakerCheckBoxMenuItem changeView;
}
