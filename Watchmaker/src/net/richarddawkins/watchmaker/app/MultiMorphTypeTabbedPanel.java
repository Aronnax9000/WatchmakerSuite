package net.richarddawkins.watchmaker.app;

public interface MultiMorphTypeTabbedPanel {

	int indexOfTab(String tabName);

	void removeTabAt(int index);

	void addAppData(AppData appData);

}