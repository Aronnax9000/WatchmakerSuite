package net.richarddawkins.watchmaker.morph.arthro.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import net.richarddawkins.watchmaker.gui.ActionBreed;
import net.richarddawkins.watchmaker.gui.SwingAppData;
import net.richarddawkins.watchmaker.gui.menu.MenuBuilder;
import net.richarddawkins.watchmaker.gui.menu.SimpleMenuBuilder;
/**
 * File (New | Open, Close, Save, Save As... | Quit)
 * Edit (Undo | Cut, Copy, Paste, Clear, Select All | Show Clipboard)
 * Operation (Breed , Show as Text, Engineer)
 * View (Preferences)
 * @author alan
 *
 */
public class ArthromorphMenuBuilder extends SimpleMenuBuilder implements MenuBuilder, PropertyChangeListener {
	
	
	public ArthromorphMenuBuilder(SwingAppData swingAppData) {
		super(swingAppData);
		
	}
	
	public void buildMenu(JMenuBar menuBar) {
		super.buildMenu(menuBar);
		menuBar.add(buildFileMenu());
		menuBar.add(buildEditMenu());
		menuBar.add(buildOperationMenu());
		menuBar.add(buildViewMenu());
		menuBar.add(buildHelpMenu());
		menuBar.repaint();

	}

	public JMenu buildFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(new JMenuItem("New"));
		menu.add(new JMenuItem("Open"));
		menu.add(new JMenuItem("Close"));
		menu.add(new JMenuItem("Save"));
		menu.add(new JMenuItem("Save As..."));
		
		return menu;
	}
	public JMenu buildEditMenu() {
		JMenu menu = new JMenu("Edit");
		menu.add(new JMenuItem("Undo"));
		menu.add(new JMenuItem("Cut"));
		menu.add(new JMenuItem("Copy"));
		menu.add(new JMenuItem("Paste"));
		menu.add(new JMenuItem("Clear"));
		menu.add(new JMenuItem("Select All"));
		menu.add(new JMenuItem("Show Clipboard"));

		return menu;
	}
	public JMenu buildOperationMenu() {
		JMenu menu = new JMenu("Operation");
		menu.add(new JMenuItem(new ActionBreed(swingAppData)));
		menu.add(new JMenuItem(new ShowAsTextAction(swingAppData)));
		menu.add(new JMenuItem(new EngineerAction(swingAppData)));
		return menu;
	}
	public JMenu buildViewMenu() {
		JMenu menu = new JMenu("View");
		menu.add(new JMenuItem(new PreferencesAction(swingAppData)));

		return menu;
	}
	public JMenu buildHelpMenu() {
		JMenu menu = new JMenu("Help");
		menu.add(new AboutArthromorphsAction(menu));
		return menu;
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}


}
