package net.richarddawkins.watchmaker.swing.morphview;

import java.awt.Component;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.MorphViewsTabbedPanel;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;

public class SwingMorphViewsTabbedPanel extends JTabbedPane implements MorphViewsTabbedPanel {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.gui.MorphViewsTabbedPane");

	protected AppData appData;
	@Override
	public AppData getAppData() {
		return appData;
	}
	@Override
	public void setAppData(AppData appData) {
		this.appData = appData;
	}

	private static final long serialVersionUID = -9105080336982806166L;

	public SwingMorphViewsTabbedPanel(AppData appData) {
		this.appData = appData;
		addChangeListener(new TabChangeListener());
	}

	protected Vector<MorphView> morphViews = new Vector<MorphView>();

	public String uniquify(String name) {
		String newName = name;
		boolean unique = false;
		int counter = 0;
		while (!unique) {
			boolean found = false;
			for (MorphView morphView : morphViews) {
				String morphViewName = morphView.getName();
				if (newName.equals(morphViewName)) {
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

	public void addMorphView(MorphView view) {
		logger.log(Level.INFO, "addMorphView " + view.getName());
		view.setName(uniquify(view.getName()));
		morphViews.add(view);

		addTab(view.getName(), null, (Component) view, view.getToolTip());

		SwingMorphViewTabComponent tabComponent = new SwingMorphViewTabComponent();
		tabComponent.setSwingMorphViewsTabbedPanel(this);
		tabComponent.setIcon(view.getIcon());
		tabComponent.setName(view.getName());

		setTabComponentAt(this.getTabCount() - 1, tabComponent);
		setSelectedIndex(this.getTabCount() - 1);
	}

	class TabChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JTabbedPane source = (JTabbedPane) e.getSource();
			int selectedIndex = getSelectedIndex();
			if (selectedIndex != -1) {
				MorphView morphView = morphViews.get(getSelectedIndex());
				
				appData.setHighlighting(((JComponent) morphView).getCursor() 
						== WatchmakerCursors.highlight);

			}
		}
	}

	@Override
	public MorphView getSelectedMorphView() {
		MorphView selectedMorphView = (MorphView) this.getSelectedComponent();
		return selectedMorphView;
	}
    @Override
    public Vector<MorphView> getMorphViews() {
        return morphViews;
        
    }

}
