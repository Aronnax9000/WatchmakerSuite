package net.richarddawkins.watchmaker.morphs.colour.swing;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;
import net.richarddawkins.watchmaker.swing.app.SwingAppData;
import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;
import net.richarddawkins.watchmaker.swing.menu.ActionEngineering;
import net.richarddawkins.watchmaker.swing.menu.SwingMenuBuilder;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenu;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenuItem;

/**
 * Application (About Colour Watchmaker) File (Timing, Quit) Edit ( | | Copy,
 * Paste) Operation (Breed, New Random Start) Help ()
 * 
 * @author alan
 *
 */
public class ColourMenuBuilder extends SwingMenuBuilder implements PropertyChangeListener {


	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	public ColourMenuBuilder(SwingAppData swingAppData) {
	    super(swingAppData);
	}

	public void buildMenu(WatchmakerMenuBar menuBar) {
		super.buildMenu(menuBar);
		
		menuBar.add(buildFileMenu());
		menuBar.add(buildEditMenu());
		menuBar.add(buildOperationMenu());
		menuBar.add(buildViewMenu());
		menuBar.add(buildHelpMenu());
		menuBar.repaint();
	}

	private WatchmakerMenu buildFileMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("File");
		menu.add(new SwingWatchmakerMenuItem("Timing"));
		return menu;
	}

	private WatchmakerMenu buildViewMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("View");
		menu.add(viewBoundingBoxes);
		return menu;
	}

	private WatchmakerMenu buildEditMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("Edit");
		menu.addSeparator();
		menu.addSeparator();
		menu.add(new SwingWatchmakerMenuItem("Copy"));
		menu.add(new SwingWatchmakerMenuItem("Paste"));
		return menu;
	}

	private WatchmakerMenu buildOperationMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("Operation");

		menu.add(new SwingWatchmakerMenuItem(new ColourActionBreed(appData)));
		menu.add(new ActionEngineering(appData));

		
		Icon newRandomStartIcon = new ImageIcon(
				ClassicImageLoader.getPicture("SixSidedDieShowsFiveIcon_ICON_00257_32x32").getImage());
		menu.add(new SwingWatchmakerMenuItem(new AbstractAction("New Random Start", newRandomStartIcon) {


			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				MorphConfig config = appData.getMorphConfig();
				Morph morph = appData.getMorphOfTheHour();
				Genome genome = config.getGenomeFactory().deliverSaltation();
				morph.setGenome(genome);
				MorphViewPanel morphViewPanel = 
				appData.getMorphViewsTabbedPane().getSelectedMorphView().getMorphViewPanel();
				morphViewPanel.seed(morph);
			}
		}));
		return menu;
	}

	private WatchmakerMenu buildHelpMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("Help");
		menu.add(new AboutColourAction());
		return menu;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

	
}
