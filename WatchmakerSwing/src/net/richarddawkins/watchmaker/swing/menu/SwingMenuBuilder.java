package net.richarddawkins.watchmaker.swing.menu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.app.AppDataFactory;
import net.richarddawkins.watchmaker.app.AppDataFactoryService;
import net.richarddawkins.watchmaker.app.MultiMorphTypeTabbedPanel;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.menu.MenuBuilder;
import net.richarddawkins.watchmaker.menu.WatchmakerCheckBoxMenuItem;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;

public abstract class SwingMenuBuilder implements MenuBuilder {

	public SwingMenuBuilder(AppData appData) {
		this.appData = appData;
		viewBoundingBoxes = new SwingWatchmakerCheckBoxMenuItem(
				new SwingWatchmakerAction(appData, "View Bounding Boxes") {

					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						appData.getPhenotypeDrawer().getDrawingPreferences()
								.setShowBoundingBoxes(viewBoundingBoxes.isSelected());
					}
				});
		spinBabyMorphs = new SwingWatchmakerCheckBoxMenuItem(new SwingWatchmakerAction(appData, "Spin baby morphs") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				appData.getPhenotypeDrawer().getDrawingPreferences().setSpinBabyMorphs(spinBabyMorphs.isSelected());
			}
		});
		highlightBiomorph = new SwingWatchmakerCheckBoxMenuItem(
				new SwingWatchmakerAction(appData, "Highlight biomorph") {

					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						MorphView selectedMorphView = appData.getMorphViewsTabbedPane().getSelectedMorphView();
						JPanel centrePanel = selectedMorphView.getCentrePanel();
						if (((JCheckBoxMenuItem) highlightBiomorph).isSelected()) {
							centrePanel.setCursor(WatchmakerCursors.highlight);
						} else {
							centrePanel.setCursor(null);
							BoxedMorphCollection boxedMorphs = selectedMorphView.getBoxedMorphVector(); 
							if(boxedMorphs.getSelectedBoxedMorph() != null) {
								boxedMorphs.setSelectedBoxedMorph(null);
								centrePanel.repaint();	
							}
						}
					}
				});
		appData.addPropertyChangeListener("highlighting", (PropertyChangeListener)highlightBiomorph);

	};

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	protected AppData appData;

	public AppData getAppData() {
		return appData;
	}

	public void setAppData(AppData appData) {
		this.appData = appData;
	}

	protected void addFileQuitAction(WatchmakerMenu menu) {
		menu.add(new SwingWatchmakerMenuItem(new ActionQuit((Component) menu)));
	}

	public void buildMenu(WatchmakerMenuBar menuBar) {
		menuBar.removeAll();
		menuBar.add(buildWatchmakerMenu());
	}

	protected WatchmakerMenu buildWatchmakerMenu() {
		WatchmakerMenu watchMakerMenu = new SwingWatchmakerMenu("Watchmaker");
		AppDataFactory factory = AppDataFactoryService.getInstance().getFactory();
		for (String morphType : factory.getMorphTypes()) {
			factory.setMorphType(morphType);
			NewMorphTypeAction morphTypeAction = new NewMorphTypeAction(appData, 
					morphType, (Icon) factory.getIcon(),
					(MultiMorphTypeTabbedPanel) appData.getFrame());
			// if (morphType.equals("Snails"))
			// morphTypeAction.setEnabled(false);
			watchMakerMenu.add(morphTypeAction);
		}
		addFileQuitAction(watchMakerMenu);
		return watchMakerMenu;
	}

	protected WatchmakerCheckBoxMenuItem viewBoundingBoxes;
	protected WatchmakerCheckBoxMenuItem highlightBiomorph;
	protected WatchmakerCheckBoxMenuItem spinBabyMorphs;
	/**
	 * Edit (Undo | Cut, Copy, Paste, Clear | Highlight Biomorph, Add Biomorph
	 * to Album, Show Album)
	 * 
	 * @return
	 */
	protected WatchmakerMenu buildEditMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("Edit");
		menu.add(new SwingWatchmakerMenuItem("Undo"));
		menu.addSeparator();
		menu.add(new SwingWatchmakerMenuItem("Cut"));
		menu.add(new ActionCopy(appData));
		menu.add(new SwingWatchmakerMenuItem("Paste"));
		menu.add(new SwingWatchmakerMenuItem("Clear"));
		menu.add(new SwingWatchmakerMenuItem("Select All"));
		menu.add(new SwingWatchmakerMenuItem("Show Clipboard"));
		menu.addSeparator();
		menu.add(highlightBiomorph);

		menu.add(new SwingWatchmakerMenuItem("Add Biomorph to Album"));
		menu.add(new ActionShowAlbum(appData));
		return menu;
	}

	protected WatchmakerMenu buildViewMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("View");
		menu.add(viewBoundingBoxes);
		menu.add(spinBabyMorphs);

		return menu;
	}

	protected WatchmakerMenu buildOperationMenu() {
		WatchmakerMenu menu = new SwingWatchmakerMenu("Operation");
		menu.add(new SwingActionBreed(appData));
		menu.add(new SwingWatchmakerMenuItem("Drift"));
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
				MorphView morphView = 
				appData.getMorphViewsTabbedPane().getSelectedMorphView();
				morphView.seed(morph);
			}
		}));
		menu.add(new SwingWatchmakerMenuItem("Hopeful Monster"));
		menu.add(new SwingWatchmakerMenuItem("Initialize Fossil Record"));
		menu.add(new SwingWatchmakerMenuItem("Play Back Fossils"));
		menu.add(new SwingWatchmakerMenuItem("Recording Fossils"));

		return menu;
	}

}
