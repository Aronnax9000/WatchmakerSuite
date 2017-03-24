package net.richarddawkins.watchmaker.swing.morphview;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;

import net.richarddawkins.watchmaker.component.WatchComponent;
import net.richarddawkins.watchmaker.component.WatchTabbedPane;
import net.richarddawkins.watchmaker.swing.components.SwingWatchComponent;

public class SwingWatchTabbedPane extends SwingWatchComponent implements WatchTabbedPane {

    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public SwingWatchTabbedPane() {
        component = new JTabbedPane();
    }
    
    public Object getComponentAt(int selectedIndex) {
        return ((JTabbedPane)component).getComponentAt(selectedIndex);
    }

    @Override
    public void addChangeListener(Object listener) {
        ((JTabbedPane)component).addChangeListener((ChangeListener)listener);
        
    }
    @Override
    public void setSelectedIndex(int i) {
        ((JTabbedPane)component).setSelectedIndex(i);
        
    }
    @Override
    public void setTabComponentAt(int i,
            WatchComponent tabComponent) {
        ((JTabbedPane)component).setTabComponentAt(i, (Component)tabComponent.getComponent());
        
    }
    public void removeTabAt(int index) {
        ((JTabbedPane)component).removeTabAt(index);
        
    }
    public int indexOfTab(String tabName) {
        return ((JTabbedPane)component).indexOfTab(tabName);
    }
    
    @Override
    public void addTab(String name, Object icon, WatchComponent newTab,
            String toolTip) {
        Component newTabComponent = (Component)newTab.getComponent();
        ((JTabbedPane)component).addTab(name, (Icon)icon, newTabComponent, toolTip);
        
    }
    @Override
    public int getTabCount() {
        return ((JTabbedPane)component).getTabCount();
    }

    @Override
    public int getSelectedIndex() {
        return ((JTabbedPane)component).getSelectedIndex();
    }

}
