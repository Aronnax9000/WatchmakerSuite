package net.richarddawkins.watchmaker.morph.colour;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import net.richarddawkins.watchmaker.gui.menu.MenuBuilder;
import net.richarddawkins.watchmaker.gui.menu.SimpleMenuBuilder;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.resourceloader.ClassicImageLoader;

/**
 * Application (About Colour Watchmaker) File (Timing, Quit) Edit ( | | Copy,
 * Paste) Operation (Breed, New Random Start) Help ()
 * 
 * @author alan
 *
 */
public class ColourMenuBuilder extends SimpleMenuBuilder implements MenuBuilder, PropertyChangeListener {

	protected ColourBiomorphConfig config;
	
	
	public ColourMenuBuilder(ColourBiomorphConfig config) {
		this.config = config;
		config.addPropertyChangeListener(this);
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

	private JMenu buildFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(new JMenuItem("Timing"));
		addFileQuitAction(menu);
		return menu;
	}

	private JMenu buildViewMenu() {
		JMenu menu = new JMenu("View");
		menu.add(viewBoundingBoxes);
		return menu;
	}

	private JMenu buildEditMenu() {
		JMenu menu = new JMenu("Edit");
		menu.addSeparator();
		menu.addSeparator();
		menu.add(new JMenuItem("Copy"));
		menu.add(new JMenuItem("Paste"));
		return menu;
	}

	private JMenu buildOperationMenu() {
		JMenu menu = new JMenu("Operation");
		Icon newRandomStartIcon = new ImageIcon(
				ClassicImageLoader.getPicture("SixSidedDieShowsFiveIcon_ICON_00257_32x32").getImage());
		menu.add(new JMenuItem(new ColourActionBreed(config)));

		menu.add(new JMenuItem(new AbstractAction("New Random Start", newRandomStartIcon) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		}));
		return menu;
	}

	private JMenu buildHelpMenu() {
		JMenu menu = new JMenu("Help");
		menu.add(new AboutColourAction());
		return menu;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMorphConfig(MorphConfig config) {
		this.config = (ColourBiomorphConfig) config;
		
	}

	@Override
	public MorphConfig getMorphConfig() {
		return config;
	}	
}
