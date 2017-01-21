package net.richarddawkins.watchmaker.swing.wtp;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.richarddawkins.watchmaker.app.MultiMorphTypeTabbedPanel;
import net.richarddawkins.watchmaker.swing.appdata.SwingAppData;
import net.richarddawkins.watchmaker.swing.appdata.SwingAppDataFactory;
import net.richarddawkins.watchmaker.swing.appdata.SwingAppDataFactoryService;

public class SwingMultiMorphTypeTabbedPanel extends JTabbedPane implements MultiMorphTypeTabbedPanel {

	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.gui.WatchmakerTabbedPane");

	protected JMenuBar jMenuBar;

	private static final long serialVersionUID = -9105080336982806166L;

	public SwingMultiMorphTypeTabbedPanel() {
		
		SwingAppDataFactoryService service = SwingAppDataFactoryService.getInstance();
		SwingAppDataFactory factory = service.getFactory();
		for (String morphType : factory.getMorphTypes()) {
			if (!morphType.equals("Snails")) {
				logger.log(Level.INFO, "Creating WatchmakerTabbedPane for " + morphType.toString());
				factory.setMorphType(morphType);
				SwingAppData swingAppData = factory.newSwingAppData();
				swingAppData.setFrame(this);
				addSwingAppData(swingAppData);
			}
		}
		if (this.getTabCount() != 0)
			changeToTab(0);
		addChangeListener(new TabChangeListener());
		setSelectedIndex(0);
	}

	protected Vector<SwingAppData> swingAppData = new Vector<SwingAppData>();

	protected WatchmakerMenuBar menuBar;

	public String uniquify(String name) {
		String newName = name;
		boolean unique = false;
		int counter = 0;
		while (!unique) {
			boolean found = false;
			for (SwingAppData appData : swingAppData) {
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

	public void addSwingAppData(SwingAppData newSwingAppData) {
		logger.log(Level.INFO, "addSwingAppData " + newSwingAppData.getName());
		newSwingAppData.setName(uniquify(newSwingAppData.getName()));
		swingAppData.add(newSwingAppData);
		newSwingAppData.addDefaultMorphView();


		
		SwingWatchmakerTabComponent tabComponent = new SwingWatchmakerTabComponent();
		tabComponent.setName(newSwingAppData.getName());
		tabComponent.setIconFromFilename(newSwingAppData.getIcon());
		
		addTab(newSwingAppData.getName(), 
				tabComponent.getIcon(), 
				(JPanel) newSwingAppData.getMorphViewsTabbedPane(),
				newSwingAppData.getToolTip());
		
		
		setTabComponentAt(this.getTabCount() - 1,
				tabComponent);

		setSelectedIndex(this.getTabCount() - 1);
	}

	public void changeToTab(int selectedIndex) {
		swingAppData.elementAt(selectedIndex).getMenuBuilder().buildMenu(jMenuBar);
	}

	class TabChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			changeToTab(getSelectedIndex());
		}
	}

	public void setMenuBar(WatchmakerMenuBar menuBar) {
		this.menuBar = menuBar;
		
	}
}
