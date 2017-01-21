package net.richarddawkins.watchmaker.swing.menu;

import java.awt.event.ActionEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.app.AppDataFactory;
import net.richarddawkins.watchmaker.app.AppDataFactoryService;
import net.richarddawkins.watchmaker.app.MultiMorphTypeTabbedPanel;
import net.richarddawkins.watchmaker.menu.MenuBuilder;
import net.richarddawkins.watchmaker.menu.WatchmakerCheckBoxMenuItem;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;

public abstract class SwingMenuBuilder extends JMenuBar implements MenuBuilder {

	/**
	 * 
	 */
	
	public SwingMenuBuilder() {} 
	public SwingMenuBuilder(AppData appData) {this.appData = appData;};
	
	private static final long serialVersionUID = 1L;
	protected AppData appData;

	public AppData getAppData() {
		return appData;
	}

	public void setAppData(AppData appData) {
		this.appData = appData;
	}

	protected void addFileQuitAction(JMenu menu) {
		menu.add(new JMenuItem(new ActionQuit(menu)));
	}

	public void buildMenu(WatchmakerMenuBar menuBar) {
		super.removeAll();
		super.add(buildWatchmakerMenu());
	}

	protected JMenu buildWatchmakerMenu() {
		JMenu watchMakerMenu = new JMenu("Watchmaker");
		AppDataFactory factory = AppDataFactoryService.getInstance().getFactory();
		for (String morphType : factory.getMorphTypes()) {
			factory.setMorphType(morphType);
			NewMorphTypeAction morphTypeAction = new NewMorphTypeAction(morphType, factory.getIcon(),
					(MultiMorphTypeTabbedPanel) appData.getFrame());
			if (morphType.equals("Snails"))
				morphTypeAction.setEnabled(false);
			watchMakerMenu.add(morphTypeAction);
		}
		addFileQuitAction(watchMakerMenu);
		return watchMakerMenu;
	}

	protected WatchmakerCheckBoxMenuItem viewBoundingBoxes 
		= new SwingWatchmakerCheckBoxMenuItem(new SwingWatchmakerAction(appData, "View Bounding Boxes") {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			appData.setShowBoundingBoxes(viewBoundingBoxes.isSelected());
			/*
			 * BreedingPanel breedingPanel =
			 * config.getBreedingAndGeneBoxPanel().getBreedingPanel();
			 * breedingPanel.repaint(); breedingPanel.revalidate();
			 */
		}
	});

}
