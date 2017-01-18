package net.richarddawkins.watchmaker.morphs.mono.swing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import net.richarddawkins.watchmaker.swing.ActionBreed;
import net.richarddawkins.watchmaker.swing.ActionEngineering;
import net.richarddawkins.watchmaker.swing.SwingAppData;
import net.richarddawkins.watchmaker.swing.menu.MenuBuilder;
import net.richarddawkins.watchmaker.swing.menu.SimpleMenuBuilder;
/**
 * Application (About Blind Watchmaker)
 * File (Load to Album..., Load as Fossils..., Save Biomorph...,
 *   Save Fossils..., Save Album..., Close Album, Quit)
 * Edit (Undo | Cut, Copy, Paste, Clear | Highlight Biomorph,
 *   Add Biomorph to Album, Show Album)
 * Operation (Breed, Drift, Engineering, Hopeful Monster,
 *   Initialize Fossil Record, Play Back Fossils, Recording Fossils,
 *   Triangle)
 * View (More Rows, Fewer Rows, More Columns, Fewer Columns,
 *   Thicker Pen, Thinner Pen, Drift Sweep, Make top of triangle,
 *   Make left of triangle, Make right of triangle)
 * Mutations (Segmentation, Gradient, Asymmetry, Radial Sym
 *   Scaling Factor, Mutation Size, Mutation Rate, Tapering Twigs)
 * Pedigree (Display Pedigree | Draw Out Offspring | No Mirrors, 
 *   Single Mirror , Double Mirrors | Move, Detach, Kill)
 * Help (Help with current operation , Miscellaneous Help)
 *   
 * @author alan
 *
 */
public class MonochromeMenuBuilder extends SimpleMenuBuilder implements MenuBuilder, PropertyChangeListener {
  
	

	JCheckBoxMenuItem segmentation = new JCheckBoxMenuItem("Segmentation");
	JCheckBoxMenuItem gradient = new JCheckBoxMenuItem("Gradient");
	JCheckBoxMenuItem asymmetry = new JCheckBoxMenuItem("Asymmetry");
	JCheckBoxMenuItem radialSym = new JCheckBoxMenuItem("Radial Sym");
	JCheckBoxMenuItem scalingFactor = new JCheckBoxMenuItem("Scaling Factor");
	JCheckBoxMenuItem mutationSize = new JCheckBoxMenuItem("Mutation Size");
	JCheckBoxMenuItem mutationRate = new JCheckBoxMenuItem("Mutation Rate");
	JCheckBoxMenuItem taperingTwigs = new JCheckBoxMenuItem("Tapering Twigs");
	JCheckBoxMenuItem noMirrors = new JCheckBoxMenuItem("No Mirrors");
	JCheckBoxMenuItem singleMirror = new JCheckBoxMenuItem("Single Mirror");
	JCheckBoxMenuItem doubleMirrors = new JCheckBoxMenuItem("Double Mirrors");
	JCheckBoxMenuItem recordingFossils = new JCheckBoxMenuItem("Recording Fossils");
	JCheckBoxMenuItem driftSweep = new JCheckBoxMenuItem("Drift Sweep");

	public MonochromeMenuBuilder(SwingAppData swingAppData) {
		super(swingAppData);
	    ButtonGroup group = new ButtonGroup();
		group.add(noMirrors);
		group.add(singleMirror);
		group.add(doubleMirrors);
		swingAppData.addPropertyChangeListener(this);
	}
	
  public void buildMenu(JMenuBar menuBar) {
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
	 *  Help (Help with current operation , Miscellaneous Help)

	 * @return
	 */
	private JMenu buildHelpMenu() {
		JMenu menu = new JMenu("Help");
		menu.add(new JMenuItem("Help with current operation"));
		menu.add(new JMenuItem("Miscellaneous Help"));
		menu.add(new JMenuItem(new AboutMonochromeAction(swingAppData.getFrame())));
		return menu;
	}
	/**
     * Pedigree (Display pedigree | Draw Out Offspring , No mirrors, 
     *   Single Mirror , Double Mirrors | Move, Detach, Kill)	 
     * @return the Pedigree menu
	 */
	public JMenu buildPedigreeMenu() {
		JMenu menu = new JMenu("Pedigree");
		menu.add(new JMenuItem("Display Pedigree"));
		menu.addSeparator();
		menu.add(new JMenuItem("Draw Out Offspring"));
		menu.add(noMirrors);
		menu.add(singleMirror);
		menu.add(doubleMirrors);
		menu.addSeparator();
		menu.add(new JMenuItem("Move"));
		menu.add(new JMenuItem("Detach"));
		menu.add(new JMenuItem("Kill"));
		return menu;
	}	
	

	/**
	 *  * View (More Rows, Fewer Rows, More Columns, Fewer Columns,
	 *   Thicker Pen, Thinner Pen, Drift Sweep, Make top of triangle,
	 *   Make left of triangle, Make right of triangle)
	
	 * @return
	 */
	private JMenu buildViewMenu() {
		JMenu menu = new JMenu("View");
		menu.add(new JMenuItem("More Rows"));
		menu.add(new JMenuItem("Fewer Rows"));
		menu.add(new JMenuItem("More Columns"));
		menu.add(new JMenuItem("Fewer Columns"));
		menu.add(new JMenuItem("Thicker Pen"));
		menu.add(new JMenuItem("Thinner Pen"));
		menu.add(new JCheckBoxMenuItem("Drift Sweep"));
		menu.add(new JMenuItem("Make top of triangle"));
		menu.add(new JMenuItem("Make left of triangle"));
		menu.add(new JMenuItem("Make right of triangle"));
		menu.add(viewBoundingBoxes);
    
		return menu;
	}
	/**
	 * Operation (Breed, Drift, Engineering, Hopeful Monster,
     *   Initialize Fossil Record, Play Back Fossils, Recording Fossils,
     *   Triangle)
	 * @return
	 */
	private JMenu buildOperationMenu() {
		JMenu menu = new JMenu("Operation");
		menu.add(new ActionBreed(swingAppData));
		menu.add(new JMenuItem("Drift"));
		menu.add(new ActionEngineering(swingAppData));

		menu.add(new JMenuItem("Hopeful Monster"));
		menu.add(new JMenuItem("Initialize Fossil Record"));
		menu.add(new JMenuItem("Play Back Fossils"));
		menu.add(new JCheckBoxMenuItem("Recording Fossils"));
		menu.add(new JMenuItem("Triangle"));
		return menu;
	}
	/**
	 * Edit (Undo | Cut, Copy, Paste, Clear | Highlight Biomorph,
	 *   Add Biomorph to Album, Show Album)
	 * @return
	 */
	private JMenu buildEditMenu() {
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
     * File (Load to Album..., Load as Fossils..., Save Biomorph...,
     *   Save Fossils..., Save Album..., Close Album, Quit)
	 * @return the new File Menu
	 */
	public JMenu buildFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(new JMenuItem("Load to Album..."));
		menu.add(new JMenuItem("Load as Fossils"));
		menu.add(new JMenuItem("Save Biomoprh..."));
		menu.add(new JMenuItem("Save Fossils..."));
		menu.add(new JMenuItem("Save Album..."));
		menu.add(new JMenuItem("Close Album"));
		
		return menu;
	}

	/**
	 * Build mutations menu
	 * @return the new Mutations menu
	 */
	public JMenu buildMutationsMenu() {
		JMenu menu = new JMenu("Mutations");
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
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	
}
