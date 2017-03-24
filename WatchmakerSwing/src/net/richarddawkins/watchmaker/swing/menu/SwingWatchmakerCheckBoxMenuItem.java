package net.richarddawkins.watchmaker.swing.menu;

import java.awt.Component;
import java.awt.LayoutManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.border.Border;

import net.richarddawkins.watchmaker.menu.WatchmakerCheckBoxMenuItem;

public class SwingWatchmakerCheckBoxMenuItem extends JCheckBoxMenuItem
        implements PropertyChangeListener, WatchmakerCheckBoxMenuItem {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public SwingWatchmakerCheckBoxMenuItem(
            SwingWatchmakerAction swingWatchmakerAction) {
        super(swingWatchmakerAction);

    }

    public SwingWatchmakerCheckBoxMenuItem(String name) {
        super(name);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        this.setSelected((Boolean) evt.getNewValue());
    }

    @Override
    public void setLayout(Object layout) {
        super.setLayout((LayoutManager) layout);
        
    }

    @Override
    public void setBorder(Object border) {
        super.setBorder((Border) border);
        
    }

    @Override
    public void add(Object newComponent) {
        super.add((Component) newComponent);
        
    }

    @Override
    public void add(Object newComponent, Object constraints) {
        super.add((Component) newComponent, constraints);
        
    }

}
