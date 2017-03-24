package net.richarddawkins.watchmaker.swing.menu;

import java.awt.Component;
import java.awt.LayoutManager;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.border.Border;

import net.richarddawkins.watchmaker.menu.WatchmakerMenuItem;

public class SwingWatchmakerMenuItem extends JMenuItem implements WatchmakerMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public SwingWatchmakerMenuItem(String string) {
        super(string);
    }

    public SwingWatchmakerMenuItem(Action action) {
        super(action);
    }

    public SwingWatchmakerMenuItem(String string, KeyStroke keyStroke) {
        super(string);
        this.setAccelerator(keyStroke);
    }

    public SwingWatchmakerMenuItem(Action action, KeyStroke keyStroke) {
        super(action);
        this.setAccelerator(keyStroke);
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
