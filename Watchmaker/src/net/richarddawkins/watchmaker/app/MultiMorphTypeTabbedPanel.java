package net.richarddawkins.watchmaker.app;

import net.richarddawkins.watchmaker.morphview.MorphViewsTabbedPanel;

public interface MultiMorphTypeTabbedPanel {

	int indexOfTab(String tabName);

	void removeTabAt(int index);

	void addAppData(AppData appData);

	MorphViewsTabbedPanel getSelectedMorphViewsTabbledPanel();


}