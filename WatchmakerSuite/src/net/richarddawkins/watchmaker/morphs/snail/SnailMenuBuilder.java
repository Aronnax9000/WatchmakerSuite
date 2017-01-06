package net.richarddawkins.watchmaker.morphs.snail;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import net.richarddawkins.watchmaker.gui.ActionBreed;
import net.richarddawkins.watchmaker.gui.menu.MenuBuilder;
import net.richarddawkins.watchmaker.gui.menu.SimpleMenuBuilder;
import net.richarddawkins.watchmaker.morph.MorphConfig;

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
public class SnailMenuBuilder extends SimpleMenuBuilder implements MenuBuilder, PropertyChangeListener {

	private JCheckBoxMenuItem recordingFossils = new JCheckBoxMenuItem("Recording Fossils");

	protected SnailConfig config;
	public SnailMenuBuilder(SnailConfig config) {
		this.config = config;
		config.addPropertyChangeListener(this);
	}

	public void buildMenu(JMenuBar menuBar) {
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
	 * @return
	 */
	public JMenu buildFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(new JMenuItem("Load to Album..."));
		menu.add(new JMenuItem("Load as Fossils"));
		menu.add(new JMenuItem("Save Biomoprh..."));
		menu.add(new JMenuItem("Save Fossils..."));
		menu.add(new JMenuItem("Save Album..."));
		menu.add(new JMenuItem("Close Album"));
		addFileQuitAction(menu);

		return menu;
	}

	/**
	 * * Edit (Undo | Cut, Copy, Paste, Clear | Highlight Biomorph, Add Biomorph
	 * to Album, Show Album)
	 * 
	 * @return
	 */
	public JMenu buildEditMenu() {
		JMenu menu = new JMenu("Edit");
		menu.add(new JMenuItem("Undo"));
		menu.addSeparator();
		menu.add(new JMenuItem("Cut"));
		menu.add(new JMenuItem("Copy"));
		menu.add(new JMenuItem("Paste"));
		menu.add(new JMenuItem("Clear"));
		menu.addSeparator();
		menu.add(new JMenuItem("Highlight Biomorph"));
		menu.add(new JMenuItem("Add Biomorph to Album"));
		menu.add(new JMenuItem("Show Album"));
		return menu;
	}

	/**
	 * * Operation (Breed, Drift, Engineering, Hopeful Monster, Initialize
	 * Fossil Record, Play Back Fossils, Recording Fossils, Triangle, Array)
	 * (Note: Array is not in Mono)
	 * 
	 * @return
	 */
	public JMenu buildOperationMenu() {
		JMenu menu = new JMenu("Operation");
		menu.add(new JMenuItem(new ActionBreed(config)));
		menu.add(new JMenuItem("Drift"));
		menu.add(new JMenuItem("Engineering"));
		menu.add(new JMenuItem("Hopeful Monster"));
		menu.add(new JMenuItem("Initialize Fossil Record"));
		menu.add(new JMenuItem("Play Back Fossils"));
		menu.add(recordingFossils);
		menu.add(new JMenuItem("Triangle"));
		menu.add(new JMenuItem("Array"));
		return menu;
	}

	/**
	 * View (More Rows, Fewer Rows, More Columns, Fewer Columns, Change View,
	 * Drift Sweep, Make top of triangle) Make left of triangle, Make right of
	 * triangle, View Pedigree)
	 */
	public JMenu buildViewMenu() {
		JMenu menu = new JMenu("Edit");
		menu.add(new JMenuItem("More Rows"));
		menu.add(new JMenuItem("Fewer Rows"));
		menu.add(new JMenuItem("More Columns"));
		menu.add(new JMenuItem("Fewer Columns"));
		menu.add(new JCheckBoxMenuItem("Change View"));
		menu.add(new JCheckBoxMenuItem("Drift Sweep"));
		menu.add(new JMenuItem("Make top of triangle"));
		menu.add(new JMenuItem("Make left of triangle"));
		menu.add(new JMenuItem("Make right of triangle"));
		menu.add(new JMenuItem("View Pedigree"));
		return menu;
	}

	/**
	 * Animal (Customise, Snail, Turritella, Bivalve, Ammonite, Nautilus,
	 * Brachiopod, Cone, Whelk, Scallop, Eloise, Gallagher's, Rapa, Lightning,
	 * Sundial, Fig, Tun, Razor Shell, Japanese Wonder)
	 * 
	 * @return
	 */
	public JMenu buildAnimalMenu() {
		JMenu menu = new JMenu("Animal");
		menu.add(new JMenuItem("Customise"));
		menu.add(new JMenuItem("Snail"));
		menu.add(new JMenuItem("Turritella"));
		menu.add(new JMenuItem("Bivalve"));
		menu.add(new JMenuItem("Ammonite"));
		menu.add(new JMenuItem("Nautilus"));
		menu.add(new JMenuItem("Brachiopod"));
		menu.add(new JMenuItem("Cone"));
		menu.add(new JMenuItem("Whelk"));
		menu.add(new JMenuItem("Scallop"));
		menu.add(new JMenuItem("Eloise"));
		menu.add(new JMenuItem("Gallagher's"));
		menu.add(new JMenuItem("Rapa"));
		menu.add(new JMenuItem("Lightning"));
		menu.add(new JMenuItem("Sundial"));
		menu.add(new JMenuItem("Fig"));
		menu.add(new JMenuItem("Tun"));
		menu.add(new JMenuItem("Razor Shell"));
		menu.add(new JMenuItem("Japanese Wonder"));
		return menu;
	}

	/**
	 * Pedigree (Start Pedigree | Draw Out Offspring , No Mirrors, Single Mirror
	 * , Double Mirrors | Move, Detach, Kill)
	 * 
	 * @return
	 */
	public JMenu buildPedigreeMenu() {
		JMenu menu = new JMenu("Pedigree");
		menu.add(new JMenuItem("Start Pedigree"));
		menu.addSeparator();
		menu.add(new JMenuItem("Draw Out Offspring"));
		ButtonGroup group = new ButtonGroup();
		group.add(menu.add(new JCheckBoxMenuItem("No Mirrors")));
		group.add(menu.add(new JCheckBoxMenuItem("Single Mirror")));
		group.add(menu.add(new JCheckBoxMenuItem("Double Mirrors")));
		menu.addSeparator();
		menu.add(new JMenuItem("Move"));
		menu.add(new JMenuItem("Detach"));
		menu.add(new JMenuItem("Kill"));
		return menu;
	}

	/**
	 * Help (Help with current operation , Miscellaneous Help)
	 * 
	 * @return
	 */
	public JMenu buildHelpMenu() {
		JMenu menu = new JMenu("Help");
		menu.add(new JMenuItem("Help with current operation"));
		menu.add(new JMenuItem("Miscellaneous Help"));
		menu.add(new JMenuItem(new AboutSnailsAction(menu)));
		return menu;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("recordingFossils"))
			recordingFossils.setSelected((Boolean) evt.getNewValue());

	}

	@Override
	public void setMorphConfig(MorphConfig config) {
		this.config = (SnailConfig) config;
		
	}

	@Override
	public MorphConfig getMorphConfig() {
		return config;
	}	
}
