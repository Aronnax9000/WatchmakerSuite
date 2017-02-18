package net.richarddawkins.watchmaker.morphs.mono.menu.swing;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
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

	public SwingMonochromeMenuBuilder(AppData appData) {
		super(appData);

	}

	public void buildMenu(WatchmakerMenuBar menuBar) {
		super.buildMenu(menuBar);
		menuBar.add(buildMutationsMenu());
		menuBar.repaint();
	}
	/**
	 * Help (Help with current operation , Miscellaneous Help)
	 * 
	 * @return
	 */
	@Override
	protected WatchmakerMenu buildHelpMenu() {
		WatchmakerMenu menu = super.buildHelpMenu();
		menu.add(new SwingWatchmakerMenuItem(new AboutMonochromeAction(menu)));
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
