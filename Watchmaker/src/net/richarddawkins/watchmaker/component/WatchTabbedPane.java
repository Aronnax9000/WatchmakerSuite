package net.richarddawkins.watchmaker.component;

import net.richarddawkins.watchmaker.app.WatchmakerTabComponent;

public interface WatchTabbedPane extends WatchComponent {
    void addChangeListener(Object listener);

    void addTab(String name, Object icon, WatchComponent component,
            String toolTip);

    int getTabCount();

    void setSelectedIndex(int i);
    int getSelectedIndex();

    void setTabComponentAt(int i, WatchmakerTabComponent tabComponent);
}

