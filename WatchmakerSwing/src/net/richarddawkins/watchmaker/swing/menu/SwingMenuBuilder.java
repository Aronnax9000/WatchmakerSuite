package net.richarddawkins.watchmaker.swing.menu;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.Icon;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.app.AppDataFactory;
import net.richarddawkins.watchmaker.app.AppDataFactoryService;
import net.richarddawkins.watchmaker.app.MultiMorphTypeTabbedPanel;
import net.richarddawkins.watchmaker.menu.MenuBuilder;
import net.richarddawkins.watchmaker.menu.WatchmakerCheckBoxMenuItem;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;

public abstract class SwingMenuBuilder  implements MenuBuilder {
	

	public SwingMenuBuilder(AppData appData) {
		this.appData = appData;
		viewBoundingBoxes
		= new SwingWatchmakerCheckBoxMenuItem(new SwingWatchmakerAction(appData, "View Bounding Boxes") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			appData.getPhenotypeDrawer().getDrawingPreferences().setShowBoundingBoxes(viewBoundingBoxes.isSelected());
		}
	});
		spinBabyMorphs
		= new SwingWatchmakerCheckBoxMenuItem(new SwingWatchmakerAction(appData, "Spin baby morphs") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			appData.getPhenotypeDrawer().getDrawingPreferences().setSpinBabyMorphs(spinBabyMorphs.isSelected());
		}
	});
	
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
		menu.add(new SwingWatchmakerMenuItem(
				new ActionQuit((Component)menu)));
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
			NewMorphTypeAction morphTypeAction 
			= new NewMorphTypeAction(appData, morphType, (Icon) factory.getIcon(),
					(MultiMorphTypeTabbedPanel) appData.getFrame());
//			if (morphType.equals("Snails"))
//				morphTypeAction.setEnabled(false);
			watchMakerMenu.add(morphTypeAction);
		}
		addFileQuitAction(watchMakerMenu);
		return watchMakerMenu;
	}

	protected WatchmakerCheckBoxMenuItem viewBoundingBoxes; 
	protected WatchmakerCheckBoxMenuItem spinBabyMorphs; 

}
