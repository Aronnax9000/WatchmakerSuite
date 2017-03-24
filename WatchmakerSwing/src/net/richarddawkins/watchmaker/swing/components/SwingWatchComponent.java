package net.richarddawkins.watchmaker.swing.components;

import java.awt.Component;
import java.awt.LayoutManager;

import javax.swing.JComponent;
import javax.swing.border.Border;

import net.richarddawkins.watchmaker.component.WatchComponent;

public class SwingWatchComponent implements WatchComponent {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    protected JComponent component;
    
    @Override
    public void add(Object newComponent) {
        component.add((Component)newComponent);
        
    }
    @Override
    public void setOpaque(boolean b) {
        component.setOpaque(b);
        
    }

    @Override
    public void add(Object newComponent, Object constraints) {
        component.add((Component) newComponent, constraints);
        
    }
    @Override
    public Object getComponent() {
        return component;
    }


    @Override
    public void setLayout(Object layout) {
        component.setLayout((LayoutManager) layout);
    }

    @Override
    public void setBorder(Object border) {
        component.setBorder((Border) border);
        
    }

    @Override
    public void repaint() {
        component.repaint();
        
    }
    @Override
    public void removeAll() {
        component.removeAll();
        
    }


}
