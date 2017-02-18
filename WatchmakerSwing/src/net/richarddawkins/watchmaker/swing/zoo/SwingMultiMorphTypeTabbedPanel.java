package net.richarddawkins.watchmaker.swing.zoo;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.app.AppDataFactory;
import net.richarddawkins.watchmaker.app.AppDataFactoryService;
import net.richarddawkins.watchmaker.app.MultiMorphTypeTabbedPanel;
import net.richarddawkins.watchmaker.morphview.MorphViewsTabbedPanel;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenuBar;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewsTabbedPanel;

public class SwingMultiMorphTypeTabbedPanel extends JTabbedPane implements MultiMorphTypeTabbedPanel {

	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.gui.WatchmakerTabbedPane");
	@Override
	public MorphViewsTabbedPanel getSelectedMorphViewsTabbledPanel() {
		return (MorphViewsTabbedPanel) this.getComponentAt(this.getSelectedIndex());
	}


	private static final long serialVersionUID = -9105080336982806166L;
	private static SwingMultiMorphTypeTabbedPanel instance;
	
	public static synchronized SwingMultiMorphTypeTabbedPanel getInstance() {
		if(instance == null) {
			instance = new SwingMultiMorphTypeTabbedPanel();
		}
		return instance;
	}
	protected SwingMultiMorphTypeTabbedPanel() {
		
        Preferences prefs = Preferences.userRoot().node("net/richarddawkins/watchmaker/startup");
        String morphs = prefs.get("morphs", "Monochrome,Colour,Arthromorphs,Snails");
		AppDataFactoryService service = AppDataFactoryService.getInstance();
		AppDataFactory factory = service.getFactory();
		for (String morphType : factory.getMorphTypes()) {
			if (morphs.indexOf(morphType) != -1) {
				logger.log(Level.INFO, "Creating WatchmakerTabbedPane for " + morphType.toString());
				factory.setMorphType(morphType);
				AppData appData = factory.newAppData();

				addAppData(appData);
			}
		}
		if (this.getTabCount() != 0)
			changeToTab(0);
		addChangeListener(new TabChangeListener());
		setSelectedIndex(0);
	}

	protected Vector<AppData> appDatas = new Vector<AppData>();

	public String uniquify(String name) {
		String newName = name;
		boolean unique = false;
		int counter = 0;
		while (!unique) {
			boolean found = false;
			for (AppData appData : appDatas) {
				if (newName.equals(appData.getName())) {
					found = true;
					break;
				}
			}
			if (found)
				newName = name + " " + ++counter;
			else
				unique = true;
		}
		return newName;
	}

	public void addAppData(AppData newAppData) {
		logger.log(Level.INFO, "addAppData " + newAppData.getName());
		newAppData.setName(uniquify(newAppData.getName()));
		appDatas.add(newAppData);
		newAppData.addDefaultMorphView();


		
		SwingMultiMorphTypeTabComponent tabComponent = new SwingMultiMorphTypeTabComponent();
		tabComponent.setPane(this);
		tabComponent.setName(newAppData.getName());
		tabComponent.setIconFromFilename(newAppData.getIcon());
		
		addTab(newAppData.getName(), 
				tabComponent.getIcon(), 
				(SwingMorphViewsTabbedPanel) newAppData.getMorphViewsTabbedPane(),
				newAppData.getToolTip());
		
		
		setTabComponentAt(this.getTabCount() - 1,
				tabComponent);

		setSelectedIndex(this.getTabCount() - 1);
	}

	public void changeToTab(int selectedIndex) {
		appDatas.elementAt(selectedIndex).getMenuBuilder().buildMenu(SwingWatchmakerMenuBar.getInstance());
	}

	class TabChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			changeToTab(getSelectedIndex());
		}
	}

}
