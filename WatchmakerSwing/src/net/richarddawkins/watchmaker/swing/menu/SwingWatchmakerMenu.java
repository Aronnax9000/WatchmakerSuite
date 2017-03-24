package net.richarddawkins.watchmaker.swing.menu;

import java.awt.Component;
import java.awt.LayoutManager;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.border.Border;

import net.richarddawkins.watchmaker.menu.WatchmakerAction;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;

public class SwingWatchmakerMenu extends JMenu implements WatchmakerMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SwingWatchmakerMenu(String name) {
		super(name);
	}

	@Override
	public void add(Object menuItem) {
		super.add((JMenuItem) menuItem);
	}

	@Override
	public void add(WatchmakerAction action) {
		super.add((Action)action);
	}

    @Override
    public void setLayout(LayoutManager layout) {
        super.setLayout(layout);
    }

    @Override
    public void add(Component newComponent, Object constraints) {
        super.add(newComponent, constraints);
    }

    @Override
    public Component add(Component newComponent) {
        return super.add(newComponent);
    }

    @Override
    public void add(Object newComponent, Object constraints) {
        super.add((Component)newComponent, constraints);
    }

    @Override
    public void setLayout(Object layout) {
        super.setLayout((LayoutManager) layout);
    }

    @Override
    public void setBorder(Object border) {
        super.setBorder((Border)border);
    }
}
